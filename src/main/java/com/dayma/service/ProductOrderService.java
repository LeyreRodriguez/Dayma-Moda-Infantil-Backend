package com.dayma.service;

import com.dayma.dto.ProductDto;
import com.dayma.dto.ProductOrderDto;

import java.util.List;

public interface ProductOrderService {

    List<ProductOrderDto> getProductOrder(String orderCode);
}
