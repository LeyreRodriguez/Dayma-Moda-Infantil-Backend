package com.dayma.dto;


public record UpdateProductRequest (
        Boolean isNew,
        Boolean archived
)
{}
