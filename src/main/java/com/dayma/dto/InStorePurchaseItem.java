package com.dayma.dto;

public record InStorePurchaseItem(
        String productCode,
        String sizeCode,
        int quantity
) {
}
