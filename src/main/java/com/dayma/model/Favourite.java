package com.dayma.model;

import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "favourites")
public class Favourite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User appUser;

    @ManyToOne
    @JoinColumn(name = "product")
    private Product product;
}