package com.dayma.service.impl;

import com.dayma.dto.ProductOrderDto;
import com.dayma.mapper.ProductOrderMapper;
import com.dayma.model.Order;
import com.dayma.model.ProductOrder;
import com.dayma.repository.ProductOrderRepository;
import com.dayma.service.ProductOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductOrderServiceImpl implements ProductOrderService {
    private final ProductOrderMapper productOrderMapper;
    private final ProductOrderRepository productOrderRepository;

    @Override
    public List<ProductOrderDto> getProductOrder(String orderCode) {
        return productOrderMapper.toDtoList(productOrderRepository.findByOrderCode(orderCode));
    }

    @Override
    public long countByOrder(Order order) {
        return productOrderRepository.countByOrder(order);
    }

    @Override
    public ProductOrder save(ProductOrder productOrder) {
        return productOrderRepository.save(productOrder);
    }

    @Override
    public List<ProductOrder> saveAll(List<ProductOrder> productOrders) {
        return productOrderRepository.saveAll(productOrders);
    }
}
