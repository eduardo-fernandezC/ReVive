package com.ReVive.cl.ReVive.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ReVive.cl.ReVive.model.CategoriasProducto;

@Repository
public interface CategoriasProductoRepository extends JpaRepository<CategoriasProducto, Long> {
    
    CategoriasProducto findByIdCatesProducto(Long idCatesProducto);

    CategoriasProducto findByNombreCatesProducto(String nombreCatesProducto);

    void deleteByIdCatesProducto(Long idCatesProducto);
}
