package com.jaytech.ecommerce.kafka;

import com.jaytech.ecommerce.customer.CustomerResponse;
import com.jaytech.ecommerce.order.PaymentMethod;
import com.jaytech.ecommerce.product.PurchaseResponse;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation(
        String orderRefrence,
        BigDecimal totalAmout,
        PaymentMethod paymentMethod,
        CustomerResponse customer,
        List<PurchaseResponse> products
) {
}
