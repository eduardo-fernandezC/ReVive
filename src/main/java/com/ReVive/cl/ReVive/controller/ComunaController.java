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

import com.ReVive.cl.ReVive.model.Comuna;
import com.ReVive.cl.ReVive.service.ComunaServices;

@RestController
@RequestMapping("/api/v1/comunas")
public class ComunaController {

    @Autowired
    private ComunaServices comunaServices;

    @GetMapping
    public ResponseEntity<List<Comuna>> listar() {
        List<Comuna> comunas = comunaServices.findAll();
        if (comunas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(comunas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Comuna> buscar(@PathVariable Long id) {
        Comuna comuna = comunaServices.findById(id);
        if (comuna != null) {
            return ResponseEntity.ok(comuna);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Comuna> guardar(@RequestBody Comuna comuna) {
        Comuna comunaGuardar = comunaServices.save(comuna);
        return ResponseEntity.status(HttpStatus.CREATED).body(comunaGuardar);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Comuna> actualizar(@PathVariable Long id, @RequestBody Comuna comuna) {
        Comuna actualizado = comunaServices.update(id, comuna);
        if (actualizado != null) {
            return ResponseEntity.ok(actualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Comuna> patchComuna(@PathVariable Long id, @RequestBody Comuna comuna) {
        Comuna actualizado = comunaServices.patch(id, comuna);
        if (actualizado != null) {
            return ResponseEntity.ok(actualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            comunaServices.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
