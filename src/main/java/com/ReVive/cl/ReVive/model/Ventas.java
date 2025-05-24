package com.ReVive.cl.ReVive.model;

import java.sql.Date;
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
@Table(name = "Ventas")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ventas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idVenta;

    @Column(nullable = false)
    private Date fechaVenta;

    @Column(nullable = false, length = 9)
    private int totalVenta;

    @ManyToOne
    @JoinColumn(name = "idUsuario", nullable = false)
    private Usuarios usuario;
    
    @ManyToOne
    @JoinColumn(name = "idSucursal", nullable = false)
    private Sucursal sucursal;

}
