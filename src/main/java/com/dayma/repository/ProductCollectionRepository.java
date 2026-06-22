package com.dayma.repository;

import com.dayma.model.Collection;
import com.dayma.model.ProductCollection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductCollectionRepository extends JpaRepository<ProductCollection, Long> {


    List<ProductCollection> findByCollectionId(Long collectionId);

    List<ProductCollection> findByProductCode(String productCode);
}
