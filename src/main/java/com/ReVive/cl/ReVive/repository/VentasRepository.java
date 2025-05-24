package com.ReVive.cl.ReVive.repository;


import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ReVive.cl.ReVive.model.Ventas;

@Repository
public interface VentasRepository extends JpaRepository<Ventas, Long>{
    List<Ventas> findByfechaVenta(Date fechaVenta);
    Ventas findByIdVenta(Long idVenta);
}
