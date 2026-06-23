package com.dayma.controller;

import com.dayma.dto.*;
import com.dayma.dto.response.GenericResponseDto;
import com.dayma.dto.response.PageResponseDto;
import com.dayma.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/products")
@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ProductImageService productImageService;
    private final ProductSizeService productSizeService;
    private final ProductOrderService productOrderService;

    @GetMapping
    public ResponseEntity<GenericResponseDto<PageResponseDto<ProductDto>>> getProducts(
            ProductFiltersRequest filters,
            @PageableDefault(size = 20) Pageable pageable) {

        Page<ProductDto> page = productService.getProducts(filters, pageable);

        PageResponseDto<ProductDto> pageResponse = PageResponseDto.<ProductDto>builder()
                .content(page.getContent())
                .pageNumber(page.getNumber())
                .pageSize(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .build();

        return ResponseEntity.ok(new GenericResponseDto<>(pageResponse));
    }


    @GetMapping("/{code}")
    public ResponseEntity<GenericResponseDto<ProductDto>> getProduct(@PathVariable String code) {
        return ResponseEntity.ok(new GenericResponseDto<>(productService.getProduct(code)));
    }

    @GetMapping("/images/{code}")
    public ResponseEntity<GenericResponseDto<List<ProductImageDto>>> getProductImages(@PathVariable String code) {
        return ResponseEntity.ok(new GenericResponseDto<>(productImageService.getProductImages(code)));
    }


    @GetMapping("/sizes/{code}")
    public ResponseEntity<GenericResponseDto<List<ProductSizeDto>>> getProductSize(@PathVariable String code) {
        return ResponseEntity.ok(new GenericResponseDto<>(productSizeService.getProductSize(code)));
    }

    @GetMapping("/order/{orderCode}")
    public ResponseEntity<GenericResponseDto<List<ProductOrderDto>>> getProductOrder(@PathVariable String orderCode) {
        return ResponseEntity.ok(new GenericResponseDto<>(productOrderService.getProductOrder(orderCode)));
    }






}
