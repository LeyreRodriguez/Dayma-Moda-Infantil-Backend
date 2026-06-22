package com.dayma.dto;

import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FeaturedCollectionDto {
    private String name;
    private String description;
    private String code;
    private String imageUrl;
}
