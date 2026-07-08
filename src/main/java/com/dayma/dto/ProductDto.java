package com.dayma.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private String id;
    private String name;
    private String shortDescription;
    private String longDescription;
    private Double price;
    private CategoryDto category;
    private String imageUrl;
    private Boolean isNew;
    private Boolean archived;
    private String code;

}
