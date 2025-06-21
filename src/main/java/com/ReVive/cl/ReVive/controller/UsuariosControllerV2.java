package com.ReVive.cl.ReVive.controller;

import com.ReVive.cl.ReVive.assemblers.UsuariosModelAssembler;
import com.ReVive.cl.ReVive.model.Usuarios;
import com.ReVive.cl.ReVive.service.UsuariosServices;
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
public class UsuariosControllerV2 {

    @Autowired
    private UsuariosServices usuariosServices;

    @Autowired
    private UsuariosModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
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
    public ResponseEntity<EntityModel<Usuarios>> getUsuarioById(@PathVariable Long id) {
        Usuarios usuario = usuariosServices.findById(id);
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(usuario));
    }

    @GetMapping(value = "/nombre/{nombre}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<Usuarios>>> getUsuariosByNombre(@PathVariable String nombre) {
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
    public ResponseEntity<EntityModel<Usuarios>> getUsuarioByRun(@PathVariable String run) {
        Usuarios usuario = usuariosServices.findByRunUsuario(run);
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(usuario));
    }

    @GetMapping(value = "/rol/{idRol}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<Usuarios>>> getUsuariosByRol(@PathVariable Long idRol) {
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
    public ResponseEntity<CollectionModel<EntityModel<Usuarios>>> getUsuariosByNombreRol(@PathVariable String nombreRol) {
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
    public ResponseEntity<CollectionModel<EntityModel<Usuarios>>> getUsuariosByRolYSucursal(
            @RequestParam String rol,
            @RequestParam String sucursal) {
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
    public ResponseEntity<EntityModel<Usuarios>> createUsuario(@RequestBody Usuarios usuario) {
        Usuarios newUsuario = usuariosServices.save(usuario);
        return ResponseEntity
                .created(linkTo(methodOn(UsuariosControllerV2.class).getUsuarioById(newUsuario.getIdUsuario())).toUri())
                .body(assembler.toModel(newUsuario));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Usuarios>> updateUsuario(@PathVariable Long id, @RequestBody Usuarios usuario) {
        Usuarios updated = usuariosServices.update(id, usuario);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(updated));
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Usuarios>> patchUsuario(@PathVariable Long id, @RequestBody Usuarios usuario) {
        Usuarios patched = usuariosServices.patch(id, usuario);
        if (patched == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(patched));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Void> deleteUsuario(@PathVariable Long id) {
        Usuarios usuario = usuariosServices.findById(id);
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }
        usuariosServices.delete(id);
        return ResponseEntity.noContent().build();
    }
}
