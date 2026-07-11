package com.dayma.service;

import com.dayma.dto.ProductOrderDto;
import com.dayma.model.Order;
import com.dayma.model.ProductOrder;

import java.util.List;

public interface ProductOrderService {

    List<ProductOrderDto> getProductOrder(String orderCode);
    long countByOrder(Order order);
    ProductOrder save(ProductOrder productOrder);
    List<ProductOrder> saveAll(List<ProductOrder> productOrders);
}
