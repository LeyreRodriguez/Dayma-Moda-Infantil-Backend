package com.dayma.mapper;


import com.dayma.dto.OrderStatusDto;
import com.dayma.model.OrderStatus;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderStatusMapper {

    List<OrderStatusDto> toDtoList(List<OrderStatus> orderStatus);
    OrderStatusDto toDto(OrderStatus orderStatus);
}