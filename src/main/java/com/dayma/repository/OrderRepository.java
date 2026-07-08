package com.dayma.repository;

import com.dayma.model.Order;
import com.dayma.model.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByAppUserEmail(String email);

    List<Order> findByStatus(OrderStatus status);

    Order findByCode(String code);

    long countByDateBetween(LocalDateTime start, LocalDateTime end);

    @Query("SELECT COUNT(o) FROM Order o WHERE o.status.code != :excludedCode AND o.date BETWEEN :start AND :end")
    long countDeliveredOrdersBetween(@Param("excludedCode") String excludedCode,
                                     @Param("start") LocalDateTime start,
                                     @Param("end") LocalDateTime end);
}
