package com.ReVive.cl.ReVive.controller;

import com.ReVive.cl.ReVive.assemblers.CategoriasResiduosModelAssembler;
import com.ReVive.cl.ReVive.model.CategoriasResiduos;
import com.ReVive.cl.ReVive.service.CategoriasResiduosServices;
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
@RequestMapping("/api/v2/categoriasResiduos")
public class CategoriasResiduosControllerV2 {

    @Autowired
    private CategoriasResiduosServices categoriasResiduosServices;

    @Autowired
    private CategoriasResiduosModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<CategoriasResiduos>>> getAllCategorias() {
        List<EntityModel<CategoriasResiduos>> categorias = categoriasResiduosServices.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        if (categorias.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(CollectionModel.of(
                categorias,
                linkTo(methodOn(CategoriasResiduosControllerV2.class).getAllCategorias()).withSelfRel()
        ));
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<CategoriasResiduos>> getCategoriaById(@PathVariable Long id) {
        CategoriasResiduos categoria = categoriasResiduosServices.findByIdCatesResiduos(id);
        if (categoria == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(categoria));
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<CategoriasResiduos>> createCategoria(@RequestBody CategoriasResiduos categoria) {
        CategoriasResiduos nueva = categoriasResiduosServices.save(categoria);
        return ResponseEntity
                .created(linkTo(methodOn(CategoriasResiduosControllerV2.class).getCategoriaById(nueva.getIdCatesResiduos())).toUri())
                .body(assembler.toModel(nueva));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<CategoriasResiduos>> updateCategoria(@PathVariable Long id, @RequestBody CategoriasResiduos categoria) {
        categoria.setIdCatesResiduos(id);
        CategoriasResiduos actualizada = categoriasResiduosServices.update(id, categoria);
        if (actualizada == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(actualizada));
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<CategoriasResiduos>> patchCategoria(@PathVariable Long id, @RequestBody CategoriasResiduos categoria) {
        CategoriasResiduos actualizada = categoriasResiduosServices.patch(id, categoria);
        if (actualizada == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(actualizada));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Void> deleteCategoria(@PathVariable Long id) {
        CategoriasResiduos categoria = categoriasResiduosServices.findByIdCatesResiduos(id);
        if (categoria == null) {
            return ResponseEntity.notFound().build();
        }
        categoriasResiduosServices.delete(id);
        return ResponseEntity.noContent().build();
    }
}
