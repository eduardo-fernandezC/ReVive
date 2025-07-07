package com.ReVive.cl.ReVive.controller;

import com.ReVive.cl.ReVive.assemblers.RolesModelAssembler;
import com.ReVive.cl.ReVive.model.Roles;
import com.ReVive.cl.ReVive.service.RolesServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
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
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v2/roles")
@Tag(name = "Roles V2", description = "Operaciones para gestión de roles con HATEOAS")
public class RolesControllerV2 {

    @Autowired
    private RolesServices rolesServices;

    @Autowired
    private RolesModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Listar todos los roles", description = "Devuelve la lista completa de roles")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Roles encontrados"),
        @ApiResponse(responseCode = "204", description = "No hay roles registrados")
    })
    public ResponseEntity<CollectionModel<EntityModel<Roles>>> getAllRoles() {
        List<EntityModel<Roles>> roles = rolesServices.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        if (roles.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(
                CollectionModel.of(roles,
                        linkTo(methodOn(RolesControllerV2.class).getAllRoles()).withSelfRel())
        );
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtener rol por ID", description = "Busca un rol usando su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Rol encontrado"),
        @ApiResponse(responseCode = "404", description = "Rol no encontrado")
    })
    public ResponseEntity<EntityModel<Roles>> getRolById(
            @Parameter(description = "ID del rol a buscar") @PathVariable Long id) {
        Roles rol = rolesServices.findByIdRoles(id);
        if (rol == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(rol));
    }

    @GetMapping(value = "/nombre/{nombre}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtener rol por nombre", description = "Busca un rol por su nombre")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Rol encontrado"),
        @ApiResponse(responseCode = "404", description = "Rol no encontrado")
    })
    public ResponseEntity<EntityModel<Roles>> getRolByNombre(
            @Parameter(description = "Nombre del rol a buscar") @PathVariable String nombre) {
        Roles rol = rolesServices.findByNombreRoles(nombre);
        if (rol == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(rol));
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Crear un nuevo rol", description = "Registra un rol nuevo en el sistema")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Rol creado correctamente"),
        @ApiResponse(responseCode = "400", description = "Error en los datos enviados")
    })
    public ResponseEntity<EntityModel<Roles>> createRol(@RequestBody Roles rol) {
        Roles newRol = rolesServices.save(rol);
        return ResponseEntity
                .created(linkTo(methodOn(RolesControllerV2.class).getRolById(newRol.getIdRoles())).toUri())
                .body(assembler.toModel(newRol));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Actualizar un rol", description = "Actualiza completamente un rol existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Rol actualizado correctamente"),
        @ApiResponse(responseCode = "404", description = "Rol no encontrado")
    })
    public ResponseEntity<EntityModel<Roles>> updateRol(
            @Parameter(description = "ID del rol a actualizar") @PathVariable Long id,
            @RequestBody Roles rol) {
        Roles updated = rolesServices.update(id, rol);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(updated));
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Modificar parcialmente un rol", description = "Actualiza parcialmente los datos de un rol")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Rol modificado correctamente"),
        @ApiResponse(responseCode = "404", description = "Rol no encontrado")
    })
    public ResponseEntity<EntityModel<Roles>> patchRol(
            @Parameter(description = "ID del rol a modificar") @PathVariable Long id,
            @RequestBody Roles rol) {
        Roles patched = rolesServices.patch(id, rol);
        if (patched == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(patched));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Eliminar un rol", description = "Elimina un rol por su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Rol eliminado correctamente"),
        @ApiResponse(responseCode = "404", description = "Rol no encontrado")
    })
    public ResponseEntity<Void> deleteRol(
            @Parameter(description = "ID del rol a eliminar") @PathVariable Long id) {
        Roles rol = rolesServices.findByIdRoles(id);
        if (rol == null) {
            return ResponseEntity.notFound().build();
        }
        rolesServices.delete(id);
        return ResponseEntity.noContent().build();
    }
}
