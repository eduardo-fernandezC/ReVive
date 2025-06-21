package com.ReVive.cl.ReVive.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ReVive.cl.ReVive.model.Producto;
import com.ReVive.cl.ReVive.repository.ProductoRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProductoServices {

    @Autowired
    private ProductoRepository productoRepository;

    public List<Producto> findAll() {
        return productoRepository.findAll();
    }

    public Producto save(Producto producto) {
        return productoRepository.save(producto);
    }

    public Producto update(Long id, Producto producto) {
        Producto productoToUpdate = productoRepository.findById(id).orElse(null);
        if (productoToUpdate != null) {
            productoToUpdate.setNombreProducto(producto.getNombreProducto());
            productoToUpdate.setDescripcionProducto(producto.getDescripcionProducto());
            productoToUpdate.setValorProducto(producto.getValorProducto());
            productoToUpdate.setStockProducto(producto.getStockProducto());
            productoToUpdate.setCategoria(producto.getCategoria());
            productoToUpdate.setSucursal(producto.getSucursal());
            return productoRepository.save(productoToUpdate);
        } else {
            return null;
        }
    }

    public Producto patch(Long id, Producto producto) {
        Producto productoToPatch = productoRepository.findById(id).orElse(null);
        if (productoToPatch != null) {
            if (producto.getNombreProducto() != null) {
                productoToPatch.setNombreProducto(producto.getNombreProducto());
            }
            if (producto.getDescripcionProducto() != null) {
                productoToPatch.setDescripcionProducto(producto.getDescripcionProducto());
            }
            if (producto.getValorProducto() != 0) {
                productoToPatch.setValorProducto(producto.getValorProducto());
            }
            if (producto.getStockProducto() != 0) {
                productoToPatch.setStockProducto(producto.getStockProducto());
            }
            if (producto.getCategoria() != null) {
                productoToPatch.setCategoria(producto.getCategoria());
            }
            if (producto.getSucursal() != null) {
                productoToPatch.setSucursal(producto.getSucursal());
            }
            return productoRepository.save(productoToPatch);
        } else {
            return null;
        }
    }

    public void delete(Long id) {
        productoRepository.deleteById(id);
    }

    public Producto findByNombreProducto(String nombreProducto) {
        return productoRepository.findByNombreProducto(nombreProducto);
    }
    public Producto findById(Long id) {
        return productoRepository.findById(id).orElse(null);
    }

    public List<Producto> findByNombreProductoAndCategoria(String nombre, String nombreCategoria) {
        return productoRepository.findByNombreProductoAndCategoria_NombreCatesProducto(nombre, nombreCategoria);
    }

    public List<Producto> findProductosByCategoriaAndSucursal(String categoria, String sucursal) {
        return productoRepository.findProductosByCategoriaAndSucursal(categoria, sucursal);
    }

}