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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/categoriasProducto")
@Tag(name = "Categorias de Producto", description = "Operaciones relacionadas con las categorias de producto")
public class CategoriaProductoController {

    @Autowired
    private CategoriasProductoServices categoriasProductoServices;

    @GetMapping
    @Operation(summary = "Listar todas las categorías", description = "Obtiene una lista de todas las categorías de productos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Categorías encontradas"),
        @ApiResponse(responseCode = "204", description = "No hay categorías disponibles")
    })
    public ResponseEntity<List<CategoriasProducto>> listar() {
        List<CategoriasProducto> categorias = categoriasProductoServices.findAll();
        if (categorias.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(categorias);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar categoría por ID", description = "Obtiene una categoría de producto por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Categoría encontrada"),
        @ApiResponse(responseCode = "404", description = "Categoría no encontrada")
    })
    public ResponseEntity<CategoriasProducto> buscar(@PathVariable Long id) {
        CategoriasProducto categoria = categoriasProductoServices.CategoriaProductoId(id);
        if (categoria != null) {
            return ResponseEntity.ok(categoria);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/buscarNombre/{nombre}")
    @Operation(summary = "Buscar categoría por nombre", description = "Obtiene una categoría de producto por su nombre")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Categoría encontrada"),
        @ApiResponse(responseCode = "404", description = "Categoría no encontrada")
    })
    public ResponseEntity<CategoriasProducto> buscarPorNombre(@PathVariable String nombre) {
        CategoriasProducto categoria = categoriasProductoServices.findByNombre(nombre);
        if (categoria != null) {
            return ResponseEntity.ok(categoria);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Guardar una nueva categoría", description = "Crea una nueva categoría de producto")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Categoría creada correctamente"),
        @ApiResponse(responseCode = "400", description = "Error al crear la categoría")
    })
    public ResponseEntity<CategoriasProducto> guardar(@RequestBody CategoriasProducto categoria) {
        CategoriasProducto guardada = categoriasProductoServices.save(categoria);
        return ResponseEntity.status(HttpStatus.CREATED).body(guardada);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una categoría existente", description = "Actualiza completamente una categoría de producto")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Categoría actualizada correctamente"),
        @ApiResponse(responseCode = "404", description = "Categoría no encontrada")
    })
    public ResponseEntity<CategoriasProducto> actualizar(@PathVariable Long id, @RequestBody CategoriasProducto categoria) {
        CategoriasProducto actualizado = categoriasProductoServices.update(id, categoria);
        if (actualizado != null) {
            return ResponseEntity.ok(actualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Modificar parcialmente una categoría", description = "Actualiza parcialmente una categoría de producto")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Categoría modificada correctamente"),
        @ApiResponse(responseCode = "404", description = "Categoría no encontrada")
    })
    public ResponseEntity<CategoriasProducto> patchCategoria(@PathVariable Long id, @RequestBody CategoriasProducto categoria) {
        CategoriasProducto actualizado = categoriasProductoServices.patch(id, categoria);
        if (actualizado != null) {
            return ResponseEntity.ok(actualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una categoría por ID", description = "Elimina una categoría de producto por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Categoría eliminada correctamente"),
        @ApiResponse(responseCode = "404", description = "Categoría no encontrada")
    })
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        try {
            categoriasProductoServices.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
