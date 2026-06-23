package com.dayma.service;

import com.dayma.dto.SizeDto;

import java.util.List;

public interface SizeService {
    List<SizeDto> getAll();
    SizeDto getByCode(String code);
}
