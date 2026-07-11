package com.dayma.service.impl;

import com.dayma.dto.CollectionDto;
import com.dayma.dto.FeaturedCollectionDto;
import com.dayma.mapper.CollectionMapper;
import com.dayma.model.Collection;
import com.dayma.model.Product;
import com.dayma.repository.CollectionRepository;
import com.dayma.service.CollectionService;
import com.dayma.service.ProductCollectionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CollectionServiceImpl implements CollectionService {

    private final CollectionRepository collectionRepository;
    private final CollectionMapper collectionMapper;
    private final ProductCollectionService productCollectionService;

    @Override
    public List<CollectionDto> getAll() {
        return collectionMapper.toDtoList(collectionRepository.findAll());
    }

    @Override
    public FeaturedCollectionDto getFeaturedCollection() {

        Collection collection = collectionRepository.findByFeaturedTrue();

        Product product = productCollectionService.getRandomProductByCollection(collection);

        return FeaturedCollectionDto.builder()
                .code(collection.getCode())
                .name(collection.getName())
                .description(collection.getDescription())
                .imageUrl(product.getImageUrl())
                .build();

    }

    @Override
    @Transactional
    public CollectionDto create(CollectionDto collection) {

        if(collection.getFeatured()){
            Collection featured = collectionRepository.findByFeaturedTrue();
            featured.setFeatured(false);
            collectionRepository.save(featured);

        }
        Collection col = new Collection();

        col.setDescription(collection.getDescription());
        col.setName(collection.getName());
        col.setFeatured(collection.getFeatured());

        col = collectionRepository.save(col);

        col.setCode("CL-" + col.getId());

        return collectionMapper.toDto(col);

    }

    @Override
    @Cacheable("collections")
    public Collection getCollection(String collectionCode) {
        return collectionRepository.findByCode(collectionCode);
    }

}
