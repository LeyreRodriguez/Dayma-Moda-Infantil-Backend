package com.dayma.repository;

import com.dayma.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    Optional<Product> findByCode(String code);

    long countByIsNewTrue();


    long countByArchivedTrue();

    @Query("SELECT COUNT(p) FROM Product p WHERE p.archived = false AND (" +
           "NOT EXISTS (SELECT ps FROM ProductSize ps WHERE ps.product = p) OR " +
           "(SELECT COALESCE(SUM(ps.stock), 0) FROM ProductSize ps WHERE ps.product = p) < 10)")
    long countByArchivedFalseAndLowStock();
}
