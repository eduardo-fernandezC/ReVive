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

import com.ReVive.cl.ReVive.model.CategoriasProducto;
import com.ReVive.cl.ReVive.service.CategoriasProductoServices;

@RestController
@RequestMapping("/api/v1/categoriasProducto")
public class CategoriaProductoController {

    @Autowired
    private CategoriasProductoServices categoriasProductoServices;

    @GetMapping
    public ResponseEntity<List<CategoriasProducto>> listar() {
        List<CategoriasProducto> categorias = categoriasProductoServices.findAll();
        if (categorias.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(categorias);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriasProducto> buscar(@PathVariable Long id) {
        CategoriasProducto categoria = categoriasProductoServices.CategoriaProductoId(id);
        if (categoria != null) {
            return ResponseEntity.ok(categoria);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/buscarNombre/{nombre}")
    public ResponseEntity<CategoriasProducto> buscarPorNombre(@PathVariable String nombre) {
        CategoriasProducto categoria = categoriasProductoServices.findByNombre(nombre);
        if (categoria != null) {
            return ResponseEntity.ok(categoria);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<CategoriasProducto> guardar(@RequestBody CategoriasProducto categoria) {
        CategoriasProducto categoriaGuardar = categoriasProductoServices.save(categoria);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaGuardar);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriasProducto> actualizar(@PathVariable Long id, @RequestBody CategoriasProducto categoria) {
        CategoriasProducto actualizado = categoriasProductoServices.update(id, categoria);
        if (actualizado != null) {
            return ResponseEntity.ok(actualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CategoriasProducto> patchCategoria(@PathVariable Long id, @RequestBody CategoriasProducto categoria) {
        CategoriasProducto actualizado = categoriasProductoServices.patch(id, categoria);
        if (actualizado != null) {
            return ResponseEntity.ok(actualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            categoriasProductoServices.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
