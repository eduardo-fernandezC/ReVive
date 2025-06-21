package com.ReVive.cl.ReVive.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ReVive.cl.ReVive.model.Camiones;

@Repository
public interface CamionesRepository extends JpaRepository<Camiones, Long> {

    @Query("SELECT c FROM Camiones c WHERE c.patenteCamion = :patente")
    Camiones buscarPorPatente(@Param("patente") String patente);

    @Query("SELECT c FROM Camiones c " +
       "JOIN c.sector s " +
       "JOIN s.comuna co " +
       "JOIN c.sucursal su " +
       "WHERE su.razonSocialSucursal = :nombreSucursal " +
       "AND co.nombreComuna = :nombreComuna")
    List<Camiones> buscarCamionesPorSucursalYComuna(@Param("nombreSucursal") String nombreSucursal, @Param("nombreComuna") String nombreComuna);
}
