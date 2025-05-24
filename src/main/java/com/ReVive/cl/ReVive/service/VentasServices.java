package com.ReVive.cl.ReVive.service;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ReVive.cl.ReVive.model.Ventas;
import com.ReVive.cl.ReVive.repository.VentasRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class VentasServices {

    @Autowired
    private VentasRepository ventasRepository;

    public List<Ventas> findAll() {
        return ventasRepository.findAll();
    }

    public Ventas save(Ventas venta) {
        return ventasRepository.save(venta);
    }

    public Ventas update(Long id, Ventas venta) {
        Ventas ventaToUpdate = ventasRepository.findById(id).orElse(null);
        if (ventaToUpdate != null) {
            ventaToUpdate.setFechaVenta(venta.getFechaVenta());
            ventaToUpdate.setTotalVenta(venta.getTotalVenta());
            ventaToUpdate.setUsuario(venta.getUsuario());
            ventaToUpdate.setSucursal(venta.getSucursal());
            return ventasRepository.save(ventaToUpdate);
        } else {
            return null;
        }
    }

    public Ventas patch(Long id, Ventas venta) {
        Ventas ventaToPatch = ventasRepository.findById(id).orElse(null);
        if (ventaToPatch != null) {
            if (venta.getFechaVenta() != null) {
                ventaToPatch.setFechaVenta(venta.getFechaVenta());
            }
            if (venta.getTotalVenta() != 0) {
                ventaToPatch.setTotalVenta(venta.getTotalVenta());
            }
            if (venta.getUsuario() != null) {
                ventaToPatch.setUsuario(venta.getUsuario());
            }
            if (venta.getSucursal() != null) {
                ventaToPatch.setSucursal(venta.getSucursal());
            }
            return ventasRepository.save(ventaToPatch);
        } else {
            return null;
        }
    }

    public void delete(Long id) {
        ventasRepository.deleteById(id);
    }

    public List<Ventas> findByfechaVentas(Date fechaVenta) {
        return ventasRepository.findByfechaVenta(fechaVenta);
    }

    public Ventas findByIdVentas(Long id) {
        return ventasRepository.findById(id).orElse(null);
    }
}