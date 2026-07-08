package com.dayma.dto;


public record ProductFiltersRequest (
        String categories,
        String sizes,
        String minPrice,
        String maxPrice,
        String limit,
        String sortBy,
        String collection,
        String name,
        String code,
        Boolean archived
){
}
