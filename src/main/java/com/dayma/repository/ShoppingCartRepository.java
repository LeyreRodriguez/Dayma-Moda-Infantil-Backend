package com.dayma.repository;

import com.dayma.model.Favourite;
import com.dayma.model.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {

    ShoppingCart findByAppUserEmailAndProductCode(String appUser, String productCode);
    ShoppingCart findByAppUserEmailAndProductCodeAndSizeCode(String appUser, String productCode, String sizeCode);
    List<ShoppingCart> findByAppUserEmail(String email);


}
