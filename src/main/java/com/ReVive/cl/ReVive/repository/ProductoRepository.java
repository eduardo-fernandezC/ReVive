package com.ReVive.cl.ReVive.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ReVive.cl.ReVive.model.CategoriasProducto;
import com.ReVive.cl.ReVive.model.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long>{
    
    Producto findByNombreProducto (String nombreProducto);

    Producto findByIdProducto (Long idProducto);
    
    List<Producto> findByCategoria(CategoriasProducto categoriasproducto);

    List<Producto> findByNombreProductoAndCategoria_NombreCatesProducto(String nombre, String nombreCategoria);

    @Query("SELECT p FROM Producto p " +
       "JOIN p.sucursal s " +
       "JOIN p.categoria c " +
       "WHERE c.nombreCatesProducto = :categoria AND s.razonSocialSucursal = :sucursal")
    List<Producto> findProductosByCategoriaAndSucursal(@Param("categoria") String categoria, @Param("sucursal") String sucursal);

    void deleteByCategoria(CategoriasProducto categoria);
}
