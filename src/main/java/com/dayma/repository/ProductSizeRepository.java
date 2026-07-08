package com.dayma.repository;

import com.dayma.model.Product;
import com.dayma.model.ProductSize;
import com.dayma.model.Size;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductSizeRepository extends JpaRepository<ProductSize, Long> {
    List<ProductSize> findByProductCode(String productCode);

    Optional<ProductSize> findByProductAndSize(Product product, Size size);

    long countByProductArchivedFalseAndStockLessThan(Integer stock);
}
