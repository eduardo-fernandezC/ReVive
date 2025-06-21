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

import com.ReVive.cl.ReVive.model.Usuarios;
import com.ReVive.cl.ReVive.service.UsuariosServices;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/usuarios")
@Tag(name = "Usuarios", description = "Operaciones relacionadas con los usuarios")
public class UsuariosController {

    @Autowired
    private UsuariosServices usuariosServices;

    @GetMapping
    @Operation(summary = "Listar todos los usuarios", description = "Obtiene una lista completa de usuarios")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Operación exitosa"),
        @ApiResponse(responseCode = "204", description = "No hay usuarios disponibles")
    })
    public ResponseEntity<List<Usuarios>> listar() {
        List<Usuarios> usuarios = usuariosServices.findAll();
        if (usuarios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener usuario por ID", description = "Busca un usuario por su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<Usuarios> buscar(@PathVariable Long id) {
        try {
            Usuarios usuario = usuariosServices.findById(id);
            return ResponseEntity.ok(usuario);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/nombre/{nombre}")
    @Operation(summary = "Buscar usuarios por nombre", description = "Obtiene usuarios que coinciden con el nombre")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuarios encontrados"),
        @ApiResponse(responseCode = "204", description = "No hay usuarios con ese nombre")
    })
    public ResponseEntity<List<Usuarios>> buscarPorNombre(@PathVariable String nombre) {
        List<Usuarios> usuarios = usuariosServices.findByNombreUsuarios(nombre);
        if (usuarios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/run/{run}")
    @Operation(summary = "Buscar usuario por RUN", description = "Obtiene un usuario por su RUN")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<Usuarios> buscarPorRun(@PathVariable String run) {
        try {
            Usuarios usuario = usuariosServices.findByRunUsuario(run);
            return ResponseEntity.ok(usuario);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/rol/{idRol}")
    @Operation(summary = "Buscar usuarios por ID de rol", description = "Obtiene usuarios que tienen un ID de rol específico")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuarios encontrados"),
        @ApiResponse(responseCode = "204", description = "No hay usuarios con ese rol")
    })
    public ResponseEntity<List<Usuarios>> buscarPorRol(@PathVariable Long idRol) {
        List<Usuarios> usuarios = usuariosServices.findByIdRol(idRol);
        if (usuarios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/rol/nombre/{nombreRol}")
    @Operation(summary = "Buscar usuarios por nombre de rol", description = "Obtiene usuarios que tienen un nombre de rol específico")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuarios encontrados"),
        @ApiResponse(responseCode = "204", description = "No hay usuarios con ese nombre de rol")
    })
    public ResponseEntity<List<Usuarios>> buscarPorNombreRol(@PathVariable String nombreRol) {
        List<Usuarios> usuarios = usuariosServices.findByNombreRol(nombreRol);
        if (usuarios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/salario/mayorPromedio")
    @Operation(summary = "Listar usuarios con salario mayor al promedio", description = "Obtiene usuarios cuyo salario es mayor al promedio")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuarios encontrados"),
        @ApiResponse(responseCode = "204", description = "No hay usuarios con salario mayor al promedio")
    })
    public ResponseEntity<List<Usuarios>> usuariosConSalarioMayorAlPromedio() {
        List<Usuarios> usuarios = usuariosServices.findUsuariosConSalarioMayorAlPromedio();
        if (usuarios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/rol-sucursal")
    @Operation(summary = "Buscar usuarios por rol y sucursal", description = "Obtiene usuarios filtrados por nombre de rol y nombre de sucursal")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuarios encontrados"),
        @ApiResponse(responseCode = "204", description = "No hay usuarios para el rol y sucursal especificados")
    })
    public ResponseEntity<List<Usuarios>> buscarPorRolYSucursal(
        @RequestParam String rol,
        @RequestParam String sucursal) {
        List<Usuarios> usuarios = usuariosServices.findByRolAndSucursal(rol, sucursal);
        if (usuarios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(usuarios);
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo usuario", description = "Guarda un usuario nuevo")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Usuario creado correctamente"),
        @ApiResponse(responseCode = "400", description = "Error al crear usuario")
    })
    public ResponseEntity<Usuarios> guardar(@RequestBody Usuarios usuario) {
        Usuarios usuarioNuevo = usuariosServices.save(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioNuevo);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un usuario", description = "Actualiza todos los datos del usuario")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuario actualizado correctamente"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<Usuarios> actualizar(@PathVariable Long id, @RequestBody Usuarios usuario) {
        try {
            Usuarios usuarioActualizado = usuariosServices.update(id, usuario);
            return ResponseEntity.ok(usuarioActualizado);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Actualizar parcialmente un usuario", description = "Actualiza ciertos campos del usuario")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuario modificado correctamente"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<Usuarios> patchUsuario(@PathVariable Long id, @RequestBody Usuarios usuario) {
        Usuarios usuarioActualizado = usuariosServices.patch(id, usuario);
        if (usuarioActualizado != null) {
            return ResponseEntity.ok(usuarioActualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un usuario", description = "Elimina un usuario por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Usuario eliminado correctamente"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        try {
            usuariosServices.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
