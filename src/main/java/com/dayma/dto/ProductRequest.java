package com.dayma.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
    private String name;
    private String description;
    private Double price;
    private String category;
    private List<SizeDto> sizes;
    private List<MultipartFile> images;
    private Boolean isNew;
}
