package com.dayma.service.impl;

import com.dayma.dto.ProductOrderDto;
import com.dayma.mapper.ProductOrderMapper;
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
}
