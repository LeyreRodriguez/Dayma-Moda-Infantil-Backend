package com.dayma.dto;

import com.dayma.model.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    private Long id;
    private Double total;
    private OrderStatusDto status;
    private UserDto appUser;
    private String code;
    private LocalDate date;
}
