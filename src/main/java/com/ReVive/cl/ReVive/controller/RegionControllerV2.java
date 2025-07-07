package com.ReVive.cl.ReVive.controller;

import com.ReVive.cl.ReVive.assemblers.RegionModelAssembler;
import com.ReVive.cl.ReVive.model.Region;
import com.ReVive.cl.ReVive.service.RegionServices;
import io.swagger.v3.oas.annotations.Operation;
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
@RequestMapping("/api/v2/regiones")
@Tag(name = "Región V2", description = "Operaciones HATEOAS relacionadas con regiones")
public class RegionControllerV2 {

    @Autowired
    private RegionServices regionServices;

    @Autowired
    private RegionModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Listar todas las regiones", description = "Devuelve todas las regiones registradas")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Regiones encontradas"),
        @ApiResponse(responseCode = "204", description = "No hay regiones registradas")
    })
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
    @Operation(summary = "Obtener región por ID", description = "Busca una región por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Región encontrada"),
        @ApiResponse(responseCode = "404", description = "Región no encontrada")
    })
    public ResponseEntity<EntityModel<Region>> getRegionById(@PathVariable Long id) {
        Region region = regionServices.findByIdRegion(id);
        if (region == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(region));
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Crear nueva región", description = "Registra una nueva región")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Región creada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Error al crear región")
    })
    public ResponseEntity<EntityModel<Region>> createRegion(@RequestBody Region region) {
        Region nueva = regionServices.save(region);
        return ResponseEntity
                .created(linkTo(methodOn(RegionControllerV2.class).getRegionById(nueva.getIdRegion())).toUri())
                .body(assembler.toModel(nueva));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Actualizar región", description = "Actualiza todos los datos de una región existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Región actualizada correctamente"),
        @ApiResponse(responseCode = "404", description = "Región no encontrada")
    })
    public ResponseEntity<EntityModel<Region>> updateRegion(@PathVariable Long id, @RequestBody Region region) {
        Region actualizada = regionServices.update(id, region);
        if (actualizada == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(actualizada));
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Modificar parcialmente región", description = "Modifica parcialmente los datos de una región")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Región modificada correctamente"),
        @ApiResponse(responseCode = "404", description = "Región no encontrada")
    })
    public ResponseEntity<EntityModel<Region>> patchRegion(@PathVariable Long id, @RequestBody Region region) {
        Region modificada = regionServices.patch(id, region);
        if (modificada == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(modificada));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Eliminar región", description = "Elimina una región por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Región eliminada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Región no encontrada")
    })
    public ResponseEntity<Void> deleteRegion(@PathVariable Long id) {
        Region region = regionServices.findByIdRegion(id);
        if (region == null) {
            return ResponseEntity.notFound().build();
        }
        regionServices.delete(id);
        return ResponseEntity.noContent().build();
    }
}
