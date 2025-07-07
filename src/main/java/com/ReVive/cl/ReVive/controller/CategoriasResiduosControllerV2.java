package com.ReVive.cl.ReVive.controller;

import com.ReVive.cl.ReVive.assemblers.CategoriasResiduosModelAssembler;
import com.ReVive.cl.ReVive.model.CategoriasResiduos;
import com.ReVive.cl.ReVive.service.CategoriasResiduosServices;
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
@RequestMapping("/api/v2/categoriasResiduos")
@Tag(name = "Categorías de Residuos V2", description = "Operaciones HATEOAS para categorías de residuos")
public class CategoriasResiduosControllerV2 {

    @Autowired
    private CategoriasResiduosServices categoriasResiduosServices;

    @Autowired
    private CategoriasResiduosModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Listar todas las categorías de residuos", description = "Obtiene una lista de todas las categorías de residuos con enlaces HATEOAS")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Categorías encontradas"),
        @ApiResponse(responseCode = "204", description = "No hay categorías disponibles")
    })
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
    @Operation(summary = "Buscar categoría de residuo por ID", description = "Obtiene una categoría de residuo por su ID con enlaces HATEOAS")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Categoría encontrada"),
        @ApiResponse(responseCode = "404", description = "Categoría no encontrada")
    })
    public ResponseEntity<EntityModel<CategoriasResiduos>> getCategoriaById(@PathVariable Long id) {
        CategoriasResiduos categoria = categoriasResiduosServices.findByIdCatesResiduos(id);
        if (categoria == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(categoria));
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Crear nueva categoría de residuo", description = "Guarda una nueva categoría de residuo y devuelve su representación con enlaces HATEOAS")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Categoría creada correctamente"),
        @ApiResponse(responseCode = "400", description = "Error al crear la categoría")
    })
    public ResponseEntity<EntityModel<CategoriasResiduos>> createCategoria(@RequestBody CategoriasResiduos categoria) {
        CategoriasResiduos nueva = categoriasResiduosServices.save(categoria);
        return ResponseEntity
                .created(linkTo(methodOn(CategoriasResiduosControllerV2.class).getCategoriaById(nueva.getIdCatesResiduos())).toUri())
                .body(assembler.toModel(nueva));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Actualizar categoría de residuo", description = "Actualiza una categoría de residuo por ID con enlaces HATEOAS")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Categoría actualizada correctamente"),
        @ApiResponse(responseCode = "404", description = "Categoría no encontrada")
    })
    public ResponseEntity<EntityModel<CategoriasResiduos>> updateCategoria(@PathVariable Long id, @RequestBody CategoriasResiduos categoria) {
        categoria.setIdCatesResiduos(id);
        CategoriasResiduos actualizada = categoriasResiduosServices.update(id, categoria);
        if (actualizada == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(actualizada));
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Modificar parcialmente una categoría de residuo", description = "Modifica parcialmente una categoría por ID con enlaces HATEOAS")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Categoría modificada correctamente"),
        @ApiResponse(responseCode = "404", description = "Categoría no encontrada")
    })
    public ResponseEntity<EntityModel<CategoriasResiduos>> patchCategoria(@PathVariable Long id, @RequestBody CategoriasResiduos categoria) {
        CategoriasResiduos actualizada = categoriasResiduosServices.patch(id, categoria);
        if (actualizada == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(actualizada));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Eliminar categoría de residuo", description = "Elimina una categoría de residuo por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Categoría eliminada correctamente"),
        @ApiResponse(responseCode = "404", description = "Categoría no encontrada")
    })
    public ResponseEntity<Void> deleteCategoria(@PathVariable Long id) {
        CategoriasResiduos categoria = categoriasResiduosServices.findByIdCatesResiduos(id);
        if (categoria == null) {
            return ResponseEntity.notFound().build();
        }
        categoriasResiduosServices.delete(id);
        return ResponseEntity.noContent().build();
    }
}
