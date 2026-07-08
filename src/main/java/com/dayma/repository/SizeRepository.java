package com.dayma.repository;

import com.dayma.model.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SizeRepository extends JpaRepository<Size, Long> {

    Size findByCode(String code);
    List<Size> findByCodeIn(List<String> sizes);
}
