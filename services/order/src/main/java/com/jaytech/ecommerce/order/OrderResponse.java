package com.jaytech.ecommerce.order;

import java.math.BigDecimal;

public record OrderResponse(
        Integer id,
        String refrence,
        BigDecimal amount,
        PaymentMethod paymentMethod,
        String customerId
) {
}
