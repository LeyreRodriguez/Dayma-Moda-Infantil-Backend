package com.dayma.dto;


import com.dayma.enums.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Long id;
    private String email;
    private String password;
    private String name;
    private RoleEnum role;
    private Boolean newsletter;
}
