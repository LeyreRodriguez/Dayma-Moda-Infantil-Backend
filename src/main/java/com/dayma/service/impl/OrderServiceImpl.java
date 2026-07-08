package com.dayma.service.impl;

import com.dayma.dto.ChangeStatusRequest;
import com.dayma.dto.OrderDto;
import com.dayma.dto.OrderStatusDto;
import com.dayma.mapper.OrderMapper;
import com.dayma.model.Order;
import com.dayma.model.OrderStatus;
import com.dayma.model.User;
import com.dayma.repository.OrderRepository;
import com.dayma.service.OrderService;
import com.dayma.service.OrderStatusService;
import com.dayma.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderMapper orderMapper;
    private final OrderRepository orderRepository;
    private final UserService userService;
    private final OrderStatusService orderStatusService;

    @Override
    public List<OrderDto> getOrders() {
        User user = userService.getCurrentUser();
        List<Order> orders= orderRepository.findByAppUserEmail(user.getEmail());
        return orderMapper.toDtoList(orders);

    }

    @Override
    public List<OrderDto> getAllOrders() {
        List<Order> orders= orderRepository.findAll();
        return orderMapper.toDtoList(orders);
    }

    @Override
    public List<OrderStatusDto> getOrderStatus() {
        return orderStatusService.getAll();
    }

    @Override
    public OrderDto updateStatus(String code, ChangeStatusRequest dto) {

        Order order = orderRepository.findByCode(code);

        OrderStatus state = orderStatusService.getByCode(dto.statusCode());

        order.setStatus(state);

        orderRepository.save(order);

        return orderMapper.toDto(order);
    }

    @Override
    public List<Order> getOrdersByStatus(OrderStatus status) {
        return orderRepository.findByStatus(status);
    }

    @Override
    public long countOrdersBetween(LocalDateTime start, LocalDateTime end) {
        return orderRepository.countByDateBetween(start, end);
    }

    @Override
    public long countDeliveredOrdersBetween(String excludeCode, LocalDateTime start, LocalDateTime end) {
        return orderRepository.countDeliveredOrdersBetween(excludeCode, start, end);
    }

    @Override
    public Order findByCode(String code) {
        return orderRepository.findByCode(code);
    }

    @Override
    public Order save(Order order) {
        return orderRepository.save(order);
    }
}
