package com.ReVive.cl.ReVive.controller;

import com.ReVive.cl.ReVive.assemblers.SectorModelAssembler;
import com.ReVive.cl.ReVive.model.Sector;
import com.ReVive.cl.ReVive.service.SectorServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Sectores V2", description = "Operaciones para gestión de sectores con HATEOAS")
public class SectorControllerV2 {

    @Autowired
    private SectorServices sectorServices;

    @Autowired
    private SectorModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Listar todos los sectores", description = "Devuelve la lista completa de sectores")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Sectores encontrados"),
        @ApiResponse(responseCode = "204", description = "No hay sectores registrados")
    })
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
    @Operation(summary = "Obtener sector por ID", description = "Busca un sector usando su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Sector encontrado"),
        @ApiResponse(responseCode = "404", description = "Sector no encontrado")
    })
    public ResponseEntity<EntityModel<Sector>> getSectorById(
            @Parameter(description = "ID del sector a buscar") @PathVariable Long id) {
        Sector sector = sectorServices.findByIdSector(id);
        if (sector == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(sector));
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Crear un nuevo sector", description = "Registra un sector nuevo en el sistema")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Sector creado correctamente"),
        @ApiResponse(responseCode = "400", description = "Error en los datos enviados")
    })
    public ResponseEntity<EntityModel<Sector>> createSector(@RequestBody Sector sector) {
        Sector newSector = sectorServices.save(sector);
        return ResponseEntity
                .created(linkTo(methodOn(SectorControllerV2.class).getSectorById(newSector.getIdSector())).toUri())
                .body(assembler.toModel(newSector));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Actualizar un sector", description = "Actualiza completamente un sector existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Sector actualizado correctamente"),
        @ApiResponse(responseCode = "404", description = "Sector no encontrado")
    })
    public ResponseEntity<EntityModel<Sector>> updateSector(
            @Parameter(description = "ID del sector a actualizar") @PathVariable Long id,
            @RequestBody Sector sector) {
        Sector updated = sectorServices.update(id, sector);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(updated));
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Modificar parcialmente un sector", description = "Actualiza parcialmente los datos de un sector")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Sector modificado correctamente"),
        @ApiResponse(responseCode = "404", description = "Sector no encontrado")
    })
    public ResponseEntity<EntityModel<Sector>> patchSector(
            @Parameter(description = "ID del sector a modificar") @PathVariable Long id,
            @RequestBody Sector sector) {
        Sector patched = sectorServices.patch(id, sector);
        if (patched == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(patched));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Eliminar un sector", description = "Elimina un sector por su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Sector eliminado correctamente"),
        @ApiResponse(responseCode = "404", description = "Sector no encontrado")
    })
    public ResponseEntity<Void> deleteSector(
            @Parameter(description = "ID del sector a eliminar") @PathVariable Long id) {
        Sector sector = sectorServices.findByIdSector(id);
        if (sector == null) {
            return ResponseEntity.notFound().build();
        }
        sectorServices.delete(id);
        return ResponseEntity.noContent().build();
    }
}
