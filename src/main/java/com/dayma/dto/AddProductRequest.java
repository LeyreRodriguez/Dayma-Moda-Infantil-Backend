package com.dayma.dto;

import lombok.Data;


@Data
public class AddProductRequest {
    private String code;
    private Integer quantity;
    private String sizeCode;
}