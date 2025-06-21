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

import com.ReVive.cl.ReVive.model.Roles;
import com.ReVive.cl.ReVive.service.RolesServices;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/roles")
@Tag(name = "Roles", description = "Operaciones relacionadas con roles")
public class RolesController {

    @Autowired
    private RolesServices rolesServices;

    @GetMapping
    @Operation(summary = "Listar todos los roles", description = "Obtiene una lista de todos los roles")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Roles encontrados"),
        @ApiResponse(responseCode = "204", description = "No hay roles disponibles")
    })
    public ResponseEntity<List<Roles>> listar() {
        List<Roles> roles = rolesServices.findAll();
        if (roles.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(roles);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar rol por ID", description = "Obtiene un rol por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Rol encontrado"),
        @ApiResponse(responseCode = "404", description = "Rol no encontrado")
    })
    public ResponseEntity<Roles> buscar(
        @PathVariable Long id) {
        Roles rol = rolesServices.findByIdRoles(id);
        if (rol != null) {
            return ResponseEntity.ok(rol);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/nombre/{nombre}")
    @Operation(summary = "Buscar rol por nombre", description = "Obtiene un rol por su nombre")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Rol encontrado"),
        @ApiResponse(responseCode = "404", description = "Rol no encontrado")
    })
    public ResponseEntity<Roles> buscarPorNombre(
        @PathVariable String nombre) {
        Roles rol = rolesServices.findByNombreRoles(nombre);
        if (rol != null) {
            return ResponseEntity.ok(rol);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Guardar un nuevo rol", description = "Crea un nuevo rol")
    @ApiResponse(responseCode = "201", description = "Rol creado correctamente")
    public ResponseEntity<Roles> guardar(
        @RequestBody Roles rol) {
        Roles rolGuardar = rolesServices.save(rol);
        return ResponseEntity.status(HttpStatus.CREATED).body(rolGuardar);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un rol por ID", description = "Actualiza todos los datos de un rol")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Rol actualizado correctamente"),
        @ApiResponse(responseCode = "404", description = "Rol no encontrado")
    })
    public ResponseEntity<Roles> actualizar(
        @PathVariable Long id,
        @RequestBody Roles rol) {
        Roles actualizado = rolesServices.update(id, rol);
        if (actualizado != null) {
            return ResponseEntity.ok(actualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Modificar parcialmente un rol", description = "Modifica parcialmente un rol")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Rol modificado correctamente"),
        @ApiResponse(responseCode = "404", description = "Rol no encontrado")
    })
    public ResponseEntity<Roles> patchRol(
        @PathVariable Long id,
        @RequestBody Roles rol) {
        Roles actualizado = rolesServices.patch(id, rol);
        if (actualizado != null) {
            return ResponseEntity.ok(actualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un rol por ID", description = "Elimina un rol por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Rol eliminado correctamente"),
        @ApiResponse(responseCode = "404", description = "Rol no encontrado")
    })
    public ResponseEntity<Void> eliminar(
        @PathVariable Long id) {
        try {
            rolesServices.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
