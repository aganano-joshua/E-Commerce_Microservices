package com.jaytech.ecommerce.payment;

import com.jaytech.ecommerce.customer.CustomerResponse;
import com.jaytech.ecommerce.order.PaymentMethod;

import java.math.BigDecimal;

public record PaymentRequest(
        BigDecimal amount,
        PaymentMethod paymentMethod,
        Integer orderId,
        String orderRefrence,
        CustomerResponse customer
) {
}
