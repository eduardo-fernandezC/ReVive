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

import com.ReVive.cl.ReVive.model.Residuos;
import com.ReVive.cl.ReVive.service.ResiduosServices;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/residuos")
@Tag(name = "Residuos", description = "Operaciones relacionadas con residuos")
public class ResiduosController {

    @Autowired
    private ResiduosServices residuosServices;

    @GetMapping
    @Operation(summary = "Listar todos los residuos", description = "Obtiene una lista de todos los residuos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Residuos encontrados"),
        @ApiResponse(responseCode = "204", description = "No hay residuos disponibles")
    })
    public ResponseEntity<List<Residuos>> listar() {
        List<Residuos> residuos = residuosServices.findAll();
        if (residuos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(residuos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar residuo por ID", description = "Obtiene residuo por id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Residuo encontrado"),
        @ApiResponse(responseCode = "404", description = "Residuo no encontrado")
    })
    public ResponseEntity<Residuos> buscar(
        @PathVariable Long id) {
        Residuos residuo = residuosServices.findByIdResiduos(id);
        if (residuo != null) {
            return ResponseEntity.ok(residuo);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/cantidad/{cantidad}")
    @Operation(summary = "Buscar residuos por cantidad", description = "Obtiene residuos filtrados por cantidad")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Residuos encontrados"),
        @ApiResponse(responseCode = "204", description = "No hay residuos con esa cantidad")
    })
    public ResponseEntity<List<Residuos>> buscarPorCantidad(
        @PathVariable int cantidad) {
        List<Residuos> residuos = residuosServices.findByCantidadResiduos(cantidad);
        if (residuos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(residuos);
    }

    // /api/v1/residuos/categoria-sucursal?categoria=Papel&sucursal=Sucursal1
    @GetMapping("/categoria-sucursal")
    @Operation(summary = "Buscar residuos por categoría y sucursal", description = "Obtiene residuos filtrados por categoría y sucursal")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Residuos encontrados"),
        @ApiResponse(responseCode = "204", description = "No hay residuos para esa categoría y sucursal")
    })
    public ResponseEntity<List<Residuos>> buscarPorCategoriaYSucursal(
        @RequestParam String categoria,
        @RequestParam String sucursal) {
        List<Residuos> residuos = residuosServices.findByCategoriaAndSucursal(categoria, sucursal);
        if (residuos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(residuos);
    }

    @PostMapping
    @Operation(summary = "Guardar un nuevo residuo", description = "Guarda un nuevo residuo")
    @ApiResponse(responseCode = "201", description = "Residuo creado correctamente")
    public ResponseEntity<Residuos> guardar(
        @RequestBody Residuos residuo) {
        Residuos residuoGuardar = residuosServices.save(residuo);
        return ResponseEntity.status(HttpStatus.CREATED).body(residuoGuardar);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un residuo por ID", description = "Actualiza todos los datos de un residuo por id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Residuo actualizado correctamente"),
        @ApiResponse(responseCode = "404", description = "Residuo no encontrado")
    })
    public ResponseEntity<Residuos> actualizar(
        @PathVariable Long id,
        @RequestBody Residuos residuo) {
        Residuos actualizado = residuosServices.update(id, residuo);
        if (actualizado != null) {
            return ResponseEntity.ok(actualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Modificar parcialmente un residuo", description = "Modifica ciertos campos de un residuo por id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Residuo modificado correctamente"),
        @ApiResponse(responseCode = "404", description = "Residuo no encontrado")
    })
    public ResponseEntity<Residuos> patchResiduo(
        @PathVariable Long id,
        @RequestBody Residuos residuo) {
        Residuos actualizado = residuosServices.patch(id, residuo);
        if (actualizado != null) {
            return ResponseEntity.ok(actualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un residuo por ID", description = "Elimina un residuo por id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Residuo eliminado correctamente"),
        @ApiResponse(responseCode = "404", description = "Residuo no encontrado")
    })
    public ResponseEntity<Void> eliminar(
        @PathVariable Long id) {
        try {
            residuosServices.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
