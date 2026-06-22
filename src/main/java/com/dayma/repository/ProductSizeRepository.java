package com.dayma.repository;

import com.dayma.model.ProductImage;
import com.dayma.model.ProductSize;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductSizeRepository extends JpaRepository<ProductSize, Long> {
    List<ProductSize> findByProductCode(String productCode);

}
