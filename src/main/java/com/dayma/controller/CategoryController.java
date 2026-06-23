package com.dayma.controller;

import com.dayma.dto.CategoryDto;
import com.dayma.dto.response.GenericResponseDto;
import com.dayma.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RequestMapping("/api/category")
@RestController
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<GenericResponseDto<List<CategoryDto>>> getAllCategories() {
        return ResponseEntity.ok(new GenericResponseDto<>(categoryService.getAll()));
    }
}