package com.dayma.controller;

import com.dayma.dto.*;
import com.dayma.dto.response.GenericResponseDto;
import com.dayma.service.FavouritesService;
import com.dayma.service.ShoppingCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequestMapping("/api/shoppingCart")
@RestController
@RequiredArgsConstructor
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;

    @GetMapping
    public ResponseEntity<GenericResponseDto<List<ShoppingCartDto>>> getShoppingCart() {
        return ResponseEntity.ok(new GenericResponseDto<>(shoppingCartService.getShoppingCart()));
    }


    @PostMapping
    public ResponseEntity<GenericResponseDto<?>> addShoppingCart(@RequestBody AddProductRequest shoppingCart) {
        shoppingCartService.addShoppingCart(shoppingCart);
        return ResponseEntity.ok(new GenericResponseDto<>());
    }

    @PutMapping
    public ResponseEntity<GenericResponseDto<?>> updateShoppingCart(@RequestBody AddProductRequest shoppingCart) {
        shoppingCartService.updateShoppingCart(shoppingCart);
        return ResponseEntity.ok(new GenericResponseDto<>());
    }

    @DeleteMapping("/{code}/{sizeCode}")
    public ResponseEntity<GenericResponseDto<?>> deleteFromShoppingCart(
            @PathVariable String code,
            @PathVariable String sizeCode) {
        shoppingCartService.deleteFromShoppingCart(code, sizeCode);
        return ResponseEntity.ok(new GenericResponseDto<>());
    }


    @PostMapping("/finish")
    public ResponseEntity<GenericResponseDto<List<ProductOrderDto>>> finishPurchase() {
        List<ProductOrderDto> result = shoppingCartService.finishPurchase();
        return ResponseEntity.ok(new GenericResponseDto<>(result));
    }

}