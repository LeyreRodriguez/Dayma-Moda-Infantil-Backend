package com.dayma.mapper;

import com.dayma.dto.ProductDto;
import com.dayma.model.Product;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductDto toDto(Product product);

    Product toEntity(ProductDto productDTO);

    List<ProductDto> toDtoList(List<Product> products);
}