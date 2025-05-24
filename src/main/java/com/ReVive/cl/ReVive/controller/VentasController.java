package com.ReVive.cl.ReVive.controller;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ReVive.cl.ReVive.model.Ventas;
import com.ReVive.cl.ReVive.service.VentasServices;

@RestController
@RequestMapping("/api/v1/ventas")
public class VentasController {

    @Autowired
    private VentasServices ventasServices;

    @GetMapping
    public ResponseEntity<List<Ventas>> listar() {
        List<Ventas> ventas = ventasServices.findAll();
        if (ventas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(ventas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ventas> buscar(@PathVariable Long id) {
        Ventas venta = ventasServices.findByIdVentas(id);
        if (venta != null) {
            return ResponseEntity.ok(venta);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/fecha/{fecha}")
    public ResponseEntity<List<Ventas>> buscarPorFecha(@PathVariable String fecha) {
        try {
            Date fechaVenta = Date.valueOf(fecha); 
            List<Ventas> ventas = ventasServices.findByfechaVentas(fechaVenta);
            if (ventas.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(ventas);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping
    public ResponseEntity<Ventas> guardar(@RequestBody Ventas venta) {
        Ventas ventaGuardar = ventasServices.save(venta);
        return ResponseEntity.status(HttpStatus.CREATED).body(ventaGuardar);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ventas> actualizar(@PathVariable Long id, @RequestBody Ventas venta) {
        Ventas actualizado = ventasServices.update(id, venta);
        if (actualizado != null) {
            return ResponseEntity.ok(actualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Ventas> patchVenta(@PathVariable Long id, @RequestBody Ventas venta) {
        Ventas actualizado = ventasServices.patch(id, venta);
        if (actualizado != null) {
            return ResponseEntity.ok(actualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            ventasServices.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
