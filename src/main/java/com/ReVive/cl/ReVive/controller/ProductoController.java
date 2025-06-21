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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ReVive.cl.ReVive.model.Producto;
import com.ReVive.cl.ReVive.service.ProductoServices;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/productos")
@Tag(name = "Productos", description = "Operaciones relacionadas con productos")
public class ProductoController {

    @Autowired
    private ProductoServices productoServices;

    @GetMapping
    @Operation(summary = "Listar todos los productos", description = "Obtiene una lista de todos los productos")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Operación exitosa"),
        @ApiResponse(responseCode = "204", description = "No hay productos disponibles")
    }) 
    public ResponseEntity<List<Producto>> listar() {
        List<Producto> productos = productoServices.findAll();
        if (productos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener producto", description = "Obtiene producto por id")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Operación exitosa"),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })  
    public ResponseEntity<Producto> buscar(@PathVariable Long id) {
        Producto producto = productoServices.findById(id);
        if (producto != null) {
            return ResponseEntity.ok(producto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/nombre/{nombre}")
    @Operation(summary = "Buscar producto por nombre", description = "Obtiene producto por nombre")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Operación exitosa"),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })  
    public ResponseEntity<Producto> buscarPorNombre(@PathVariable String nombre) {
        Producto producto = productoServices.findByNombreProducto(nombre);
        if (producto != null) {
            return ResponseEntity.ok(producto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/nombre-categoria")
    @Operation(summary = "Buscar productos por nombre y categoría", description = "Obtiene lista de productos filtrados por nombre y categoría")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Operación exitosa"),
        @ApiResponse(responseCode = "204", description = "No hay productos disponibles")
    }) 
    public ResponseEntity<List<Producto>> buscarPorNombreYCategoria(
            @RequestParam String nombre, 
            @RequestParam String categoria) {
        List<Producto> productos = productoServices.findByNombreProductoAndCategoria(nombre, categoria);
        if (productos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(productos);
    } // /api/v1/productos/nombre-categoria?nombre=algo&categoria=otra

    @GetMapping("/categoria-sucursal")
    @Operation(summary = "Buscar productos por categoría y sucursal", description = "Obtiene lista de productos filtrados por categoría y sucursal")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Operación exitosa"),
        @ApiResponse(responseCode = "204", description = "No hay productos disponibles")
    }) 
    public ResponseEntity<List<Producto>> buscarPorCategoriaYSucursal(
            @RequestParam String categoria,
            @RequestParam String sucursal) {
        List<Producto> productos = productoServices.findProductosByCategoriaAndSucursal(categoria, sucursal);
        if (productos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(productos);
    } // /api/v1/productos/categoria-sucursal?categoria=cat1&sucursal=suc1

    @PostMapping
    @Operation(summary = "Crear un nuevo producto", description = "Guarda un nuevo producto")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "201", description = "Producto creado correctamente"),
        @ApiResponse(responseCode = "400", description = "Solicitud incorrecta")
    }) 
    public ResponseEntity<Producto> guardar(@RequestBody Producto producto) {
        Producto productoNuevo = productoServices.save(producto);
        return ResponseEntity.status(HttpStatus.CREATED).body(productoNuevo);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un producto", description = "Actualiza todos los datos de un producto por id")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Producto actualizado correctamente"),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    }) 
    public ResponseEntity<Producto> actualizar(@PathVariable Long id, @RequestBody Producto producto) {
        Producto actualizado = productoServices.update(id, producto);
        if (actualizado != null) {
            return ResponseEntity.ok(actualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Actualizar parcialmente un producto", description = "Modifica ciertos campos de un producto por id")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Producto modificado correctamente"),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    }) 
    public ResponseEntity<Producto> actualizarParcial(@PathVariable Long id, @RequestBody Producto producto) {
        Producto actualizado = productoServices.patch(id, producto);
        if (actualizado != null) {
            return ResponseEntity.ok(actualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un producto", description = "Elimina un producto por id")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "204", description = "Producto eliminado correctamente"),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    }) 
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        try {
            productoServices.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
