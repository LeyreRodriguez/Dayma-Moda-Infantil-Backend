package com.dayma.service;

import com.dayma.dto.CollectionDto;
import com.dayma.dto.FeaturedCollectionDto;

import java.util.List;

public interface CollectionService {
    List<CollectionDto> getAll();

    FeaturedCollectionDto getFeaturedCollection();
}
