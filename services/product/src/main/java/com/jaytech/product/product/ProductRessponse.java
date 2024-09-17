package com.jaytech.product.product;

import java.math.BigDecimal;

public record ProductRessponse(
        Integer id,
        String name,
        String description,
        double availableQuantity,
        BigDecimal price,
        Integer categoryId,
        String categoryName,
        String categoryDescription
) {
}
