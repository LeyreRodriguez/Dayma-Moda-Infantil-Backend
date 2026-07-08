package com.dayma.service.impl;

import com.dayma.dto.*;
import com.dayma.exception.ProductsNotFoundException;
import com.dayma.mapper.ProductMapper;
import com.dayma.model.*;
import com.dayma.repository.CollectionRepository;
import com.dayma.repository.ProductCollectionRepository;
import com.dayma.repository.ProductRepository;
import com.dayma.service.*;
import com.dayma.specification.ProductSpecification;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CategoryService categoryService;
    private final ProductSizeService productSizeService;
    private final CloudinaryService cloudinaryService;
    private final ProductImageService productImageService;

    @Qualifier("uploadExecutor")
    private final ExecutorService uploadExecutor;

    @Override
    public Page<ProductDto> getProducts(ProductFiltersRequest filters, Pageable pageable) {
        Specification<Product> spec = ProductSpecification.withFilters(filters);

        int size = pageable.getPageSize();
        if (filters.limit() != null && !filters.limit().isBlank()) {
            size = Integer.parseInt(filters.limit());
        }

        Sort sort = resolveSort(filters.sortBy(), pageable.getSort());

        Pageable adjustedPageable = PageRequest.of(
                pageable.getPageNumber(), size, sort);

        return productRepository.findAll(spec, adjustedPageable)
                .map(productMapper::toDto);
    }

    @Override
    public ProductDto getProduct(String code) {
        return productMapper.toDto(productRepository.findByCode(code).get());
    }

    @Override
    public Product getEntityProduct(String code) {
        return productRepository.findByCode(code).get();
    }

    @Override
    @Transactional
    public ProductDto createProduct(ProductRequest request) {

        Category cat = categoryService.getByCode(request.getCategory());

        Product product = Product.builder()
                .name(request.getName())
                .longDescription(request.getDescription())
                .price(request.getPrice())
                .category(cat)
                .isNew(request.getIsNew())
                .archived(false)
                .build();

        productRepository.save(product);

        addSizesInBatch(product.getCode(), request.getSizes());

        List<String> url = uploadImagesInParallel(request.getImages());
        addProductImages(product.getCode(), request.getImages());
        product.setCode("PR-" + product.getId());
        product.setImageUrl(url.getFirst());

        return productMapper.toDto(product);
    }

    @Override
    public List<ProductImageDto> addProductImages(String productCode, List<MultipartFile> images) {

        Product product = productRepository.findByCode(productCode)
                .orElseThrow(() -> new ProductsNotFoundException(productCode));

        List<String> imageUrls = uploadImagesInParallel(images);

        return imageUrls.stream()
                .map(url -> productImageService.addProductImage(product, url))
                .toList();
    }

    @Override
    public ProductDto updateProduct(String productCode, Boolean isNew, Boolean archived) {

        Product product = productRepository.findByCode(productCode)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Producto no encontrado: " + productCode));

        if (isNew != null) {
            product.setIsNew(isNew);
        }

        if (archived != null) {
            product.setArchived(archived);
        }

        Product saved = productRepository.save(product);

        return productMapper.toDto(saved);
    }

    @Override
    public Long countByIsNewTrue() {
        return productRepository.countByIsNewTrue();
    }

    @Override
    public Long countByArchivedFalseAndLowStock() {
        return productRepository.countByArchivedFalseAndLowStock();
    }

    @Override
    public Long countByArchivedTrue() {
        return productRepository.countByArchivedTrue();
    }


    private void addSizesInBatch(String productCode, List<SizeDto> sizes) {
        if (sizes == null || sizes.isEmpty()) return;

        List<AddSizeToProductRequest> requests = sizes.stream()
                .map(size -> {
                    AddSizeToProductRequest add = new AddSizeToProductRequest();
                    add.setSizeCode(size.getCode());
                    add.setStock(1);
                    return add;
                })
                .toList();

        productSizeService.addSizesToProduct(productCode, requests);
    }



    private List<String> uploadImagesInParallel(List<MultipartFile> images) {
        if (images == null || images.isEmpty()) {
            return List.of();
        }

        List<CompletableFuture<String>> futures = images.stream()
                .map(file -> CompletableFuture.supplyAsync(
                        () -> cloudinaryService.uploadImage(file),
                        uploadExecutor
                ))
                .toList();

        return futures.stream()
                .map(CompletableFuture::join)
                .toList();
    }


    private Sort resolveSort(String sortBy, Sort defaultSort) {
        if (sortBy != null && !sortBy.isBlank()) {
            if (sortBy.equalsIgnoreCase("newest")) {
                return Sort.by(Sort.Direction.DESC, "insertionDate");
            }

            String field;
            Sort.Direction direction = Sort.Direction.ASC;

            if (sortBy.contains(",")) {
                String[] parts = sortBy.split(",");
                field = parts[0];
                if (parts.length > 1 && parts[1].equalsIgnoreCase("desc")) {
                    direction = Sort.Direction.DESC;
                }
            } else if (sortBy.contains("_")) {
                int idx = sortBy.lastIndexOf("_");
                String possibleDir = sortBy.substring(idx + 1);
                if (possibleDir.equalsIgnoreCase("asc") || possibleDir.equalsIgnoreCase("desc")) {
                    field = sortBy.substring(0, idx);
                    direction = possibleDir.equalsIgnoreCase("desc")
                            ? Sort.Direction.DESC : Sort.Direction.ASC;
                } else {
                    field = sortBy;
                }
            } else {
                field = sortBy;
            }

            return Sort.by(direction, field);
        }

        return defaultSort.isSorted() ? defaultSort : Sort.by("name");
    }
}
