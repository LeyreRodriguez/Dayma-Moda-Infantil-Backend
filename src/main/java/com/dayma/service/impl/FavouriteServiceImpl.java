package com.dayma.service.impl;

import com.dayma.dto.FavouriteDto;
import com.dayma.dto.ProductDto;
import com.dayma.mapper.FavouriteMapper;
import com.dayma.mapper.ProductMapper;
import com.dayma.model.Favourite;
import com.dayma.model.User;
import com.dayma.repository.FavouriteRepository;
import com.dayma.service.FavouritesService;
import com.dayma.service.ProductService;
import com.dayma.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FavouriteServiceImpl implements FavouritesService {

    private final ProductService productService;
    private final UserService userService;
    private final FavouriteRepository favouriteRepository;
    private final ProductMapper productMapper;
    private final FavouriteMapper favouriteMapper;

    @Override
    public void setFavourite(String productCode) {
        ProductDto product = productService.getProduct(productCode);
        User user = userService.getCurrentUser();

        Favourite fav = Favourite.builder()
                .product(productMapper.toEntity(product))
                .appUser(user)
                .build();

        favouriteRepository.save(fav);

    }

    @Override
    public void deleteFavourites(String productCode) {
        User user = userService.getCurrentUser();

        Favourite fav = favouriteRepository.findByAppUserEmailAndProductCode(user.getEmail(), productCode);


        favouriteRepository.delete(fav);
    }

    @Override
    public List<FavouriteDto> getFavourites() {

        return favouriteMapper.toDtoList(favouriteRepository.findByAppUserEmail(userService.getCurrentUser().getEmail()));
    }
}
