package com.dayma.service;

import com.dayma.dto.CollectionDto;
import com.dayma.dto.FeaturedCollectionDto;
import com.dayma.model.Collection;

import java.util.List;

public interface CollectionService {
    List<CollectionDto> getAll();

    FeaturedCollectionDto getFeaturedCollection();

    CollectionDto create(CollectionDto collection);
    Collection getCollection(String collectionCode);


}
