package com.jaytech.product.product;

import java.math.BigDecimal;

public record ProductPurchaseResponse(
        Integer prouctId,
        String name,
        String description,
        BigDecimal price,
        double quantity
) {
}
