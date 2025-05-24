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
@Table(name="Residuos")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Residuos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idResiduos;

    @Column(nullable = false, length = 9)
    private int cantidadResiduos;

    @ManyToOne
    @JoinColumn(name = "idCatesResiduos", nullable = false)
    private CategoriasResiduos categoriaResiduos;

    @ManyToOne
    @JoinColumn(name = "idSucursal", nullable = false)
    private Sucursal sucursal;
}
