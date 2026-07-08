package com.dayma.exception;

public class ProductsNotFoundException extends RuntimeException {
    public ProductsNotFoundException(String message) {
        super(message);
    }
}
