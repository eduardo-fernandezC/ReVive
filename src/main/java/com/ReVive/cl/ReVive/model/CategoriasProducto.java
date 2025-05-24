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
@Table(name="CategoriasProducto")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class CategoriasProducto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCatesProducto;

    @Column(nullable = true, length = 30)
    private String nombreCatesProducto;

}
