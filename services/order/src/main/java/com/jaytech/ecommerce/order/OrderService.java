package com.jaytech.ecommerce.order;

import com.jaytech.ecommerce.customer.CustomerClient;
import com.jaytech.ecommerce.exception.BusinessException;
import com.jaytech.ecommerce.kafka.OrderConfirmation;
import com.jaytech.ecommerce.kafka.OrderProducer;
import com.jaytech.ecommerce.orderline.OrderLineRequest;
import com.jaytech.ecommerce.orderline.OrderLineService;
import com.jaytech.ecommerce.payment.PaymentClient;
import com.jaytech.ecommerce.payment.PaymentRequest;
import com.jaytech.ecommerce.product.ProductClient;
import com.jaytech.ecommerce.product.PurchaseRequest;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final OrderRespo repo;
    private final OrderMapper mapper;
    private final OrderLineService orderLineService;
    private final OrderProducer orderProducer;
    private final PaymentClient paymentClient;

    public Integer createOrder(@Valid OrderRequest request) {
//        Check customer --> OpenFeign
        var customer = this.customerClient.findCustomerById(request.customerId())
                .orElseThrow(() -> new BusinessException("Cannot create order:: No Customer exists with the provided ID:: " + request.customerId()));
//        purchase the product --> product microservice (RestTemplate)
        var purchaseProducts = this.productClient.purchaseProducts(request.products());
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

        var paymentRequest = new PaymentRequest(
                request.amount(),
                request.paymentMethod(),
                order.getId(),
                order.getRefrence(),
                customer
        );
        paymentClient.requestOrderPayment(paymentRequest);


//        send the order confirmation --> notification microservice (kafka)
        orderProducer.sendOrderConfirmation(
                new OrderConfirmation(
                        request.refrence(),
                        request.amount(),
                        request.paymentMethod(),
                        customer,
                        purchaseProducts
                )
        );

        return order.getId();
    }

    public List<OrderResponse> findAll() {
        return repo.findAll()
                .stream()
                .map(mapper::fromOrder)
                .collect(Collectors.toList());
    }

    public OrderResponse findById(Integer orderId) {
        return repo.findById(orderId)
                .map(mapper::fromOrder)
                .orElseThrow(() -> new EntityNotFoundException(String.format("No order found with the provided ID:: %d"+ orderId)));
    }
}
