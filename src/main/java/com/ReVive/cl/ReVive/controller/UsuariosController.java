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

import com.ReVive.cl.ReVive.model.Usuarios;
import com.ReVive.cl.ReVive.service.UsuariosServices;

@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuariosController {

    @Autowired
    private UsuariosServices usuariosServices;

    @GetMapping
    public ResponseEntity<List<Usuarios>> listar() {
        List<Usuarios> usuarios = usuariosServices.findAll();
        if (usuarios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuarios> buscar(@PathVariable Long id) {
        Usuarios usuario = usuariosServices.findById(id);
        if (usuario != null) {
            return ResponseEntity.ok(usuario);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<List<Usuarios>> buscarPorNombre(@PathVariable String nombre) {
        List<Usuarios> usuarios = usuariosServices.findByNombreUsuarios(nombre);
        if (usuarios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/run/{run}")
    public ResponseEntity<Usuarios> buscarPorRun(@PathVariable String run) {
        Usuarios usuario = usuariosServices.findByRunUsuario(run);
        if (usuario != null) {
            return ResponseEntity.ok(usuario);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/rol/{idRol}")
    public ResponseEntity<List<Usuarios>> buscarPorRol(@PathVariable Long idRol) {
        List<Usuarios> usuarios = usuariosServices.findByIdRol(idRol);
        if (usuarios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/rol/nombre/{nombreRol}")
    public ResponseEntity<List<Usuarios>> buscarPorNombreRol(@PathVariable String nombreRol) {
        List<Usuarios> usuarios = usuariosServices.findByNombreRol(nombreRol);
        if (usuarios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/salario/mayorPromedio")
    public ResponseEntity<List<Usuarios>> usuariosConSalarioMayorAlPromedio() {
        List<Usuarios> usuarios = usuariosServices.findUsuariosConSalarioMayorAlPromedio();
        if (usuarios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(usuarios);
    }


    @PostMapping
    public ResponseEntity<Usuarios> guardar(@RequestBody Usuarios usuario) {
        Usuarios usuarioGuardar = usuariosServices.save(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioGuardar);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuarios> actualizar(@PathVariable Long id, @RequestBody Usuarios usuario) {
        Usuarios actualizado = usuariosServices.update(id, usuario);
        if (actualizado != null) {
            return ResponseEntity.ok(actualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Usuarios> patchUsuario(@PathVariable Long id, @RequestBody Usuarios usuario) {
        Usuarios actualizado = usuariosServices.patch(id, usuario);
        if (actualizado != null) {
            return ResponseEntity.ok(actualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            usuariosServices.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
