package com.dayma.controller;

import com.dayma.dto.*;
import com.dayma.dto.response.GenericResponseDto;
import com.dayma.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RequestMapping("/api/admin")
@RestController
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/inventory-summary")
    public ResponseEntity<GenericResponseDto<InventorySummaryDto>> getInventorySummary() {
        return ResponseEntity.ok(new GenericResponseDto<>(adminService.getInventorySummary()));
    }

    @GetMapping("/orders/pending")
    public ResponseEntity<GenericResponseDto<List<PendingOrderDto>>> getPendingOrders() {
        return ResponseEntity.ok(new GenericResponseDto<>(adminService.getPendingOrders()));
    }

    @GetMapping("/analytics")
    public ResponseEntity<GenericResponseDto<AnalyticsDataDto>> getAnalytics() {
        return ResponseEntity.ok(new GenericResponseDto<>(adminService.getAnalytics()));
    }

    @GetMapping("/growth")
    public ResponseEntity<GenericResponseDto<List<GrowthPointDto>>> getGrowthData() {
        return ResponseEntity.ok(new GenericResponseDto<>(adminService.getGrowthData()));
    }

    @PostMapping("/in-store-purchase")
    public ResponseEntity<GenericResponseDto<OrderDto>> createInStorePurchase(@RequestBody InStorePurchaseRequest request) {
        return ResponseEntity.ok(new GenericResponseDto<>(adminService.createInStorePurchase(request)));
    }

}