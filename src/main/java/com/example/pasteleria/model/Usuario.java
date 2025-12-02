package com.example.pasteleria.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    private String rut;

    @Column(length = 50)
    private String nombre;

    @Column(length = 50)
    private String apellido;

    @Column(unique = true, length = 250)
    private String correo;

    @Column(length = 250)
    private String password;

    @Column(length = 50)
    private String telefono; // opcional

    private LocalDate fechaNacimiento; // opcional

    @Column(length = 50)
    private String direccion;

    @Column(length = 50, nullable = true)
    private String tipoUsuario;

}
