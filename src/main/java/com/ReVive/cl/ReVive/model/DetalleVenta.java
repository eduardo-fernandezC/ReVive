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
@Table(name = "detalleVentas")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetalleVenta {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDetalleVenta;

    @Column(nullable = false, length = 9)
    private Integer cantidad;

    @Column(nullable = false, length = 9)
    private Integer precioUnitario;

    @Column(nullable = false, length = 9)
    private Integer subtotal;
    
    @ManyToOne
    @JoinColumn(name = "idVenta", nullable = false)
    private Ventas ventas;

    @ManyToOne
    @JoinColumn(name = "idProducto", nullable = false)
    private Producto producto;
}
