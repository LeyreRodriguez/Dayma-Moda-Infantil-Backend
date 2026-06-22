package com.dayma.service;

import com.dayma.dto.CollectionDto;
import com.dayma.model.Collection;
import com.dayma.model.Product;

import java.util.List;

public interface ProductCollectionService {

    Product getRandomProductByCollection(Collection collection);

    CollectionDto getCollectionByProduct(String productCode);

}
