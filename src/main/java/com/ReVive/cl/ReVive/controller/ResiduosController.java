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

import com.ReVive.cl.ReVive.model.Residuos;
import com.ReVive.cl.ReVive.service.ResiduosServices;

@RestController
@RequestMapping("/api/v1/residuos")
public class ResiduosController {

    @Autowired
    private ResiduosServices residuosServices;

    @GetMapping
    public ResponseEntity<List<Residuos>> listar() {
        List<Residuos> residuos = residuosServices.findAll();
        if (residuos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(residuos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Residuos> buscar(@PathVariable Long id) {
        Residuos residuo = residuosServices.findByIdResiduos(id);
        if (residuo != null) {
            return ResponseEntity.ok(residuo);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/cantidad/{cantidad}")
    public ResponseEntity<List<Residuos>> buscarPorCantidad(@PathVariable int cantidad) {
        List<Residuos> residuos = residuosServices.findByCantidadResiduos(cantidad);
        if (residuos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(residuos);
    }

    @PostMapping
    public ResponseEntity<Residuos> guardar(@RequestBody Residuos residuo) {
        Residuos residuoGuardar = residuosServices.save(residuo);
        return ResponseEntity.status(HttpStatus.CREATED).body(residuoGuardar);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Residuos> actualizar(@PathVariable Long id, @RequestBody Residuos residuo) {
        Residuos actualizado = residuosServices.update(id, residuo);
        if (actualizado != null) {
            return ResponseEntity.ok(actualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Residuos> patchResiduo(@PathVariable Long id, @RequestBody Residuos residuo) {
        Residuos actualizado = residuosServices.patch(id, residuo);
        if (actualizado != null) {
            return ResponseEntity.ok(actualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            residuosServices.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
