package com.dayma.service;

import com.dayma.dto.ProductImageDto;

import java.util.List;

public interface ProductImageService {

    List<ProductImageDto> getProductImages(String productCode);
}
