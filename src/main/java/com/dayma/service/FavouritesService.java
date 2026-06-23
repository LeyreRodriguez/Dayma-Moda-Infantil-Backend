package com.dayma.service;

import com.dayma.dto.FavouriteDto;

import java.util.List;

public interface FavouritesService {
    void setFavourite(String productCode);
    void deleteFavourites(String productCode);

    List<FavouriteDto> getFavourites();
}
