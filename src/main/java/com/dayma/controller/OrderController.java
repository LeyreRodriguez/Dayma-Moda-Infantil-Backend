package com.dayma.controller;


import com.dayma.dto.*;
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

    @GetMapping("/all")
    public ResponseEntity<GenericResponseDto<List<OrderDto>>> getAllOrders() {
        return ResponseEntity.ok(new GenericResponseDto<>(orderService.getAllOrders()));
    }

    @GetMapping("/status")
    public ResponseEntity<GenericResponseDto<List<OrderStatusDto>>> getOrderStatus() {
        return ResponseEntity.ok(new GenericResponseDto<>(orderService.getOrderStatus()));
    }

    @PutMapping("{code}/status")
    public ResponseEntity<GenericResponseDto<OrderDto>> getOrders(@PathVariable String code, @RequestBody ChangeStatusRequest status) {
        return ResponseEntity.ok(new GenericResponseDto<>(orderService.updateStatus(code, status)));
    }

}
