package com.ReVive.cl.ReVive.controller;

import com.ReVive.cl.ReVive.assemblers.SucursalModelAssembler;
import com.ReVive.cl.ReVive.model.Sucursal;
import com.ReVive.cl.ReVive.service.SucursalServices;
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
@RequestMapping("/api/v2/sucursales")
@Tag(name = "Sucursales V2", description = "Operaciones para gestión de sucursales con HATEOAS")
public class SucursalControllerV2 {

    @Autowired
    private SucursalServices sucursalServices;

    @Autowired
    private SucursalModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Listar todas las sucursales", description = "Devuelve la lista completa de sucursales")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Sucursales encontradas"),
        @ApiResponse(responseCode = "204", description = "No hay sucursales registradas")
    })
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
    @Operation(summary = "Obtener sucursal por ID", description = "Busca una sucursal usando su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Sucursal encontrada"),
        @ApiResponse(responseCode = "404", description = "Sucursal no encontrada")
    })
    public ResponseEntity<EntityModel<Sucursal>> getSucursalById(
            @Parameter(description = "ID de la sucursal a buscar") @PathVariable Long id) {
        Sucursal sucursal = sucursalServices.findByIdSucursal(id);
        if (sucursal == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(sucursal));
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Crear una nueva sucursal", description = "Registra una sucursal nueva en el sistema")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Sucursal creada correctamente"),
        @ApiResponse(responseCode = "400", description = "Error en los datos enviados")
    })
    public ResponseEntity<EntityModel<Sucursal>> createSucursal(@RequestBody Sucursal sucursal) {
        Sucursal newSucursal = sucursalServices.save(sucursal);
        return ResponseEntity
                .created(linkTo(methodOn(SucursalControllerV2.class).getSucursalById(newSucursal.getIdSucursal())).toUri())
                .body(assembler.toModel(newSucursal));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Actualizar una sucursal", description = "Actualiza completamente una sucursal existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Sucursal actualizada correctamente"),
        @ApiResponse(responseCode = "404", description = "Sucursal no encontrada")
    })
    public ResponseEntity<EntityModel<Sucursal>> updateSucursal(
            @Parameter(description = "ID de la sucursal a actualizar") @PathVariable Long id,
            @RequestBody Sucursal sucursal) {
        Sucursal updated = sucursalServices.update(id, sucursal);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(updated));
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Modificar parcialmente una sucursal", description = "Actualiza parcialmente los datos de una sucursal")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Sucursal modificada correctamente"),
        @ApiResponse(responseCode = "404", description = "Sucursal no encontrada")
    })
    public ResponseEntity<EntityModel<Sucursal>> patchSucursal(
            @Parameter(description = "ID de la sucursal a modificar") @PathVariable Long id,
            @RequestBody Sucursal sucursal) {
        Sucursal patched = sucursalServices.patch(id, sucursal);
        if (patched == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(patched));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Eliminar una sucursal", description = "Elimina una sucursal por su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Sucursal eliminada correctamente"),
        @ApiResponse(responseCode = "404", description = "Sucursal no encontrada")
    })
    public ResponseEntity<Void> deleteSucursal(
            @Parameter(description = "ID de la sucursal a eliminar") @PathVariable Long id) {
        Sucursal sucursal = sucursalServices.findByIdSucursal(id);
        if (sucursal == null) {
            return ResponseEntity.notFound().build();
        }
        sucursalServices.delete(id);
        return ResponseEntity.noContent().build();
    }
}
