package com.dayma.service;

import com.dayma.dto.ProductImageDto;
import com.dayma.model.Product;

import java.util.List;

public interface ProductImageService {

    List<ProductImageDto> getProductImages(String productCode);
    ProductImageDto addProductImage(Product product, String url);
}
