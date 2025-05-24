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

import com.ReVive.cl.ReVive.model.CategoriasResiduos;
import com.ReVive.cl.ReVive.service.CategoriasResiduosServices;

@RestController
@RequestMapping("/api/v1/categoriasResiduos")
public class CategoriaResiduosController {

    @Autowired
    private CategoriasResiduosServices categoriasResiduosServices;

    @GetMapping
    public ResponseEntity<List<CategoriasResiduos>> listar() {
        List<CategoriasResiduos> categorias = categoriasResiduosServices.findAll();
        if (categorias.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(categorias);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriasResiduos> buscar(@PathVariable Long id) {
        CategoriasResiduos categoria = categoriasResiduosServices.findByIdCatesResiduos(id);
        if (categoria != null) {
            return ResponseEntity.ok(categoria);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/buscarNombre/{nombre}")
    public ResponseEntity<CategoriasResiduos> buscarPorNombre(@PathVariable String nombre) {
        CategoriasResiduos categoria = categoriasResiduosServices.findByNombreCatesResiduos(nombre);
        if (categoria != null) {
            return ResponseEntity.ok(categoria);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<CategoriasResiduos> guardar(@RequestBody CategoriasResiduos categoria) {
        CategoriasResiduos categoriaGuardar = categoriasResiduosServices.save(categoria);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaGuardar);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriasResiduos> actualizar(@PathVariable Long id, @RequestBody CategoriasResiduos categoria) {
        CategoriasResiduos actualizado = categoriasResiduosServices.update(id, categoria);
        if (actualizado != null) {
            return ResponseEntity.ok(actualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CategoriasResiduos> patchCategoria(@PathVariable Long id, @RequestBody CategoriasResiduos categoria) {
        CategoriasResiduos actualizado = categoriasResiduosServices.patch(id, categoria);
        if (actualizado != null) {
            return ResponseEntity.ok(actualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            categoriasResiduosServices.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
