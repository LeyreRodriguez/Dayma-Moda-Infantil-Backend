package com.dayma.service;

import com.dayma.dto.CollectionDto;
import com.dayma.dto.ProductDto;
import com.dayma.model.Collection;
import com.dayma.model.Product;

import java.util.List;

public interface ProductCollectionService {

    Product getRandomProductByCollection(Collection collection);

    CollectionDto getCollectionByProduct(String productCode);

    ProductDto updateCollection(String productCode, String collectionCode);
    ProductDto deleteCollection(String productCode);

}
