package com.ReVive.cl.ReVive.controller;

import com.ReVive.cl.ReVive.assemblers.ComunaModelAssembler;
import com.ReVive.cl.ReVive.model.Comuna;
import com.ReVive.cl.ReVive.service.ComunaServices;
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
@RequestMapping("/api/v2/comunas")
@Tag(name = "Comunas V2", description = "Operaciones HATEOAS para comunas")
public class ComunaControllerV2 {

    @Autowired
    private ComunaServices comunaServices;

    @Autowired
    private ComunaModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Listar todas las comunas", description = "Obtiene una lista de todas las comunas con enlaces HATEOAS")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Comunas encontradas"),
        @ApiResponse(responseCode = "204", description = "No hay comunas disponibles")
    })
    public ResponseEntity<CollectionModel<EntityModel<Comuna>>> getAllComunas() {
        List<EntityModel<Comuna>> comunas = comunaServices.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        if (comunas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(CollectionModel.of(
                comunas,
                linkTo(methodOn(ComunaControllerV2.class).getAllComunas()).withSelfRel()
        ));
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Buscar comuna por ID", description = "Obtiene una comuna por su ID con enlaces HATEOAS")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Comuna encontrada"),
        @ApiResponse(responseCode = "404", description = "Comuna no encontrada")
    })
    public ResponseEntity<EntityModel<Comuna>> getComunaById(@PathVariable Long id) {
        Comuna comuna = comunaServices.findById(id);
        if (comuna == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(comuna));
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Crear nueva comuna", description = "Crea una nueva comuna y devuelve su representación con enlaces HATEOAS")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Comuna creada correctamente"),
        @ApiResponse(responseCode = "400", description = "Error al crear la comuna")
    })
    public ResponseEntity<EntityModel<Comuna>> createComuna(@RequestBody Comuna comuna) {
        Comuna newComuna = comunaServices.save(comuna);
        return ResponseEntity
                .created(linkTo(methodOn(ComunaControllerV2.class).getComunaById(newComuna.getIdComuna())).toUri())
                .body(assembler.toModel(newComuna));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Actualizar comuna", description = "Actualiza una comuna por ID con enlaces HATEOAS")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Comuna actualizada correctamente"),
        @ApiResponse(responseCode = "404", description = "Comuna no encontrada")
    })
    public ResponseEntity<EntityModel<Comuna>> updateComuna(@PathVariable Long id, @RequestBody Comuna comuna) {
        Comuna updatedComuna = comunaServices.update(id, comuna);
        if (updatedComuna == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(updatedComuna));
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Modificar parcialmente una comuna", description = "Modifica parcialmente una comuna por ID con enlaces HATEOAS")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Comuna modificada correctamente"),
        @ApiResponse(responseCode = "404", description = "Comuna no encontrada")
    })
    public ResponseEntity<EntityModel<Comuna>> patchComuna(@PathVariable Long id, @RequestBody Comuna comuna) {
        Comuna patchedComuna = comunaServices.patch(id, comuna);
        if (patchedComuna == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(patchedComuna));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Eliminar comuna", description = "Elimina una comuna por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Comuna eliminada correctamente"),
        @ApiResponse(responseCode = "404", description = "Comuna no encontrada")
    })
    public ResponseEntity<Void> deleteComuna(@PathVariable Long id) {
        Comuna comuna = comunaServices.findById(id);
        if (comuna == null) {
            return ResponseEntity.notFound().build();
        }
        comunaServices.delete(id);
        return ResponseEntity.noContent().build();
    }
}
