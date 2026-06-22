package com.dayma.mapper;

import com.dayma.dto.ProductDto;
import com.dayma.dto.ProductImageDto;
import com.dayma.model.Product;
import com.dayma.model.ProductImage;
import org.mapstruct.Mapper;

import java.util.List;


@Mapper(componentModel = "spring")
public interface ProductImageMapper {

    ProductImageDto toDto(ProductImage productImage);

    ProductImage toEntity(ProductImageDto productImage);

    List<ProductImageDto> toDtoList(List<ProductImage> productImage);
}