package com.dayma.service.impl;

import com.dayma.dto.CategoryDto;
import com.dayma.mapper.CategoryMapper;
import com.dayma.model.Category;
import com.dayma.repository.CategoryRepository;
import com.dayma.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public List<CategoryDto> getAll() {
        return categoryMapper.toDtoList(categoryRepository.findAll());
    }

    @Override
    public Category getByCode(String code) {
        return categoryRepository.findByCode(code);
    }
}
