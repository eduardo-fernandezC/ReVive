package com.ReVive.cl.ReVive.controller;

import com.ReVive.cl.ReVive.assemblers.UsuariosModelAssembler;
import com.ReVive.cl.ReVive.model.Usuarios;
import com.ReVive.cl.ReVive.service.UsuariosServices;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v2/usuarios")
@Tag(name = "Usuarios V2", description = "Operaciones para gestionar usuarios con HATEOAS")
public class UsuariosControllerV2 {

    @Autowired
    private UsuariosServices usuariosServices;

    @Autowired
    private UsuariosModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Listar todos los usuarios", description = "Obtiene todos los usuarios registrados")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de usuarios obtenida"),
        @ApiResponse(responseCode = "204", description = "No hay usuarios registrados")
    })
    public ResponseEntity<CollectionModel<EntityModel<Usuarios>>> getAllUsuarios() {
        List<EntityModel<Usuarios>> usuarios = usuariosServices.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        if (usuarios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(
                CollectionModel.of(usuarios,
                        linkTo(methodOn(UsuariosControllerV2.class).getAllUsuarios()).withSelfRel())
        );
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtener usuario por ID", description = "Busca un usuario usando su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<EntityModel<Usuarios>> getUsuarioById(
            @Parameter(description = "ID del usuario a buscar") @PathVariable Long id) {
        Usuarios usuario = usuariosServices.findById(id);
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(usuario));
    }

    @GetMapping(value = "/nombre/{nombre}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Buscar usuarios por nombre", description = "Obtiene usuarios que coincidan con el nombre")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuarios encontrados"),
        @ApiResponse(responseCode = "204", description = "No se encontraron usuarios")
    })
    public ResponseEntity<CollectionModel<EntityModel<Usuarios>>> getUsuariosByNombre(
            @Parameter(description = "Nombre del usuario a buscar") @PathVariable String nombre) {
        List<EntityModel<Usuarios>> usuarios = usuariosServices.findByNombreUsuarios(nombre).stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        if (usuarios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(CollectionModel.of(usuarios,
                linkTo(methodOn(UsuariosControllerV2.class).getUsuariosByNombre(nombre)).withSelfRel()));
    }

    @GetMapping(value = "/run/{run}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Buscar usuario por RUN", description = "Obtiene un usuario por su RUN")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<EntityModel<Usuarios>> getUsuarioByRun(
            @Parameter(description = "RUN del usuario") @PathVariable String run) {
        Usuarios usuario = usuariosServices.findByRunUsuario(run);
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(usuario));
    }

    @GetMapping(value = "/rol/{idRol}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Buscar usuarios por ID de rol", description = "Obtiene usuarios según el ID del rol")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuarios encontrados"),
        @ApiResponse(responseCode = "204", description = "No se encontraron usuarios")
    })
    public ResponseEntity<CollectionModel<EntityModel<Usuarios>>> getUsuariosByRol(
            @Parameter(description = "ID del rol") @PathVariable Long idRol) {
        List<EntityModel<Usuarios>> usuarios = usuariosServices.findByIdRol(idRol).stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        if (usuarios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(CollectionModel.of(usuarios,
                linkTo(methodOn(UsuariosControllerV2.class).getUsuariosByRol(idRol)).withSelfRel()));
    }

    @GetMapping(value = "/rol/nombre/{nombreRol}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Buscar usuarios por nombre de rol", description = "Obtiene usuarios según el nombre del rol")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuarios encontrados"),
        @ApiResponse(responseCode = "204", description = "No se encontraron usuarios")
    })
    public ResponseEntity<CollectionModel<EntityModel<Usuarios>>> getUsuariosByNombreRol(
            @Parameter(description = "Nombre del rol") @PathVariable String nombreRol) {
        List<EntityModel<Usuarios>> usuarios = usuariosServices.findByNombreRol(nombreRol).stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        if (usuarios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(CollectionModel.of(usuarios,
                linkTo(methodOn(UsuariosControllerV2.class).getUsuariosByNombreRol(nombreRol)).withSelfRel()));
    }

    @GetMapping(value = "/salario/mayorPromedio", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Usuarios con salario mayor al promedio", description = "Lista usuarios que ganan más que el salario promedio")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuarios encontrados"),
        @ApiResponse(responseCode = "204", description = "No se encontraron usuarios")
    })
    public ResponseEntity<CollectionModel<EntityModel<Usuarios>>> getUsuariosConSalarioMayorAlPromedio() {
        List<EntityModel<Usuarios>> usuarios = usuariosServices.findUsuariosConSalarioMayorAlPromedio().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        if (usuarios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(CollectionModel.of(usuarios,
                linkTo(methodOn(UsuariosControllerV2.class).getUsuariosConSalarioMayorAlPromedio()).withSelfRel()));
    }

    @GetMapping(value = "/rol-sucursal", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Buscar usuarios por rol y sucursal", description = "Obtiene usuarios filtrando por rol y sucursal")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuarios encontrados"),
        @ApiResponse(responseCode = "204", description = "No se encontraron usuarios")
    })
    public ResponseEntity<CollectionModel<EntityModel<Usuarios>>> getUsuariosByRolYSucursal(
            @Parameter(description = "Nombre del rol") @RequestParam String rol,
            @Parameter(description = "Nombre de la sucursal") @RequestParam String sucursal) {
        List<EntityModel<Usuarios>> usuarios = usuariosServices.findByRolAndSucursal(rol, sucursal).stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        if (usuarios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(CollectionModel.of(usuarios,
                linkTo(methodOn(UsuariosControllerV2.class).getUsuariosByRolYSucursal(rol, sucursal)).withSelfRel()));
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Crear un nuevo usuario", description = "Registra un usuario nuevo")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Usuario creado correctamente"),
        @ApiResponse(responseCode = "400", description = "Error en los datos enviados")
    })
    public ResponseEntity<EntityModel<Usuarios>> createUsuario(@RequestBody Usuarios usuario) {
        Usuarios newUsuario = usuariosServices.save(usuario);
        return ResponseEntity
                .created(linkTo(methodOn(UsuariosControllerV2.class).getUsuarioById(newUsuario.getIdUsuario())).toUri())
                .body(assembler.toModel(newUsuario));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Actualizar usuario", description = "Actualiza todos los datos de un usuario")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuario actualizado"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<EntityModel<Usuarios>> updateUsuario(
            @Parameter(description = "ID del usuario a actualizar") @PathVariable Long id,
            @RequestBody Usuarios usuario) {
        Usuarios updated = usuariosServices.update(id, usuario);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(updated));
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Actualizar parcialmente usuario", description = "Modifica parcialmente los datos de un usuario")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuario actualizado"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<EntityModel<Usuarios>> patchUsuario(
            @Parameter(description = "ID del usuario a modificar") @PathVariable Long id,
            @RequestBody Usuarios usuario) {
        Usuarios patched = usuariosServices.patch(id, usuario);
        if (patched == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(patched));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Eliminar usuario", description = "Elimina un usuario por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Usuario eliminado"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<Void> deleteUsuario(
            @Parameter(description = "ID del usuario a eliminar") @PathVariable Long id) {
        Usuarios usuario = usuariosServices.findById(id);
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }
        usuariosServices.delete(id);
        return ResponseEntity.noContent().build();
    }
}
