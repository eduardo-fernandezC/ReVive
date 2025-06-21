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

import com.ReVive.cl.ReVive.model.Camiones;
import com.ReVive.cl.ReVive.service.CamionesServices;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/camiones")
@Tag(name = "Camiones", description = "Operaciones relacionadas con los camiones")
public class CamionesController {

    @Autowired
    private CamionesServices camionesServices;

    @GetMapping
    @Operation(summary = "Listar todos los camiones", description = "Obtiene una lista de todos los camiones")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Camiones encontrados"),
        @ApiResponse(responseCode = "204", description = "No hay camiones disponibles")
    })
    public ResponseEntity<List<Camiones>> listar() {
        List<Camiones> camiones = camionesServices.findAll();
        if (camiones.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(camiones);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar camion por ID", description = "Obtiene un camion por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "camion encontrado"),
        @ApiResponse(responseCode = "404", description = "camion no encontrado")
    })
    public ResponseEntity<Camiones> buscar(@PathVariable Long id) {
        Camiones camion = camionesServices.findById(id);
        if (camion != null) {
            return ResponseEntity.ok(camion);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/buscarPatente/{patente}")
    @Operation(summary = "Buscar camión por patente", description = "Obtiene un camión por su patente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Camión encontrado"),
        @ApiResponse(responseCode = "404", description = "Camión no encontrado")
    })
    public ResponseEntity<Camiones> buscarPorPatente(@PathVariable String patente) {
        Camiones camion = camionesServices.buscarPorPatente(patente);
        if (camion != null) {
            return ResponseEntity.ok(camion);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/buscarPorSucursalYComuna")
    @Operation(summary = "Buscar camiones por sucursal y comuna", description = "Obtiene camiones por nombre de sucursal y comuna")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Camiones encontrados"),
        @ApiResponse(responseCode = "204", description = "No hay camiones para los criterios indicados")
    })
    public ResponseEntity<List<Camiones>> buscarPorSucursalYComuna(
            @RequestParam String nombreSucursal,
            @RequestParam String nombreComuna) {
        List<Camiones> camiones = camionesServices.buscarPorSucursalYComuna(nombreSucursal, nombreComuna);
        if (camiones.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(camiones);
    }

    @PostMapping
    @Operation(summary = "Guardar un nuevo camión", description = "Crea un nuevo camión")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Camión creado correctamente"),
        @ApiResponse(responseCode = "400", description = "Error al crear el camión")
    })
    public ResponseEntity<Camiones> guardar(@RequestBody Camiones camiones) {
        Camiones camionesGuardado = camionesServices.save(camiones);
        return ResponseEntity.status(HttpStatus.CREATED).body(camionesGuardado);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un camion por ID", description = "Actualiza todos los datos del camion")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Camion actualizado correctamente"),
        @ApiResponse(responseCode = "404", description = "Camion no encontrado")
    })
    public ResponseEntity<Camiones> actualizar(@PathVariable Long id, @RequestBody Camiones camiones) {
        Camiones actualizado = camionesServices.update(id, camiones);
        if (actualizado != null) {
            return ResponseEntity.ok(actualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Modificar parcialmente un camion", description = "Modifica parcialmente un camion por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "camion modificado correctamente"),
        @ApiResponse(responseCode = "404", description = "camion no encontrado")
    })
    public ResponseEntity<Camiones> patchCamiones(@PathVariable Long id, @RequestBody Camiones camiones) {
        Camiones actualizado = camionesServices.patch(id, camiones);
        if (actualizado != null) {
            return ResponseEntity.ok(actualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un camion por ID", description = "Elimina un camion por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "camion eliminado correctamente"),
        @ApiResponse(responseCode = "404", description = "camion no encontrado")
    })
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        try {
            camionesServices.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
