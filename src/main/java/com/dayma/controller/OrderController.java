package com.dayma.controller;


import com.dayma.dto.FavouriteDto;
import com.dayma.dto.OrderDto;
import com.dayma.dto.ProductOrderDto;
import com.dayma.dto.SetFavouriteRequest;
import com.dayma.dto.response.GenericResponseDto;
import com.dayma.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/order")
@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<GenericResponseDto<List<OrderDto>>> getOrders() {
        return ResponseEntity.ok(new GenericResponseDto<>(orderService.getOrders()));
    }





}
