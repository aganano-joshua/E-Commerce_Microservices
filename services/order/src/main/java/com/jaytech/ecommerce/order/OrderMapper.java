package com.jaytech.ecommerce.order;

import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

@Service
public class OrderMapper {

    public Order toOrder(@Valid OrderRequest request) {
        return Order.builder()
                .id(request.id())
                .customeId(request.customerId())
                .refrence(request.refrence())
                .totalAmount(request.amount())
                .paymentMethod(request.paymentMethod())
                .build();
    }
}
