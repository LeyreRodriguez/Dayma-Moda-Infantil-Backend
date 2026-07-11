package com.dayma.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "product", indexes = {
    @Index(name = "idx_product_code", columnList = "code", unique = true),
    @Index(name = "idx_product_category", columnList = "category"),
    @Index(name = "idx_product_archived", columnList = "archived"),
    @Index(name = "idx_product_is_new", columnList = "is_new"),
    @Index(name = "idx_product_insertion_date", columnList = "insertion_date")
})
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "short_description")
    private String shortDescription;

    @Column(name="long_description")
    private String longDescription;

    @Column(name = "price")
    private Double price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category")
    private Category category;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "is_new")
    private Boolean isNew;

    @Column(name = "code")
    private String code;

    @Column(name = "insertion_date")
    private LocalDate insertionDate;

    @Column(name = "archived")
    private Boolean archived = false;

}
