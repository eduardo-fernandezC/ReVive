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

import com.ReVive.cl.ReVive.model.Region;
import com.ReVive.cl.ReVive.service.RegionServices;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/regiones")
@Tag(name = "Regiones", description = "Operaciones relacionadas con las regiones")
public class RegionController {

    @Autowired
    private RegionServices regionServices;

    @GetMapping
    @Operation(summary = "Listar todas las regiones", description = "Obtiene una lista de todas las regiones")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Operación exitosa"),
        @ApiResponse(responseCode = "204", description = "No hay regiones disponibles")
    }) 
    public ResponseEntity<List<Region>> listar() {
        List<Region> regiones = regionServices.findAll();
        if (regiones.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(regiones);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener región", description = "Obtiene región por id")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Operación exitosa"),
        @ApiResponse(responseCode = "404", description = "Región no encontrada")
    })  
    public ResponseEntity<Region> buscar(@PathVariable Long id) {
        Region region = regionServices.findByIdRegion(id);
        if (region != null) {
            return ResponseEntity.ok(region);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Crear una nueva región", description = "Guarda una nueva región")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "201", description = "Región creada correctamente"),
        @ApiResponse(responseCode = "400", description = "Solicitud incorrecta")
    }) 
    public ResponseEntity<Region> guardar(@RequestBody Region region) {
        Region regionNueva = regionServices.save(region);
        return ResponseEntity.status(HttpStatus.CREATED).body(regionNueva);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una región", description = "Actualiza todos los datos de una región por id")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Región actualizada correctamente"),
        @ApiResponse(responseCode = "404", description = "Región no encontrada")
    }) 
    public ResponseEntity<Region> actualizar(@PathVariable Long id, @RequestBody Region region) {
        Region actualizado = regionServices.update(id, region);
        if (actualizado != null) {
            return ResponseEntity.ok(actualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Actualizar parcialmente una región", description = "Modifica ciertos campos de una región por id")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Región modificada correctamente"),
        @ApiResponse(responseCode = "404", description = "Región no encontrada")
    }) 
    public ResponseEntity<Region> actualizarParcial(@PathVariable Long id, @RequestBody Region region) {
        Region actualizado = regionServices.patch(id, region);
        if (actualizado != null) {
            return ResponseEntity.ok(actualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una región", description = "Elimina una región por id")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "204", description = "Región eliminada correctamente"),
        @ApiResponse(responseCode = "404", description = "Región no encontrada")
    }) 
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        try {
            regionServices.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
