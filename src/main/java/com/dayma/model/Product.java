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
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "short_description")
    private String shortDescription;

    @Column(name="long_description")
    private String longDescription;

    @Column(name = "price")
    private Double price;

    @ManyToOne
    @JoinColumn(name = "category")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "size")
    private Size size;

    @Column(name = "material")
    private String material;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "is_new")
    private Boolean isNew;

    @Column(name = "code")
    private String code;

    @Column(name = "insertion_date")
    private LocalDate insertionDate;


}
