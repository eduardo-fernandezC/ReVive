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
@Table(name="Camiones")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Camiones {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCamion;

    @Column(unique = true, nullable = false, length = 8)
    private String patenteCamion;

    @ManyToOne
    @JoinColumn(name = "idSector", nullable = false)
    private Sector sector;
    
    @ManyToOne
    @JoinColumn(name = "idSucursal", nullable = false)
    private Sucursal sucursal;
}
