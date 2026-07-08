package com.dayma.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GrowthPointDto {
    private String period;
    private long deliveredOrders;
    private long subscribedUsers;
}
