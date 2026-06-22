package com.dayma.dto.response;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GenericResponseDto<T> {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    boolean success = false;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    String message = null;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    T data;

    WarningDto warning = null;

    public GenericResponseDto(T data) {
        this.success = true;
        this.data = data;
    }

    public GenericResponseDto(T data, String message) {
        this.success = true;
        this.data = data;
        this.message = message;
    }

}