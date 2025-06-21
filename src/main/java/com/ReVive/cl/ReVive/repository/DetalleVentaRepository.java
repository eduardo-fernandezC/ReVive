package com.ReVive.cl.ReVive.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ReVive.cl.ReVive.model.DetalleVenta;
import com.ReVive.cl.ReVive.model.Producto;
import com.ReVive.cl.ReVive.model.Ventas;

@Repository
public interface DetalleVentaRepository extends JpaRepository<DetalleVenta, Long>{
    
    List<DetalleVenta> findByVentas(Ventas ventas);

    List<DetalleVenta> findByProducto(Producto producto);

    @Query("SELECT dv FROM DetalleVenta dv " +
       "JOIN dv.ventas v " +
       "JOIN v.usuario u " +
       "JOIN dv.producto p " +
       "JOIN p.categoria c " +
       "WHERE u.nombreUsuario = :nombre AND c.nombreCatesProducto = :categoria")
    List<DetalleVenta> buscarDetallePorUsuarioYCategoria(@Param("nombre") String nombre, @Param("categoria") String categoria); 
    // filtrar por nombre de usuario y nombre de producto vendido

    void deleteByVentas(Ventas ventas);

    void deleteByProducto(Producto producto);

}
