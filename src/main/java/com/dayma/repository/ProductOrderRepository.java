package com.dayma.repository;

import com.dayma.model.Order;
import com.dayma.model.ProductOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductOrderRepository extends JpaRepository<ProductOrder, Long> {
    List<ProductOrder> findByOrderCode(String orderCode);

    List<ProductOrder> findByOrder(Order order);

    long countByOrder(Order order);
}
