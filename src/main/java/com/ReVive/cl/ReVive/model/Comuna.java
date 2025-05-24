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
@Table(name="Comuna")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Comuna {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idComuna;
    
    @Column(nullable = false, length = 5,unique = true)
    private int codigoComuna;

    @Column(nullable = false, length = 30)
    private String nombreComuna;

    @ManyToOne
    @JoinColumn(name = "idRegion", nullable = false)
    private Region region;
}
