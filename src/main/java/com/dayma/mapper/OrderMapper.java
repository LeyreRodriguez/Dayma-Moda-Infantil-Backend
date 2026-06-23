package com.dayma.mapper;

import com.dayma.dto.FavouriteDto;
import com.dayma.dto.OrderDto;
import com.dayma.model.Favourite;
import com.dayma.model.Order;
import org.mapstruct.Mapper;

import java.util.List;


@Mapper(componentModel = "spring")
public interface OrderMapper {

    List<OrderDto> toDtoList(List<Order> orders);
    OrderDto toDto(Order order);
}