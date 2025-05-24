package com.ReVive.cl.ReVive.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ReVive.cl.ReVive.model.DetalleVenta;
import com.ReVive.cl.ReVive.model.Producto;
import com.ReVive.cl.ReVive.model.Ventas;

@Repository
public interface DetalleVentaRepository extends JpaRepository<DetalleVenta, Long>{
    List<DetalleVenta> findByVentas(Ventas ventas);
    List<DetalleVenta> findByProducto(Producto producto);
}
