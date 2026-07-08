package com.dayma.service;

import com.dayma.dto.AnalyticsDataDto;
import com.dayma.dto.GrowthPointDto;
import com.dayma.dto.*;

import java.util.List;

public interface AdminService {
    InventorySummaryDto getInventorySummary();
    List<PendingOrderDto> getPendingOrders();
    AnalyticsDataDto getAnalytics();
    List<GrowthPointDto> getGrowthData();
    OrderDto createInStorePurchase(InStorePurchaseRequest request);
}
