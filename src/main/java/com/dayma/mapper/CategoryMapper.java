package com.dayma.mapper;

import com.dayma.dto.CategoryDto;
import com.dayma.model.Category;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    List<CategoryDto> toDtoList(List<Category> categories);
}
