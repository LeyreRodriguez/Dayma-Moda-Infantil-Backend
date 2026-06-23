package com.dayma.service.impl;

import com.dayma.dto.OrderDto;
import com.dayma.mapper.OrderMapper;
import com.dayma.model.Order;
import com.dayma.model.User;
import com.dayma.repository.OrderRepository;
import com.dayma.service.OrderService;
import com.dayma.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderMapper orderMapper;
    private final OrderRepository orderRepository;
    private final UserService userService;

    @Override
    public List<OrderDto> getOrders() {
        User user = userService.getCurrentUser();
        List<Order> orders= orderRepository.findByAppUserEmail(user.getEmail());
        return orderMapper.toDtoList(orders);

    }
}
