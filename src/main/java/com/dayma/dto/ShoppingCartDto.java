package com.dayma.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingCartDto {
    private ProductDto productDto;
    private UserDto appUser;
    private Integer quantity;
    private SizeDto size;
}
