package com.dayma.service.impl;

import com.dayma.dto.ProductSizeDto;
import com.dayma.mapper.ProductSizeMapper;
import com.dayma.model.ProductImage;
import com.dayma.model.ProductSize;
import com.dayma.repository.ProductSizeRepository;
import com.dayma.service.ProductSizeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductSizeServiceImpl implements ProductSizeService {

    private final ProductSizeRepository productSizeRepository;
    private final ProductSizeMapper productSizeMapper;
    @Override
    public List<ProductSizeDto> getProductSize(String productCode) {
        List<ProductSize> productImages = productSizeRepository.findByProductCode(productCode);
        return productSizeMapper.toDtoList(productImages);
    }
}
