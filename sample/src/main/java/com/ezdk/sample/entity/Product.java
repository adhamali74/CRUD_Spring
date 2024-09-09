package com.ezdk.sample.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Use Long for ID

    @Column(name = "product_name")
    private String productName; // Correct field name for product name

    @Column(name = "product_description")
    private String productDescription; // Field for product description

    @Column(name = "stock_quantity")
    private Integer stockQuantity; // Field for stock quantity

    @Column(name = "date_added")
    private String dateAdded; // Field for the date added

    // You can also add more fields if your table has additional columns
}
