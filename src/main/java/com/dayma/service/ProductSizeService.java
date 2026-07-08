package com.dayma.service;

import com.dayma.dto.AddSizeToProductRequest;
import com.dayma.dto.ProductSizeDto;
import com.dayma.dto.UpdateStockRequest;
import com.dayma.model.Product;
import com.dayma.model.ProductSize;
import com.dayma.model.Size;

import java.util.List;

public interface ProductSizeService {

    List<ProductSizeDto> getProductSize(String productCode);

    ProductSizeDto updateSizeStock(String productCode, String sizeCode, UpdateStockRequest request);
    ProductSizeDto addSizeToProduct(String productCode, AddSizeToProductRequest request);
    List<ProductSizeDto> addSizesToProduct(String productCode, List<AddSizeToProductRequest> requests);

    ProductSize getByProductAndSize(Product product, Size size);
    ProductSize save(ProductSize productSize);

}
