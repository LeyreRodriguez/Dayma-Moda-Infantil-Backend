package com.dayma.repository;

import com.dayma.model.Favourite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavouriteRepository extends JpaRepository<Favourite, Long> {

    List<Favourite> findByAppUserEmail(String email);

    Favourite findByAppUserEmailAndProductCode(String email, String productCode);
}
