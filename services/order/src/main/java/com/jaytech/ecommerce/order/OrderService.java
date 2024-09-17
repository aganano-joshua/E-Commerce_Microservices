package com.jaytech.ecommerce.order;

import com.jaytech.ecommerce.customer.CustomerClient;
import com.jaytech.ecommerce.exception.BusinessException;
import com.jaytech.ecommerce.orderline.OrderLineRequest;
import com.jaytech.ecommerce.orderline.OrderLineService;
import com.jaytech.ecommerce.product.ProductClient;
import com.jaytech.ecommerce.product.PurchaseRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final OrderRespo repo;
    private final OrderMapper mapper;
    private final OrderLineService orderLineService;

    public Integer createOrder(@Valid OrderRequest request) {
//        Check customer --> OpenFeign
        var customer = this.customerClient.findCustomerById(request.customerId())
                .orElseThrow(() -> new BusinessException("Cannot create order:: No Customer exists with the provided ID:: " + request.customerId()));
//        purchase the product --> product microservice (RestTemplate)
        this.productClient.purchaseProducts(request.products());
//        persist order
        var order = this.repo.save(mapper.toOrder(request));
//        persit orderline
        for (PurchaseRequest purchaseRequest: request.products()) {
            orderLineService.saveOrderLine(
                    new OrderLineRequest(
                            null,
                            order.getId(),
                            purchaseRequest.productId(),
                            purchaseRequest.quantity()
                    )
            );
        }
//        start payment process
//        send the order confirmation --> notification microservice (kafka)
        return null;
    }
}
