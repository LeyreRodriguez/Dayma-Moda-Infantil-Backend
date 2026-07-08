package com.dayma.dto;

import lombok.Data;

@Data
public class AddSizeToProductRequest
{
    private String sizeCode;
    private Integer stock;
}
