package com.dayma.service.impl;

import com.dayma.dto.OrderStatusDto;
import com.dayma.mapper.OrderStatusMapper;
import com.dayma.model.OrderStatus;
import com.dayma.repository.OrderStatusRepository;
import com.dayma.service.OrderStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderStatusServiceImpl implements OrderStatusService {

    private final OrderStatusRepository orderStatusRepository;
    private final OrderStatusMapper orderStatusMapper;

    @Override
    @Cacheable("orderStatuses")
    public OrderStatus getByCode(String code) {
        return orderStatusRepository.findByCode(code);
    }

    @Override
    public List<OrderStatusDto> getAll() {

        return orderStatusMapper.toDtoList(orderStatusRepository.findAll());
    }

    @Override
    public OrderStatus create(String status, String code) {
        return orderStatusRepository.save(OrderStatus.builder()
                .status(status)
                .code(code)
                .build());
    }
}
