package com.dayma.controller;

import com.dayma.dto.*;
import com.dayma.dto.response.GenericResponseDto;
import com.dayma.dto.response.PageResponseDto;
import com.dayma.service.FavouritesService;
import com.dayma.service.ProductImageService;
import com.dayma.service.ProductService;
import com.dayma.service.ProductSizeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RequestMapping("/api/favourites")
@RestController
@RequiredArgsConstructor
public class FavouriteController {

    private final FavouritesService favouritesService;

    @GetMapping
    public ResponseEntity<GenericResponseDto<List<FavouriteDto>>> getFavourites() {
        return ResponseEntity.ok(new GenericResponseDto<>(favouritesService.getFavourites()));
    }


    @PostMapping
    public ResponseEntity<GenericResponseDto<?>> setFavourites(@RequestBody SetFavouriteRequest favourite) {
        favouritesService.setFavourite(favourite.getCode());
        return ResponseEntity.ok(new GenericResponseDto<>());
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<GenericResponseDto<?>> deleteFavourites(@PathVariable String code) {
        favouritesService.deleteFavourites(code);
        return ResponseEntity.ok(new GenericResponseDto<>());
    }


}
