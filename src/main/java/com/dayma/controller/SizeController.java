package com.dayma.controller;

import com.dayma.dto.ProductDto;
import com.dayma.dto.ProductFiltersRequest;
import com.dayma.dto.SizeDto;
import com.dayma.dto.response.GenericResponseDto;
import com.dayma.dto.response.PageResponseDto;
import com.dayma.service.ProductService;
import com.dayma.service.SizeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RequestMapping("/api/sizes")
@RestController
@RequiredArgsConstructor
public class SizeController {

    private final SizeService sizeService;

    @GetMapping
    public ResponseEntity<GenericResponseDto<List<SizeDto>>> getAllSizes() {
        return ResponseEntity.ok(new GenericResponseDto<>(sizeService.getAll()));
    }
}
