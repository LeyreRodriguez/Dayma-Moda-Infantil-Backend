package com.dayma.service;

import com.dayma.dto.AddProductRequest;
import com.dayma.dto.ProductOrderDto;
import com.dayma.dto.ShoppingCartDto;

import java.util.List;

public interface ShoppingCartService {
    void addShoppingCart(AddProductRequest shoppingCart);
    void updateShoppingCart(AddProductRequest shoppingCart);
    void deleteFromShoppingCart(String productCode, String sizeCode);
    List<ShoppingCartDto> getShoppingCart();

    List<ProductOrderDto> finishPurchase();
}
