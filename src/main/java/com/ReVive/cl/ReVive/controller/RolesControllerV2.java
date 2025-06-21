package com.ReVive.cl.ReVive.controller;

import com.ReVive.cl.ReVive.assemblers.RolesModelAssembler;
import com.ReVive.cl.ReVive.model.Roles;
import com.ReVive.cl.ReVive.service.RolesServices;
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
public class RolesControllerV2 {

    @Autowired
    private RolesServices rolesServices;

    @Autowired
    private RolesModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
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
    public ResponseEntity<EntityModel<Roles>> getRolById(@PathVariable Long id) {
        Roles rol = rolesServices.findByIdRoles(id);
        if (rol == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(rol));
    }

    @GetMapping(value = "/nombre/{nombre}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Roles>> getRolByNombre(@PathVariable String nombre) {
        Roles rol = rolesServices.findByNombreRoles(nombre);
        if (rol == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(rol));
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Roles>> createRol(@RequestBody Roles rol) {
        Roles newRol = rolesServices.save(rol);
        return ResponseEntity
                .created(linkTo(methodOn(RolesControllerV2.class).getRolById(newRol.getIdRoles())).toUri())
                .body(assembler.toModel(newRol));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Roles>> updateRol(@PathVariable Long id, @RequestBody Roles rol) {
        Roles updated = rolesServices.update(id, rol);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(updated));
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Roles>> patchRol(@PathVariable Long id, @RequestBody Roles rol) {
        Roles patched = rolesServices.patch(id, rol);
        if (patched == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(patched));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Void> deleteRol(@PathVariable Long id) {
        Roles rol = rolesServices.findByIdRoles(id);
        if (rol == null) {
            return ResponseEntity.notFound().build();
        }
        rolesServices.delete(id);
        return ResponseEntity.noContent().build();
    }
}
