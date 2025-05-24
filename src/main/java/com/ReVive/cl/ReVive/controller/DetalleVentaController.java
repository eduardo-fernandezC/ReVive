package com.ReVive.cl.ReVive.controller;

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

import com.ReVive.cl.ReVive.model.DetalleVenta;
import com.ReVive.cl.ReVive.service.DetalleVentaServices;

@RestController
@RequestMapping("/api/v1/detalleVentas")
public class DetalleVentaController {

    @Autowired
    private DetalleVentaServices detalleVentaServices;

    @GetMapping
    public ResponseEntity<List<DetalleVenta>> listar() {
        List<DetalleVenta> detalles = detalleVentaServices.findAll();
        if (detalles.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(detalles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalleVenta> buscar(@PathVariable Long id) {
        DetalleVenta detalle = detalleVentaServices.findById(id);
        if (detalle != null) {
            return ResponseEntity.ok(detalle);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<DetalleVenta> guardar(@RequestBody DetalleVenta detalleVenta) {
        DetalleVenta detalleGuardado = detalleVentaServices.save(detalleVenta);
        return ResponseEntity.status(HttpStatus.CREATED).body(detalleGuardado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DetalleVenta> actualizar(@PathVariable Long id, @RequestBody DetalleVenta detalleVenta) {
        DetalleVenta actualizado = detalleVentaServices.update(id, detalleVenta);
        if (actualizado != null) {
            return ResponseEntity.ok(actualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<DetalleVenta> patchDetalleVenta(@PathVariable Long id, @RequestBody DetalleVenta detalleVenta) {
        DetalleVenta actualizado = detalleVentaServices.patch(id, detalleVenta);
        if (actualizado != null) {
            return ResponseEntity.ok(actualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            detalleVentaServices.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
