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

import com.ReVive.cl.ReVive.model.DetalleVenta;
import com.ReVive.cl.ReVive.service.DetalleVentaServices;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/detalleVentas")
@Tag(name = "Detalles de Venta", description = "Operaciones relacionadas con los detalles de venta")
public class DetalleVentaController {

    @Autowired
    private DetalleVentaServices detalleVentaServices;

    @GetMapping
    @Operation(summary = "Listar todos los detalles de venta", description = "Obtiene una lista de todos los detalles de venta")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Detalles encontrados"),
        @ApiResponse(responseCode = "204", description = "No hay detalles disponibles")
    })
    public ResponseEntity<List<DetalleVenta>> listar() {
        List<DetalleVenta> detalles = detalleVentaServices.findAll();
        if (detalles.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(detalles);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar detalle de venta por ID", description = "Obtiene un detalle de venta por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Detalle encontrado"),
        @ApiResponse(responseCode = "404", description = "Detalle no encontrado")
    })
    public ResponseEntity<DetalleVenta> buscar(@PathVariable Long id) {
        DetalleVenta detalle = detalleVentaServices.findById(id);
        return (detalle != null) ? ResponseEntity.ok(detalle) : ResponseEntity.notFound().build();
    }

    @GetMapping("/buscar-detalle")
    @Operation(summary = "Buscar detalle por usuario y categoría", description = "Obtiene detalles de venta por nombre de usuario y nombre de categoría")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Detalles encontrados"),
        @ApiResponse(responseCode = "204", description = "No hay detalles disponibles")
    })
    public ResponseEntity<List<DetalleVenta>> buscarDetallePorUsuarioYCategoria(
        @RequestParam String usuario,
        @RequestParam String categoria) {
        List<DetalleVenta> detalles = detalleVentaServices.buscarDetallePorUsuarioYCategoria(usuario, categoria);
        if (detalles.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(detalles);
    } // /api/v1/detalleVentas/buscar-detalle?usuario=Martin&categoria=Reciclables

    @PostMapping
    @Operation(summary = "Guardar un nuevo detalle de venta", description = "Crea un nuevo detalle de venta")
    @ApiResponse(responseCode = "201", description = "Detalle creado correctamente")
    public ResponseEntity<DetalleVenta> guardar(@RequestBody DetalleVenta detalleVenta) {
        DetalleVenta guardado = detalleVentaServices.save(detalleVenta);
        return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un detalle por ID", description = "Actualiza un detalle de venta por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Detalle actualizado correctamente"),
        @ApiResponse(responseCode = "404", description = "Detalle no encontrado")
    })
    public ResponseEntity<DetalleVenta> actualizar(@PathVariable Long id, @RequestBody DetalleVenta detalleVenta) {
        DetalleVenta actualizado = detalleVentaServices.update(id, detalleVenta);
        return (actualizado != null) ? ResponseEntity.ok(actualizado) : ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Modificar parcialmente un detalle", description = "Modifica parcialmente un detalle de venta")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Detalle modificado correctamente"),
        @ApiResponse(responseCode = "404", description = "Detalle no encontrado")
    })
    public ResponseEntity<DetalleVenta> patchDetalleVenta(@PathVariable Long id, @RequestBody DetalleVenta detalleVenta) {
        DetalleVenta actualizado = detalleVentaServices.patch(id, detalleVenta);
        return (actualizado != null) ? ResponseEntity.ok(actualizado) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un detalle por ID", description = "Elimina un detalle de venta por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Detalle eliminado correctamente"),
        @ApiResponse(responseCode = "404", description = "Detalle no encontrado")
    })
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        try {
            detalleVentaServices.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
