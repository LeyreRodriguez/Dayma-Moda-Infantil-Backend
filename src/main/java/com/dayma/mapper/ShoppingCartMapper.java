package com.dayma.mapper;

import com.dayma.dto.FavouriteDto;
import com.dayma.dto.ShoppingCartDto;
import com.dayma.model.Favourite;
import com.dayma.model.ShoppingCart;
import org.mapstruct.Mapper;

import java.util.List;


@Mapper(componentModel = "spring", uses = SizeMapper.class)
public interface ShoppingCartMapper {

    List<ShoppingCartDto> toDtoList(List<ShoppingCart> shoppingCarts);
    ShoppingCartDto toDto(ShoppingCart shoppingCart);
}
