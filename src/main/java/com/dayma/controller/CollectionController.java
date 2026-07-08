package com.dayma.controller;

import com.dayma.dto.CollectionDto;
import com.dayma.dto.FeaturedCollectionDto;
import com.dayma.dto.response.GenericResponseDto;
import com.dayma.service.CollectionService;
import com.dayma.service.ProductCollectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/collections")
@RestController
@RequiredArgsConstructor
public class CollectionController {

    private final CollectionService collectionService;
    private final ProductCollectionService productCollectionService;

    @GetMapping
    public ResponseEntity<GenericResponseDto<List<CollectionDto>>> getAllCollections() {
        return ResponseEntity.ok(new GenericResponseDto<>(collectionService.getAll()));
    }

    @GetMapping("/featured")
    public ResponseEntity<GenericResponseDto<FeaturedCollectionDto>> getFeaturedCollection() {
        return ResponseEntity.ok(new GenericResponseDto<>(collectionService.getFeaturedCollection()));
    }

    @GetMapping("/{productCode}")
    public ResponseEntity<GenericResponseDto<CollectionDto>> getCollectionByProduct(@PathVariable String productCode) {
        return ResponseEntity.ok(new GenericResponseDto<>(productCollectionService.getCollectionByProduct(productCode)));
    }

    @PostMapping
    public ResponseEntity<GenericResponseDto<CollectionDto>> create(@RequestBody CollectionDto collection) {
        return ResponseEntity.ok(new GenericResponseDto<>(collectionService.create(collection)));
    }
}
