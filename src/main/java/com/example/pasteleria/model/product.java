package com.example.pasteleria.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    private String title;

    @Column(length = 500)
    private String description;

    @Column(unique = false, length = 100)
    private String category;

    @Column(length = 15)
    private int price;

    @Column(length = 255)
    private String imageUrl; // <-- Atributo para guardar la URL
}

