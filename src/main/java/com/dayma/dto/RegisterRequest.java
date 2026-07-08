package com.dayma.dto;

public record RegisterRequest (
        String email,
        String password,
        String name
){
}
