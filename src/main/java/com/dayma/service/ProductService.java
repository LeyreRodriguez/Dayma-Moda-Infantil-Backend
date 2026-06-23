package com.dayma.service;

import com.dayma.dto.ProductDto;
import com.dayma.dto.ProductFiltersRequest;
import com.dayma.dto.ProductImageDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    Page<ProductDto> getProducts(ProductFiltersRequest filters, Pageable pageable);
    ProductDto getProduct(String code);

}
