package com.dayma.service.impl;

import com.dayma.dto.ProductImageDto;
import com.dayma.mapper.ProductImageMapper;
import com.dayma.model.Product;
import com.dayma.model.ProductImage;
import com.dayma.repository.ProductImageRepository;
import com.dayma.service.ProductImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductImageServiceImpl implements ProductImageService {

    private final ProductImageRepository productImageRepository;
    private final ProductImageMapper productImageMapper;

    @Override
    public List<ProductImageDto> getProductImages(String productCode) {

        List<ProductImage> productImages = productImageRepository.findByProductCode(productCode);
        return productImageMapper.toDtoList(productImages);
    }

    public ProductImageDto addProductImage(Product product, String url) {
        ProductImage image = ProductImage.builder()
                .product(product)
                .image(url)
                .build();

        ProductImage saved = productImageRepository.save(image);

        return productImageMapper.toDto(saved);
    }


}
