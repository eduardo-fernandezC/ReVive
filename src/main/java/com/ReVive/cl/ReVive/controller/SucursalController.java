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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/sucursales")
@Tag(name = "Sucursales", description = "Operaciones relacionadas con las sucursales")
public class SucursalController {

    @Autowired
    private SucursalServices sucursalServices;

    @GetMapping
    @Operation(summary = "Obtener todas las sucursales", description = "Obtiene una lista de todas las sucursales")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Operación exitosa"), 
        @ApiResponse(responseCode = "204", description = "No hay sucursales disponibles") 
    })  
    public ResponseEntity<List<Sucursal>> listar() {
        List<Sucursal> sucursales = sucursalServices.findAll();
        if (sucursales.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(sucursales);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener sucursal", description = "Obtiene sucursal por id")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Operación exitosa"), 
        @ApiResponse(responseCode = "404", description = "Sucursal no encontrada") 
    })  
    public ResponseEntity<Sucursal> buscar(@PathVariable Long id) {
        try {
            Sucursal sucursal = sucursalServices.findByIdSucursal(id);
            return ResponseEntity.ok(sucursal);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/razonSocial/{razon}")
    @Operation(summary = "Buscar sucursal por razón social", description = "Obtiene sucursal por razón social")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Operación exitosa"),
        @ApiResponse(responseCode = "404", description = "Sucursal no encontrada")
    })
    public ResponseEntity<Sucursal> buscarPorRazonSocial(@PathVariable String razon) {
        try {
            Sucursal sucursal = sucursalServices.findByRazonSocial(razon);
            return ResponseEntity.ok(sucursal);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Crear una nueva sucursal", description = "Crea una nueva sucursal")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "201", description = "Sucursal creada correctamente"), 
        @ApiResponse(responseCode = "404", description = "No se pudo crear la sucursal") 
    }) 
    public ResponseEntity<Sucursal> guardar(@RequestBody Sucursal sucursal) {
        Sucursal sucursalNueva = sucursalServices.save(sucursal);
        return ResponseEntity.status(HttpStatus.CREATED).body(sucursalNueva);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una sucursal", description = "Actualiza todos los datos de la sucursal")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Operación exitosa"), 
        @ApiResponse(responseCode = "404", description = "Sucursal no actualizada") 
    }) 
    public ResponseEntity<Sucursal> actualizar(@PathVariable Long id, @RequestBody Sucursal sucursal) {
        try {
            Sucursal sucursalActualizada = sucursalServices.update(id, sucursal);
            return ResponseEntity.ok(sucursalActualizada);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Actualizar parcialmente una sucursal", description = "Actualiza ciertos datos de una sucursal")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Operación exitosa"), 
        @ApiResponse(responseCode = "404", description = "Sucursal no actualizada") 
    }) 
    public ResponseEntity<Sucursal> actualizarParcial(@PathVariable Long id, @RequestBody Sucursal sucursalParcial) {
        Sucursal sucursalActualizada = sucursalServices.patch(id, sucursalParcial);
        if (sucursalActualizada != null) {
            return ResponseEntity.ok(sucursalActualizada);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una sucursal", description = "Elimina sucursal por id")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "204", description = "Sucursal eliminada correctamente"), 
        @ApiResponse(responseCode = "404", description = "Sucursal no encontrada") 
    }) 
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        try {
            sucursalServices.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
