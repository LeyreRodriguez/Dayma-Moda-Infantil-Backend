package com.dayma.model;

import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "product_size", indexes = {
    @Index(name = "idx_product_size_product", columnList = "product"),
    @Index(name = "idx_product_size_size", columnList = "size"),
    @Index(name = "idx_product_size_product_size", columnList = "product, size", unique = true)
})
public class ProductSize {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "size")
    private Size size;

    @Column(name = "stock")
    private Integer stock;

}