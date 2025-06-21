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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ReVive.cl.ReVive.model.Ventas;
import com.ReVive.cl.ReVive.service.VentasServices;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/ventas")
@Tag(name = "Ventas", description = "Operaciones relacionadas con las ventas")
public class VentasController {

    @Autowired
    private VentasServices ventasServices;

    @GetMapping
    @Operation(summary = "Listar todas las ventas", description = "Obtiene todas las ventas registradas")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Operación exitosa"),
        @ApiResponse(responseCode = "204", description = "No hay ventas disponibles")
    })
    public ResponseEntity<List<Ventas>> listar() {
        List<Ventas> ventas = ventasServices.findAll();
        if (ventas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(ventas);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener venta por ID", description = "Busca una venta por su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Venta encontrada"),
        @ApiResponse(responseCode = "404", description = "Venta no encontrada")
    })
    public ResponseEntity<Ventas> buscar(@PathVariable Long id) {
        try {
            Ventas venta = ventasServices.findByIdVentas(id);
            return ResponseEntity.ok(venta);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/fecha/{fecha}")
    @Operation(summary = "Buscar ventas por fecha", description = "Obtiene ventas realizadas en una fecha específica (YYYY-MM-DD)")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Ventas encontradas"),
        @ApiResponse(responseCode = "204", description = "No hay ventas en esa fecha"),
        @ApiResponse(responseCode = "400", description = "Formato de fecha inválido")
    })
    public ResponseEntity<List<Ventas>> buscarPorFecha(@PathVariable String fecha) {
        try {
            Date fechaVenta = Date.valueOf(fecha);
            List<Ventas> ventas = ventasServices.findByfechaVentas(fechaVenta);
            if (ventas.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(ventas);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/usuario-sucursal")
    @Operation(summary = "Buscar ventas por usuario y sucursal", description = "Filtra ventas por nombre de usuario y nombre de sucursal")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Ventas encontradas"),
        @ApiResponse(responseCode = "204", description = "No hay ventas para el usuario y sucursal especificados")
    })
    public ResponseEntity<List<Ventas>> buscarPorUsuarioYSucursal(@RequestParam String usuario, @RequestParam String sucursal) {
        List<Ventas> ventas = ventasServices.findByUsuarioAndSucursal(usuario, sucursal);
        if (ventas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(ventas);
    }

    @GetMapping("/sucursal-categoria/{sucursal}/{categoria}")
    @Operation(summary = "Buscar ventas por sucursal y categoría", description = "Filtra ventas por sucursal y categoría")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Ventas encontradas"),
        @ApiResponse(responseCode = "204", description = "No hay ventas para la sucursal y categoría especificadas")
    })
    public ResponseEntity<List<Ventas>> buscarPorSucursalYCategoria(@PathVariable String sucursal, @PathVariable String categoria) {
        List<Ventas> ventas = ventasServices.buscarVentasPorSucursalYCategoria(sucursal, categoria);
        if (ventas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(ventas);
    }

    @PostMapping
    @Operation(summary = "Crear una nueva venta", description = "Guarda una venta nueva")
    @ApiResponse(responseCode = "201", description = "Venta creada correctamente")
    public ResponseEntity<Ventas> guardar(@RequestBody Ventas venta) {
        Ventas ventaNueva = ventasServices.save(venta);
        return ResponseEntity.status(HttpStatus.CREATED).body(ventaNueva);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una venta", description = "Actualiza todos los datos de una venta")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Venta actualizada correctamente"),
        @ApiResponse(responseCode = "404", description = "Venta no encontrada")
    })
    public ResponseEntity<Ventas> actualizar(@PathVariable Long id, @RequestBody Ventas venta) {
        try {
            Ventas ventaActualizada = ventasServices.update(id, venta);
            return ResponseEntity.ok(ventaActualizada);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Actualizar parcialmente una venta", description = "Actualiza ciertos campos de una venta")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Venta modificada correctamente"),
        @ApiResponse(responseCode = "404", description = "Venta no encontrada")
    })
    public ResponseEntity<Ventas> patchVenta(@PathVariable Long id, @RequestBody Ventas venta) {
        Ventas ventaActualizada = ventasServices.patch(id, venta);
        if (ventaActualizada != null) {
            return ResponseEntity.ok(ventaActualizada);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una venta", description = "Elimina una venta por su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Venta eliminada correctamente"),
        @ApiResponse(responseCode = "404", description = "Venta no encontrada")
    })
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        try {
            ventasServices.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
