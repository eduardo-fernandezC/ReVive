package com.ReVive.cl.ReVive.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ReVive.cl.ReVive.model.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long>{
    Producto findByNombreProducto (String nombreProducto);
    Producto findByIdProducto (Long idProducto);
}
