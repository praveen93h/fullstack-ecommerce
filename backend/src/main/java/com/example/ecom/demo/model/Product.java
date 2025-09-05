package com.example.ecom.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name="products")
public class Product {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(length=2000)
    private String description;

    private BigDecimal price;

    private Integer stock;

    private String imageUrl; // optional

    // getters / setters
}
