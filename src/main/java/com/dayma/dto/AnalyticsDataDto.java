package com.dayma.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnalyticsDataDto {
    private double revenueGrowth;
    private long newSubscribers;
    private List<MonthlyRevenueDto> monthlyRevenue;
}
