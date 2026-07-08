package com.dayma.dto;

import java.util.List;

public record InStorePurchaseRequest(
        List<InStorePurchaseItem> items
) {
}
