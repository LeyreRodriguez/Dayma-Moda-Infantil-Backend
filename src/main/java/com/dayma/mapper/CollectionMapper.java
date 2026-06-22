package com.dayma.mapper;

import com.dayma.dto.CollectionDto;
import com.dayma.model.Collection;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CollectionMapper {

    List<CollectionDto> toDtoList(List<Collection> collections);
    CollectionDto toDto(Collection collection);
}
