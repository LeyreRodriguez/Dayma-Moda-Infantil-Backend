package com.dayma.service.impl;

import com.dayma.dto.*;
import com.dayma.mapper.OrderMapper;
import com.dayma.model.*;
import com.dayma.service.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final ProductService productService;
    private final ProductSizeService productSizeService;
    private final OrderService orderService;
    private final OrderStatusService orderStatusService;
    private final ProductOrderService productOrderService;
    private final SizeService sizeService;
    private final UserService userService;
    private final OrderMapper orderMapper;

    @Override
    public InventorySummaryDto getInventorySummary() {
        long newArrivals = productService.countByIsNewTrue();
        long lowStock = productService.countByArchivedFalseAndLowStock();
        long archived = productService.countByArchivedTrue();
        return new InventorySummaryDto(newArrivals, lowStock, archived);
    }

    @Override
    public List<PendingOrderDto> getPendingOrders() {
        OrderStatus pendingStatus = orderStatusService.getByCode("PENDING");
        if (pendingStatus == null) {
            return List.of();
        }

        List<Order> pendingOrders = orderService.getOrdersByStatus(pendingStatus);
        List<PendingOrderDto> result = new ArrayList<>();

        for (Order order : pendingOrders) {
            long itemCount = productOrderService.countByOrder(order);
            String customerName = order.getAppUser().getName();
            if (customerName == null || customerName.isBlank()) {
                customerName = order.getAppUser().getEmail();
            }
            String initials = generateInitials(customerName);

            result.add(new PendingOrderDto(
                    order.getId(),
                    customerName,
                    initials,
                    order.getCode(),
                    itemCount,
                    order.getStatus().getStatus()
            ));
        }

        return result;
    }

    @Override
    public AnalyticsDataDto getAnalytics() {
        LocalDate now = LocalDate.now();
        YearMonth currentMonth = YearMonth.from(now);
        YearMonth previousMonth = currentMonth.minusMonths(1);

        LocalDateTime currentStart = currentMonth.atDay(1).atStartOfDay();
        LocalDateTime currentEnd = currentMonth.atEndOfMonth().atTime(23, 59, 59);
        LocalDateTime previousStart = previousMonth.atDay(1).atStartOfDay();
        LocalDateTime previousEnd = previousMonth.atEndOfMonth().atTime(23, 59, 59);

        long currentRevenueCount = orderService.countOrdersBetween(currentStart, currentEnd);
        long previousRevenueCount = orderService.countOrdersBetween(previousStart, previousEnd);

        double revenueGrowth = previousRevenueCount > 0
                ? ((double) (currentRevenueCount - previousRevenueCount) / previousRevenueCount) * 100
                : 0;

        long newSubscribers = userService.countByRegistrationDateBetween(currentStart, currentEnd);

        List<MonthlyRevenueDto> monthlyRevenue = new ArrayList<>();
        for (int i = 5; i >= 0; i--) {
            YearMonth ym = currentMonth.minusMonths(i);
            LocalDateTime start = ym.atDay(1).atStartOfDay();
            LocalDateTime end = ym.atEndOfMonth().atTime(23, 59, 59);
            long count = orderService.countOrdersBetween(start, end);
            monthlyRevenue.add(new MonthlyRevenueDto(ym.toString(), count * 100));
        }

        return new AnalyticsDataDto(revenueGrowth, newSubscribers, monthlyRevenue);
    }

    @Override
    public List<GrowthPointDto> getGrowthData() {
        LocalDate now = LocalDate.now();
        YearMonth currentMonth = YearMonth.from(now);
        List<GrowthPointDto> result = new ArrayList<>();

        for (int i = 11; i >= 0; i--) {
            YearMonth ym = currentMonth.minusMonths(i);
            LocalDateTime start = ym.atDay(1).atStartOfDay();
            LocalDateTime end = ym.atEndOfMonth().atTime(23, 59, 59);

            long deliveredOrders = orderService.countDeliveredOrdersBetween("PENDING", start, end);
            long subscribedUsers = userService.countByRegistrationDateBetween(start, end);

            result.add(new GrowthPointDto(ym.toString(), deliveredOrders, subscribedUsers));
        }

        return result;
    }

    @Override
    @Transactional
    public OrderDto createInStorePurchase(InStorePurchaseRequest request) {
        User admin = userService.getCurrentUser();

        OrderStatus status = orderStatusService.getByCode("DELIVERED");
        if (status == null) {
            status = orderStatusService.create("Entregado", "DELIVERED");
        }

        double total = 0;
        List<ProductOrder> productOrders = new ArrayList<>();
        List<ProductSize> sizesToUpdate = new ArrayList<>();

        for (InStorePurchaseItem item : request.items()) {
            Product product = productService.getEntityProduct(item.productCode());

            if (product == null) {
                throw new RuntimeException("Producto no encontrado: " + item.productCode());
            }
            Size size = sizeService.getEntityByCode(item.sizeCode());

            total += product.getPrice() * item.quantity();

            ProductSize productSize = productSizeService.getByProductAndSize(product, size);

            if (productSize.getStock() < item.quantity()) {
                throw new RuntimeException("Stock insuficiente para " + product.getName() + " (" + size.getSize() + "): disponible " + productSize.getStock() + ", solicitado " + item.quantity());
            }

            productOrders.add(ProductOrder.builder()
                    .product(product)
                    .size(size)
                    .quantity(item.quantity())
                    .build());

            productSize.setStock(productSize.getStock() - item.quantity());
            sizesToUpdate.add(productSize);
        }
        productSizeService.saveAll(sizesToUpdate);

        Order order = Order.builder()
                .appUser(admin)
                .total(total)
                .status(status)
                .date(LocalDateTime.now())
                .build();

        order = orderService.save(order);
        order.setCode("DM-" + order.getId());

        final Order savedOrder = order;

        productOrders.forEach(po -> po.setOrder(savedOrder));
        productOrderService.saveAll(productOrders);

        return orderMapper.toDto(order);
    }

    private String generateInitials(String name) {
        if (name == null || name.isBlank()) return "??";
        String[] parts = name.trim().split("\\s+");
        if (parts.length == 1) {
            return String.valueOf(Character.toUpperCase(parts[0].charAt(0)));
        }
        return String.valueOf(Character.toUpperCase(parts[0].charAt(0)))
                + Character.toUpperCase(parts[parts.length - 1].charAt(0));
    }
}
