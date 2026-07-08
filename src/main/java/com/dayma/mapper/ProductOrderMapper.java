package com.dayma.mapper;

import com.dayma.dto.OrderDto;
import com.dayma.dto.ProductOrderDto;
import com.dayma.model.Order;
import com.dayma.model.ProductOrder;
import org.mapstruct.Mapper;

import java.util.List;


@Mapper(componentModel = "spring", uses = SizeMapper.class)
public interface ProductOrderMapper {

    List<ProductOrderDto> toDtoList(List<ProductOrder> productOrders);
    ProductOrderDto toDto(ProductOrder productOrders);
}