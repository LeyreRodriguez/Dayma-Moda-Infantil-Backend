package com.dayma.service.impl;

import com.dayma.dto.CollectionDto;
import com.dayma.dto.ProductDto;
import com.dayma.exception.NoProductsInCollectionException;
import com.dayma.exception.ProductsNotFoundException;
import com.dayma.mapper.CollectionMapper;
import com.dayma.mapper.ProductMapper;
import com.dayma.model.Collection;
import com.dayma.model.Product;
import com.dayma.model.ProductCollection;
import com.dayma.repository.CollectionRepository;
import com.dayma.repository.ProductCollectionRepository;
import com.dayma.service.CollectionService;
import com.dayma.service.ProductCollectionService;
import com.dayma.service.ProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;


@Service
@RequiredArgsConstructor
public class ProductCollectionServiceImpl implements ProductCollectionService  {

    private final ProductCollectionRepository productCollectionRepository;
    private final ProductService productService;
    private final CollectionRepository collectionRepository;
    private final CollectionMapper collectionMapper;
    private final ProductMapper productMapper;

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

        List<ProductCollection> productCollections = productCollectionRepository.findByProductCode(productCode);
        if (productCollections.isEmpty()) {
            return null;
        }
        return collectionMapper.toDto(productCollections.getFirst().getCollection());

    }


    @Override
    @Transactional
    public ProductDto updateCollection(String productCode, String collectionCode) {
        Product product = productService.getEntityProduct(productCode);

        if(product == null) throw new ProductsNotFoundException(productCode);

        Collection collection = collectionRepository.findByCode(collectionCode);
        if (collection == null) {
            throw new RuntimeException("Collection not found: " + collectionCode);
        }

        ProductCollection productCollection = ProductCollection.builder()
                .product(product)
                .collection(collection)
                .build();

        productCollectionRepository.save(productCollection);

        return productMapper.toDto(product);
    }

    @Override
    @Transactional
    public ProductDto deleteCollection(String productCode) {
        Product product = productService.getEntityProduct(productCode);

        if(product == null) throw new ProductsNotFoundException(productCode);


        productCollectionRepository.deleteByProductCode(productCode);

        return productMapper.toDto(product);
    }
}
