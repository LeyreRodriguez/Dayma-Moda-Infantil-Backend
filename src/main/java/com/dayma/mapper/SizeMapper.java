package com.dayma.mapper;

import com.dayma.dto.SizeDto;
import com.dayma.model.Size;
import org.mapstruct.Mapper;

import java.util.List;


@Mapper(componentModel = "spring")
public interface SizeMapper {

    SizeDto toDto(Size product);

    Size toEntity(SizeDto productDTO);

    List<SizeDto> toDtoList(List<Size> products);
}