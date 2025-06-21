package com.ReVive.cl.ReVive.controller;

import com.ReVive.cl.ReVive.assemblers.ResiduosModelAssembler;
import com.ReVive.cl.ReVive.model.Residuos;
import com.ReVive.cl.ReVive.service.ResiduosServices;
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
@RequestMapping("/api/v2/residuos")
public class ResiduosControllerV2 {

    @Autowired
    private ResiduosServices residuosServices;

    @Autowired
    private ResiduosModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<Residuos>>> getAllResiduos() {
        List<Residuos> residuos = residuosServices.findAll();

        if (residuos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<EntityModel<Residuos>> residuosModel = residuos.stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return ResponseEntity.ok(
                CollectionModel.of(residuosModel,
                        linkTo(methodOn(ResiduosControllerV2.class).getAllResiduos()).withSelfRel()));
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Residuos>> getResiduosById(@PathVariable Long id) {
        Residuos residuo = residuosServices.findByIdResiduos(id);
        if (residuo == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(residuo));
    }

    @GetMapping(value = "/cantidad/{cantidad}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<Residuos>>> getResiduosByCantidad(@PathVariable int cantidad) {
        List<Residuos> residuos = residuosServices.findByCantidadResiduos(cantidad);

        if (residuos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<EntityModel<Residuos>> residuosModel = residuos.stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return ResponseEntity.ok(
                CollectionModel.of(residuosModel,
                        linkTo(methodOn(ResiduosControllerV2.class).getResiduosByCantidad(cantidad)).withSelfRel()));
    }

    @GetMapping(value = "/categoria-sucursal", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<Residuos>>> getResiduosByCategoriaYSucursal(
            @RequestParam String categoria,
            @RequestParam String sucursal) {
        List<Residuos> residuos = residuosServices.findByCategoriaAndSucursal(categoria, sucursal);

        if (residuos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<EntityModel<Residuos>> residuosModel = residuos.stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return ResponseEntity.ok(
                CollectionModel.of(residuosModel,
                        linkTo(methodOn(ResiduosControllerV2.class).getResiduosByCategoriaYSucursal(categoria, sucursal)).withSelfRel()));
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Residuos>> createResiduos(@RequestBody Residuos residuo) {
        Residuos nuevo = residuosServices.save(residuo);
        return ResponseEntity
                .created(linkTo(methodOn(ResiduosControllerV2.class).getResiduosById(nuevo.getIdResiduos())).toUri())
                .body(assembler.toModel(nuevo));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Residuos>> updateResiduos(@PathVariable Long id, @RequestBody Residuos residuo) {
        Residuos actualizado = residuosServices.update(id, residuo);
        if (actualizado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(actualizado));
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Residuos>> patchResiduos(@PathVariable Long id, @RequestBody Residuos residuo) {
        Residuos patched = residuosServices.patch(id, residuo);
        if (patched == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(patched));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Void> deleteResiduos(@PathVariable Long id) {
        Residuos residuo = residuosServices.findByIdResiduos(id);
        if (residuo == null) {
            return ResponseEntity.notFound().build();
        }
        residuosServices.delete(id);
        return ResponseEntity.noContent().build();
    }
}
