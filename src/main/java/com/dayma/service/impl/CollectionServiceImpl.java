package com.dayma.service.impl;

import com.dayma.dto.CollectionDto;
import com.dayma.dto.FeaturedCollectionDto;
import com.dayma.mapper.CollectionMapper;
import com.dayma.model.Collection;
import com.dayma.model.Product;
import com.dayma.repository.CollectionRepository;
import com.dayma.service.CollectionService;
import com.dayma.service.ProductCollectionService;
import lombok.RequiredArgsConstructor;
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

}
