package com.ReVive.cl.ReVive.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ReVive.cl.ReVive.model.Ventas;

@Repository
public interface VentasRepository extends JpaRepository<Ventas, Long> {

    List<Ventas> findByfechaVenta(Date fechaVenta);

    Ventas findByIdVenta(Long idVenta);

    List<Ventas> findByUsuario_NombreUsuarioAndSucursal_RazonSocialSucursal(String nombreUsuario, String sucursal);

    @Query("SELECT dv.ventas FROM DetalleVenta dv " +
           "JOIN dv.ventas v " +
           "JOIN v.sucursal s " +
           "JOIN dv.producto p " +
           "JOIN p.categoria c " +
           "WHERE s.razonSocialSucursal = :nombreSucursal AND c.nombreCatesProducto = :nombreCategoria")
    List<Ventas> buscarVentasPorSucursalYCategoria(@Param("nombreSucursal") String nombreSucursal, @Param("nombreCategoria") String nombreCategoria);

}