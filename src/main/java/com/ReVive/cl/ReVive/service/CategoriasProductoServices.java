package com.ReVive.cl.ReVive.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ReVive.cl.ReVive.model.CategoriasProducto;
import com.ReVive.cl.ReVive.model.Producto;
import com.ReVive.cl.ReVive.repository.CategoriasProductoRepository;
import com.ReVive.cl.ReVive.repository.DetalleVentaRepository;
import com.ReVive.cl.ReVive.repository.ProductoRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CategoriasProductoServices {

    @Autowired
    private CategoriasProductoRepository categoriasProductoRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private DetalleVentaRepository detalleVentaRepository;

    public List<CategoriasProducto> findAll() {
        return categoriasProductoRepository.findAll();
    }

    public CategoriasProducto save(CategoriasProducto categoriaProducto) {
        return categoriasProductoRepository.save(categoriaProducto);
    }

    public CategoriasProducto update(Long id, CategoriasProducto categoriaProducto) {
        CategoriasProducto categoriaToUpdate = categoriasProductoRepository.findById(id).orElse(null);
        if (categoriaToUpdate != null) {
            categoriaToUpdate.setNombreCatesProducto(categoriaProducto.getNombreCatesProducto());
            return categoriasProductoRepository.save(categoriaToUpdate);
        } else {
            return null;
        }
    }

    public CategoriasProducto patch(Long id, CategoriasProducto categoriaProducto) {
        CategoriasProducto categoriaToPatch = categoriasProductoRepository.findById(id).orElse(null);
        if (categoriaToPatch != null) {
            if (categoriaProducto.getNombreCatesProducto() != null) {
                categoriaToPatch.setNombreCatesProducto(categoriaProducto.getNombreCatesProducto());
            }
            return categoriasProductoRepository.save(categoriaToPatch);
        } else {
            return null;
        }
    }

    public void delete(Long id) {
        CategoriasProducto categoriaproducto = categoriasProductoRepository.findByIdCatesProducto(id);

       List<Producto> productos = productoRepository.findByCategoria(categoriaproducto); 
       for ( Producto producto : productos) {
             detalleVentaRepository.deleteByProducto(producto);
        }

        productoRepository.deleteByCategoria(categoriaproducto);
        categoriasProductoRepository.deleteByIdCatesProducto(id);

    }

    public CategoriasProducto CategoriaProductoId(Long idCatesProducto) {
        return categoriasProductoRepository.findByIdCatesProducto(idCatesProducto);    
    }

    public CategoriasProducto findByNombre(String nombreCatesProducto) {
    return categoriasProductoRepository.findByNombreCatesProducto(nombreCatesProducto);
    }


}