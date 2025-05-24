package com.ReVive.cl.ReVive.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="Producto")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProducto;

    @Column(nullable = false, length = 20)
    private String nombreProducto;

    @Column(nullable = false, length = 200)
    private String descripcionProducto;

    @Column(nullable = false, length = 9)
    private int valorProducto;

    @Column(nullable = false, length = 5)
    private int stockProducto;

    @ManyToOne
    @JoinColumn(name = "idCategoria", nullable = false)
    private CategoriasProducto categoria;

    @ManyToOne
    @JoinColumn(name = "idSucursal", nullable = false)
    private Sucursal sucursal;
    
}
