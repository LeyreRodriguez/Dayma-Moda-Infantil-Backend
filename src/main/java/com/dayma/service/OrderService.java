package com.dayma.service;

import com.dayma.dto.ChangeStatusRequest;
import com.dayma.dto.OrderDto;
import com.dayma.dto.OrderStatusDto;
import com.dayma.model.Order;
import com.dayma.model.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderService {

    List<OrderDto> getOrders();
    List<OrderDto> getAllOrders();
    List<OrderStatusDto> getOrderStatus();
    OrderDto updateStatus(String code, ChangeStatusRequest status);
    List<Order> getOrdersByStatus(OrderStatus status);
    long countOrdersBetween(LocalDateTime start, LocalDateTime end);
    long countDeliveredOrdersBetween(String excludeCode, LocalDateTime start, LocalDateTime end);
    Order findByCode(String code);
    Order save(Order order);
}
