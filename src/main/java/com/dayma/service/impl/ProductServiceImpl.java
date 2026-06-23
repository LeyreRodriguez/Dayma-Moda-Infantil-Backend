package com.dayma.service.impl;

import com.dayma.dto.ProductDto;
import com.dayma.dto.ProductFiltersRequest;
import com.dayma.mapper.ProductMapper;
import com.dayma.model.Product;
import com.dayma.repository.ProductRepository;
import com.dayma.service.ProductService;
import com.dayma.service.UserService;
import com.dayma.specification.ProductSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final UserService userService;
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

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
        return productMapper.toDto(productRepository.findByCode(code));
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
