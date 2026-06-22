package com.dayma.service;

import com.dayma.dto.ProductSizeDto;
import com.dayma.model.ProductSize;

import java.util.List;

public interface ProductSizeService {

    List<ProductSizeDto> getProductSize(String productCode);
}
