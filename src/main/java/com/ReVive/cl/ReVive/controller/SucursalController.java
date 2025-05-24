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

import com.ReVive.cl.ReVive.model.Sucursal;
import com.ReVive.cl.ReVive.service.SucursalServices;

@RestController
@RequestMapping("/api/v1/sucursales")
public class SucursalController {

    @Autowired
    private SucursalServices sucursalServices;

    @GetMapping
    public ResponseEntity<List<Sucursal>> listar() {
        List<Sucursal> sucursales = sucursalServices.findAll();
        if (sucursales.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(sucursales);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sucursal> buscar(@PathVariable Long id) {
        Sucursal sucursal = sucursalServices.findByIdSucursal(id);
        if (sucursal != null) {
            return ResponseEntity.ok(sucursal);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/razonSocial/{razon}")
    public ResponseEntity<Sucursal> buscarPorRazonSocial(@PathVariable String razon) {
        Sucursal sucursal = sucursalServices.findByRazonSocial(razon);
        if (sucursal != null) {
            return ResponseEntity.ok(sucursal);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Sucursal> guardar(@RequestBody Sucursal sucursal) {
        Sucursal sucursalGuardar = sucursalServices.save(sucursal);
        return ResponseEntity.status(HttpStatus.CREATED).body(sucursalGuardar);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Sucursal> actualizar(@PathVariable Long id, @RequestBody Sucursal sucursal) {
        Sucursal actualizado = sucursalServices.update(id, sucursal);
        if (actualizado != null) {
            return ResponseEntity.ok(actualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Sucursal> patchSucursal(@PathVariable Long id, @RequestBody Sucursal sucursal) {
        Sucursal actualizado = sucursalServices.patch(id, sucursal);
        if (actualizado != null) {
            return ResponseEntity.ok(actualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            sucursalServices.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
 