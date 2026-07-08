package com.dayma.service;

import com.dayma.dto.OrderStatusDto;
import com.dayma.model.OrderStatus;

import java.util.List;

public interface OrderStatusService {

    OrderStatus getByCode(String code);
    List<OrderStatusDto> getAll();
    OrderStatus create(String status, String code);
}
