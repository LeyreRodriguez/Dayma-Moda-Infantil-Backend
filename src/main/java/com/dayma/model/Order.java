package com.dayma.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "total")
    private Double total;

    @ManyToOne
    @JoinColumn(name = "status")
    private OrderStatus status;

    @Column(name = "code")
    private String code;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User appUser;

    @Column(name = "date")
    private LocalDateTime date;



}
