package com.ReVive.cl.ReVive.controller;

import com.ReVive.cl.ReVive.assemblers.RegionModelAssembler;
import com.ReVive.cl.ReVive.model.Region;
import com.ReVive.cl.ReVive.service.RegionServices;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v2/regiones")
public class RegionControllerV2 {

    @Autowired
    private RegionServices regionServices;

    @Autowired
    private RegionModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<Region>>> getAllRegiones() {
        List<EntityModel<Region>> regiones = regionServices.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        if (regiones.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(
                CollectionModel.of(regiones,
                        linkTo(methodOn(RegionControllerV2.class).getAllRegiones()).withSelfRel())
        );
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Region>> getRegionById(@PathVariable Long id) {
        Region region = regionServices.findByIdRegion(id);
        if (region == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(region));
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Region>> createRegion(@RequestBody Region region) {
        Region nueva = regionServices.save(region);
        return ResponseEntity
                .created(linkTo(methodOn(RegionControllerV2.class).getRegionById(nueva.getIdRegion())).toUri())
                .body(assembler.toModel(nueva));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Region>> updateRegion(@PathVariable Long id, @RequestBody Region region) {
        Region actualizada = regionServices.update(id, region);
        if (actualizada == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(actualizada));
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Region>> patchRegion(@PathVariable Long id, @RequestBody Region region) {
        Region modificada = regionServices.patch(id, region);
        if (modificada == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(modificada));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Void> deleteRegion(@PathVariable Long id) {
        Region region = regionServices.findByIdRegion(id);
        if (region == null) {
            return ResponseEntity.notFound().build();
        }
        regionServices.delete(id);
        return ResponseEntity.noContent().build();
    }
}
