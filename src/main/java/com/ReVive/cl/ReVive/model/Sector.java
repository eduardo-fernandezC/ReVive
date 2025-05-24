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
@Table(name="Sector")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Sector {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSector;

    @Column(nullable = true, length = 30)
    private String nombreSector;

    @Column(nullable = false, length = 60)
    private String direccionSector;

    @ManyToOne
    @JoinColumn(name = "idComuna", nullable = false)
    private Comuna comuna;
    
}
