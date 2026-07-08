package com.dayma.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventorySummaryDto {
    private long newArrivals;
    private long lowStock;
    private long archived;
}
