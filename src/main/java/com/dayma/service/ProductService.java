package com.dayma.service;

import com.dayma.dto.ProductDto;
import com.dayma.dto.ProductFiltersRequest;
import com.dayma.dto.ProductImageDto;
import com.dayma.dto.ProductRequest;
import com.dayma.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {
    Page<ProductDto> getProducts(ProductFiltersRequest filters, Pageable pageable);
    ProductDto getProduct(String code);
    Product getEntityProduct(String code);
    ProductDto createProduct(ProductRequest request);
    List<ProductImageDto> addProductImages(String productCode, List<MultipartFile> images);
    ProductDto updateProduct(String productCode, Boolean isNew, Boolean archived);
    Long countByIsNewTrue();
    Long countByArchivedFalseAndLowStock();
    Long countByArchivedTrue();

}
