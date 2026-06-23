package com.dayma.mapper;

import com.dayma.dto.FavouriteDto;
import com.dayma.model.Favourite;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FavouriteMapper {

    List<FavouriteDto> toDtoList(List<Favourite> favourites);
    FavouriteDto toDto(Favourite favourite);
}
