package com.dayma.mapper;

import com.dayma.dto.ProductImageDto;
import com.dayma.dto.ProductSizeDto;
import com.dayma.model.ProductImage;
import com.dayma.model.ProductSize;
import org.mapstruct.Mapper;

import java.util.List;


@Mapper(componentModel = "spring")
public interface ProductSizeMapper {

    ProductSizeDto toDto(ProductSize productSize);

    ProductSize toEntity(ProductSizeDto productSize);

    List<ProductSizeDto> toDtoList(List<ProductSize> productSize);
}