package com.dayma.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PendingOrderDto {
    private Long id;
    private String customerName;
    private String initials;
    private String orderCode;
    private long items;
    private String status;
}
