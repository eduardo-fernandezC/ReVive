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

import com.ReVive.cl.ReVive.model.Sector;
import com.ReVive.cl.ReVive.service.SectorServices;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/sectores")
@Tag(name = "Sectores", description = "Operaciones relacionadas con los sectores")
public class SectorController {

    @Autowired
    private SectorServices sectorServices;

    @GetMapping
    @Operation(summary = "Obtener todos los sectores", description = "Obtiene una lista de todos los sectores")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Operación exitosa"), 
        @ApiResponse(responseCode = "204", description = "No hay sectores disponibles") 
    })  
    public ResponseEntity<List<Sector>> listar() {
        List<Sector> sectores = sectorServices.findAll();
        if (sectores.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(sectores);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener sector", description = "Obtiene sector por id")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Operación exitosa"), 
        @ApiResponse(responseCode = "404", description = "Sector no encontrado") 
    })  
    public ResponseEntity<Sector> buscar(@PathVariable Long id) {
        try {
            Sector sector = sectorServices.findByIdSector(id);
            return ResponseEntity.ok(sector);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo sector", description = "Crea un nuevo sector")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "201", description = "Sector creado correctamente"), 
        @ApiResponse(responseCode = "404", description = "No se pudo crear el sector") 
    }) 
    public ResponseEntity<Sector> guardar(@RequestBody Sector sector) {
        Sector sectorNuevo = sectorServices.save(sector);
        return ResponseEntity.status(HttpStatus.CREATED).body(sectorNuevo);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un sector", description = "Actualiza todos los datos del sector")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Operación exitosa"), 
        @ApiResponse(responseCode = "404", description = "Sector no actualizado") 
    }) 
    public ResponseEntity<Sector> actualizar(@PathVariable Long id, @RequestBody Sector sector) {
        try {
            Sector sectorActualizado = sectorServices.update(id, sector);
            return ResponseEntity.ok(sectorActualizado);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Actualizar parcialmente un sector", description = "Actualiza ciertos datos de un sector")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Operación exitosa"), 
        @ApiResponse(responseCode = "404", description = "Sector no actualizado") 
    }) 
    public ResponseEntity<Sector> actualizarParcial(@PathVariable Long id, @RequestBody Sector sectorParcial) {
        Sector sectorActualizado = sectorServices.patch(id, sectorParcial);
        if (sectorActualizado != null) {
            return ResponseEntity.ok(sectorActualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un sector", description = "Elimina sector por id")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "204", description = "Sector eliminado correctamente"), 
        @ApiResponse(responseCode = "404", description = "Sector no encontrado") 
    }) 
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        try {
            sectorServices.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
