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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ReVive.cl.ReVive.model.Comuna;
import com.ReVive.cl.ReVive.service.ComunaServices;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/comunas")
@Tag(name = "Comunas", description = "Operaciones relacionadas con las comunas")
public class ComunaController {

    @Autowired
    private ComunaServices comunaServices;

    @GetMapping
    @Operation(summary = "Listar todas las comunas", description = "Obtiene una lista de todas las comunas")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Comunas encontradas"),
        @ApiResponse(responseCode = "204", description = "No hay comunas disponibles")
    })
    public ResponseEntity<List<Comuna>> listar() {
        List<Comuna> comunas = comunaServices.findAll();
        if (comunas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(comunas);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar comuna por ID", description = "Obtiene una comuna por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Comuna encontrada"),
        @ApiResponse(responseCode = "404", description = "Comuna no encontrada")
    })
    public ResponseEntity<Comuna> buscar(@PathVariable Long id) {
        Comuna comuna = comunaServices.findById(id);
        return (comuna != null) ? ResponseEntity.ok(comuna) : ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(summary = "Guardar una nueva comuna", description = "Crea una nueva comuna")
    @ApiResponse(responseCode = "201", description = "Comuna creada correctamente")
    public ResponseEntity<Comuna> guardar(@RequestBody Comuna comuna) {
        Comuna comunaGuardada = comunaServices.save(comuna);
        return ResponseEntity.status(HttpStatus.CREATED).body(comunaGuardada);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una comuna por ID", description = "Actualiza una comuna existente por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Comuna actualizada correctamente"),
        @ApiResponse(responseCode = "404", description = "Comuna no encontrada")
    })
    public ResponseEntity<Comuna> actualizar(@PathVariable Long id, @RequestBody Comuna comuna) {
        Comuna actualizada = comunaServices.update(id, comuna);
        return (actualizada != null) ? ResponseEntity.ok(actualizada) : ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Modificar parcialmente una comuna", description = "Modifica parcialmente una comuna")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Comuna modificada correctamente"),
        @ApiResponse(responseCode = "404", description = "Comuna no encontrada")
    })
    public ResponseEntity<Comuna> patchComuna(@PathVariable Long id, @RequestBody Comuna comuna) {
        Comuna actualizada = comunaServices.patch(id, comuna);
        return (actualizada != null) ? ResponseEntity.ok(actualizada) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una comuna por ID", description = "Elimina una comuna por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Comuna eliminada correctamente"),
        @ApiResponse(responseCode = "404", description = "Comuna no encontrada")
    })
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        try {
            comunaServices.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
