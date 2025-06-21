package com.ReVive.cl.ReVive.controller;

import com.ReVive.cl.ReVive.assemblers.SucursalModelAssembler;
import com.ReVive.cl.ReVive.model.Sucursal;
import com.ReVive.cl.ReVive.service.SucursalServices;
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
@RequestMapping("/api/v2/sucursales")
public class SucursalControllerV2 {

    @Autowired
    private SucursalServices sucursalServices;

    @Autowired
    private SucursalModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<Sucursal>>> getAllSucursales() {
        List<EntityModel<Sucursal>> sucursales = sucursalServices.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        if (sucursales.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(
                CollectionModel.of(sucursales,
                        linkTo(methodOn(SucursalControllerV2.class).getAllSucursales()).withSelfRel())
        );
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Sucursal>> getSucursalById(@PathVariable Long id) {
        Sucursal sucursal = sucursalServices.findByIdSucursal(id);
        if (sucursal == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(sucursal));
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Sucursal>> createSucursal(@RequestBody Sucursal sucursal) {
        Sucursal newSucursal = sucursalServices.save(sucursal);
        return ResponseEntity
                .created(linkTo(methodOn(SucursalControllerV2.class).getSucursalById(newSucursal.getIdSucursal())).toUri())
                .body(assembler.toModel(newSucursal));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Sucursal>> updateSucursal(@PathVariable Long id, @RequestBody Sucursal sucursal) {
        Sucursal updated = sucursalServices.update(id, sucursal);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(updated));
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Sucursal>> patchSucursal(@PathVariable Long id, @RequestBody Sucursal sucursal) {
        Sucursal patched = sucursalServices.patch(id, sucursal);
        if (patched == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(patched));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Void> deleteSucursal(@PathVariable Long id) {
        Sucursal sucursal = sucursalServices.findByIdSucursal(id);
        if (sucursal == null) {
            return ResponseEntity.notFound().build();
        }
        sucursalServices.delete(id);
        return ResponseEntity.noContent().build();
    }
}
