package com.dayma.service;

import com.dayma.dto.SizeDto;
import com.dayma.model.Size;

import java.util.List;

public interface SizeService {
    List<SizeDto> getAll();
    SizeDto getByCode(String code);
    Size getEntityByCode(String code);
    List<Size> getEntitiesByCodeIn(List<String> sizes);
}
