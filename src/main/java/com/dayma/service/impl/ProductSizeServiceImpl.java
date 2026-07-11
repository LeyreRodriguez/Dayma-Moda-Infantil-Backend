package com.dayma.service.impl;

import com.dayma.dto.AddSizeToProductRequest;
import com.dayma.dto.ProductDto;
import com.dayma.dto.ProductSizeDto;
import com.dayma.dto.UpdateStockRequest;
import com.dayma.mapper.ProductSizeMapper;
import com.dayma.model.Product;
import com.dayma.model.ProductImage;
import com.dayma.model.ProductSize;
import com.dayma.model.Size;
import com.dayma.repository.ProductSizeRepository;
import com.dayma.service.ProductService;
import com.dayma.service.ProductSizeService;
import com.dayma.service.SizeService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProductSizeServiceImpl implements ProductSizeService {

    private final ProductSizeRepository productSizeRepository;
    private final SizeService sizeService;
    private final ProductSizeMapper productSizeMapper;

    private ProductService productService; // no final

    @Autowired
    @Lazy
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public List<ProductSizeDto> getProductSize(String productCode) {
        List<ProductSize> productImages = productSizeRepository.findByProductCode(productCode);
        return productSizeMapper.toDtoList(productImages);
    }

    @Override
    public ProductSizeDto updateSizeStock(String productCode, String sizeCode, UpdateStockRequest request) {
        Product product = productService.getEntityProduct(productCode);
        Size size = sizeService.getEntityByCode(sizeCode);

        ProductSize productSize = productSizeRepository.findByProductAndSize(product, size).get();

        productSize.setStock(request.stock());

        productSizeRepository.save(productSize);

        return productSizeMapper.toDto(productSize);

    }

    @Override
    public ProductSizeDto addSizeToProduct(String productCode, AddSizeToProductRequest request) {

        Product product = productService.getEntityProduct(productCode);
        Size size = sizeService.getEntityByCode(request.getSizeCode());

        Optional<ProductSize> productSizeOpt =
                productSizeRepository.findByProductAndSize(product, size);

        ProductSize productSize;

        if (productSizeOpt.isPresent()) {
            productSize = productSizeOpt.get();
            productSize.setStock(request.getStock());
        } else {
            productSize = ProductSize.builder()
                    .product(product)
                    .size(size)
                    .stock(request.getStock())
                    .build();
        }

        productSizeRepository.save(productSize);

        return productSizeMapper.toDto(productSize);
    }

    @Override
    @Transactional
    public List<ProductSizeDto> addSizesToProduct(String productCode, List<AddSizeToProductRequest> requests) {

        if (requests == null || requests.isEmpty()) {
            return List.of();
        }

        Product product = productService.getEntityProduct(productCode);

        // 1 sola query para todas las tallas, en vez de N llamadas a getEntityByCode
        List<String> sizeCodes = requests.stream()
                .map(AddSizeToProductRequest::getSizeCode)
                .toList();

        Map<String, Size> sizesByCode = sizeService.getEntitiesByCodeIn(sizeCodes).stream()
                .collect(Collectors.toMap(Size::getCode, Function.identity()));

        for (String code : sizeCodes) {
            if (!sizesByCode.containsKey(code)) {
                throw new EntityNotFoundException("Talla no encontrada: " + code);
            }
        }




        Map<String, ProductSize> existingByCode = productSizeRepository.findByProductCode(product.getCode()).stream()
                .collect(Collectors.toMap(ps -> ps.getSize().getCode(), Function.identity()));

        List<ProductSize> toSave = requests.stream()
                .map(req -> {
                    Size size = sizesByCode.get(req.getSizeCode());
                    ProductSize existing = existingByCode.get(req.getSizeCode());

                    if (existing != null) {
                        existing.setStock(req.getStock());
                        return existing;
                    } else {
                        return ProductSize.builder()
                                .product(product)
                                .size(size)
                                .stock(req.getStock())
                                .build();
                    }
                })
                .toList();

        List<ProductSize> saved = productSizeRepository.saveAll(toSave);

        return saved.stream()
                .map(productSizeMapper::toDto)
                .toList();
    }

    @Override
    public ProductSize getByProductAndSize(Product product, Size size) {
        return productSizeRepository
                    .findByProductAndSize(product, size)
                .orElseThrow(() -> new RuntimeException("Stock no encontrado para " + product.getCode()));
    }

    @Override
    public ProductSize save(ProductSize productSize) {
        return productSizeRepository.save(productSize);
    }

    @Override
    public List<ProductSize> saveAll(List<ProductSize> productSizes) {
        return productSizeRepository.saveAll(productSizes);
    }
}
