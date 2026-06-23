package com.dayma.service.impl;

import com.dayma.dto.SizeDto;
import com.dayma.mapper.SizeMapper;
import com.dayma.repository.SizeRepository;
import com.dayma.service.SizeService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SizeServiceImpl implements SizeService {
    private final SizeRepository sizeRepository;
    private final SizeMapper sizeMapper;

    @Override
    public List<SizeDto> getAll() {
        return sizeMapper.toDtoList(sizeRepository.findAll());
    }

    @Override
    public SizeDto getByCode(String code) {
        return sizeMapper.toDto(sizeRepository.findByCode(code));
    }


}
