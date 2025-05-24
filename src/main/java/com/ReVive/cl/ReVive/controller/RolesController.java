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

@RestController
@RequestMapping("/api/v1/roles")
public class RolesController {

    @Autowired
    private RolesServices rolesServices;

    @GetMapping
    public ResponseEntity<List<Roles>> listar() {
        List<Roles> roles = rolesServices.findAll();
        if (roles.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(roles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Roles> buscar(@PathVariable Long id) {
        Roles rol = rolesServices.findByIdRoles(id);
        if (rol != null) {
            return ResponseEntity.ok(rol);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<Roles> buscarPorNombre(@PathVariable String nombre) {
        Roles rol = rolesServices.findByNombreRoles(nombre);
        if (rol != null) {
            return ResponseEntity.ok(rol);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping
    public ResponseEntity<Roles> guardar(@RequestBody Roles rol) {
        Roles rolGuardar = rolesServices.save(rol);
        return ResponseEntity.status(HttpStatus.CREATED).body(rolGuardar);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Roles> actualizar(@PathVariable Long id, @RequestBody Roles rol) {
        Roles actualizado = rolesServices.update(id, rol);
        if (actualizado != null) {
            return ResponseEntity.ok(actualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Roles> patchRol(@PathVariable Long id, @RequestBody Roles rol) {
        Roles actualizado = rolesServices.patch(id, rol);
        if (actualizado != null) {
            return ResponseEntity.ok(actualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            rolesServices.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
