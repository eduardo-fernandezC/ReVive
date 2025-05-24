package com.ReVive.cl.ReVive.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "CategoriasResiduos")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class CategoriasResiduos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCatesResiduos;

    @Column(nullable = true, length = 30)
    private String nombreCatesResiduos;
}
