package com.ReVive.cl.ReVive.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ReVive.cl.ReVive.model.DetalleVenta;
import com.ReVive.cl.ReVive.model.Producto;
import com.ReVive.cl.ReVive.model.Ventas;
import com.ReVive.cl.ReVive.repository.DetalleVentaRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class DetalleVentaServices {

    @Autowired
    private DetalleVentaRepository detalleVentaRepository;

    public DetalleVenta findById(Long id) {
        return detalleVentaRepository.findById(id).orElse(null);
    }

    public List<DetalleVenta> findAll() {
        return detalleVentaRepository.findAll();
    }

    public DetalleVenta save(DetalleVenta detalleVenta) {
        return detalleVentaRepository.save(detalleVenta);
    }

    public DetalleVenta update(Long id, DetalleVenta detalleVenta) {
        DetalleVenta dvToUpdate = detalleVentaRepository.findById(id).orElse(null);
        if (dvToUpdate != null) {
            dvToUpdate.setCantidad(detalleVenta.getCantidad());
            dvToUpdate.setPrecioUnitario(detalleVenta.getPrecioUnitario());
            dvToUpdate.setSubtotal(detalleVenta.getSubtotal());
            dvToUpdate.setProducto(detalleVenta.getProducto());
            dvToUpdate.setVentas(detalleVenta.getVentas());
            return detalleVentaRepository.save(dvToUpdate);
        } else {
            return null;
        }
    }

    public void delete(Long id) {
        detalleVentaRepository.deleteById(id);
    }

    public DetalleVenta patch(Long id, DetalleVenta detalleVenta) {
        DetalleVenta dvToPatch = detalleVentaRepository.findById(id).orElse(null);
        if (dvToPatch != null) {
            if (detalleVenta.getCantidad() != null) {
                dvToPatch.setCantidad(detalleVenta.getCantidad());
            }
            if (detalleVenta.getPrecioUnitario() != null) {
                dvToPatch.setPrecioUnitario(detalleVenta.getPrecioUnitario());
            }
            if (detalleVenta.getSubtotal() != null) {
                dvToPatch.setSubtotal(detalleVenta.getSubtotal());
            }
            if (detalleVenta.getProducto() != null) {
                dvToPatch.setProducto(detalleVenta.getProducto());
            }
            if (detalleVenta.getVentas() != null) {
                dvToPatch.setVentas(detalleVenta.getVentas());
            }
            return detalleVentaRepository.save(dvToPatch);
        } else {
            return null;
        }
    }

    public List<DetalleVenta> findByVentas(Ventas venta) {
        return detalleVentaRepository.findByVentas(venta);
    }

    public List<DetalleVenta> findByProducto(Producto producto) {
        return detalleVentaRepository.findByProducto(producto);
    }

    public List<DetalleVenta> buscarDetallePorUsuarioYCategoria(String nombreUsuario, String nombreCategoria) {
        return detalleVentaRepository.buscarDetallePorUsuarioYCategoria(nombreUsuario, nombreCategoria);
    }
}
