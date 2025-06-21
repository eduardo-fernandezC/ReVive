package com.ReVive.cl.ReVive.controller;

import com.ReVive.cl.ReVive.assemblers.SectorModelAssembler;
import com.ReVive.cl.ReVive.model.Sector;
import com.ReVive.cl.ReVive.service.SectorServices;
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
@RequestMapping("/api/v2/sectores")
public class SectorControllerV2 {

    @Autowired
    private SectorServices sectorServices;

    @Autowired
    private SectorModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<Sector>>> getAllSectores() {
        List<EntityModel<Sector>> sectores = sectorServices.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        if (sectores.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(
                CollectionModel.of(sectores,
                        linkTo(methodOn(SectorControllerV2.class).getAllSectores()).withSelfRel())
        );
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Sector>> getSectorById(@PathVariable Long id) {
        Sector sector = sectorServices.findByIdSector(id);
        if (sector == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(sector));
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Sector>> createSector(@RequestBody Sector sector) {
        Sector newSector = sectorServices.save(sector);
        return ResponseEntity
                .created(linkTo(methodOn(SectorControllerV2.class).getSectorById(newSector.getIdSector())).toUri())
                .body(assembler.toModel(newSector));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Sector>> updateSector(@PathVariable Long id, @RequestBody Sector sector) {
        Sector updated = sectorServices.update(id, sector);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(updated));
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Sector>> patchSector(@PathVariable Long id, @RequestBody Sector sector) {
        Sector patched = sectorServices.patch(id, sector);
        if (patched == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(patched));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Void> deleteSector(@PathVariable Long id) {
        Sector sector = sectorServices.findByIdSector(id);
        if (sector == null) {
            return ResponseEntity.notFound().build();
        }
        sectorServices.delete(id);
        return ResponseEntity.noContent().build();
    }
}
