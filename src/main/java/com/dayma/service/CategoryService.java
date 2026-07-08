package com.dayma.service;


import com.dayma.dto.CategoryDto;
import com.dayma.model.Category;

import java.util.List;

public interface CategoryService {
    public List<CategoryDto> getAll();

    Category getByCode(String code);
}
