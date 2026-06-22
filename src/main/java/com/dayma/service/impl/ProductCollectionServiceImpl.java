package com.dayma.service.impl;

import com.dayma.dto.CollectionDto;
import com.dayma.exception.NoProductsInCollectionException;
import com.dayma.mapper.CollectionMapper;
import com.dayma.model.Collection;
import com.dayma.model.Product;
import com.dayma.model.ProductCollection;
import com.dayma.repository.ProductCollectionRepository;
import com.dayma.service.ProductCollectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;


@Service
@RequiredArgsConstructor
public class ProductCollectionServiceImpl implements ProductCollectionService  {

    private final ProductCollectionRepository productCollectionRepository;
    private final CollectionMapper collectionMapper;

    @Override
    public Product getRandomProductByCollection(Collection collection) {

        List<ProductCollection> productCollections =
                productCollectionRepository.findByCollectionId(collection.getId());

        if (productCollections.isEmpty()) {
            throw new NoProductsInCollectionException(
                    "No hay productos para la colección: " + collection.getName()
            );
        }

        int randomIndex = ThreadLocalRandom.current()
                .nextInt(productCollections.size());

        return productCollections.get(randomIndex).getProduct();
    }

    @Override
    public CollectionDto getCollectionByProduct(String productCode) {

        Collection collection = productCollectionRepository.findByProductCode(productCode).getFirst().getCollection();
        return collectionMapper.toDto(collection);

    }
}
