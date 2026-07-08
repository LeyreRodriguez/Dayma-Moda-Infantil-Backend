package com.dayma.repository;

import com.dayma.model.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CollectionRepository extends JpaRepository<Collection, Long> {

    Collection findByFeaturedTrue();

    Collection findByCode(String code);
}
