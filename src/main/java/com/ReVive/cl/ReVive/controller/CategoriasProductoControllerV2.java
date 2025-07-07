package com.ReVive.cl.ReVive.controller;

import com.ReVive.cl.ReVive.assemblers.CategoriasProductoModelAssembler;
import com.ReVive.cl.ReVive.model.CategoriasProducto;
import com.ReVive.cl.ReVive.service.CategoriasProductoServices;
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
@RequestMapping("/api/v2/categoriasProducto")
@Tag(name = "Categorías de Producto V2", description = "Operaciones HATEOAS para categorías de productos")
public class CategoriasProductoControllerV2 {

    @Autowired
    private CategoriasProductoServices categoriasProductoServices;

    @Autowired
    private CategoriasProductoModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Listar todas las categorías", description = "Obtiene una lista de todas las categorías de productos con enlaces HATEOAS")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Categorías encontradas"),
        @ApiResponse(responseCode = "204", description = "No hay categorías disponibles")
    })
    public ResponseEntity<CollectionModel<EntityModel<CategoriasProducto>>> getAllCategorias() {
        List<EntityModel<CategoriasProducto>> categorias = categoriasProductoServices.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        if (categorias.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(CollectionModel.of(
                categorias,
                linkTo(methodOn(CategoriasProductoControllerV2.class).getAllCategorias()).withSelfRel()
        ));
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Buscar categoría por ID", description = "Obtiene una categoría por su ID con enlaces HATEOAS")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Categoría encontrada"),
        @ApiResponse(responseCode = "404", description = "Categoría no encontrada")
    })
    public ResponseEntity<EntityModel<CategoriasProducto>> getCategoriaById(@PathVariable Long id) {
        CategoriasProducto categoria = categoriasProductoServices.CategoriaProductoId(id);
        if (categoria == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(categoria));
    }

    @GetMapping(value = "/buscarNombre/{nombre}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Buscar categoría por nombre", description = "Obtiene una categoría por su nombre con enlaces HATEOAS")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Categoría encontrada"),
        @ApiResponse(responseCode = "404", description = "Categoría no encontrada")
    })
    public ResponseEntity<EntityModel<CategoriasProducto>> getCategoriaByNombre(@PathVariable String nombre) {
        CategoriasProducto categoria = categoriasProductoServices.findByNombre(nombre);
        if (categoria == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(categoria));
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Crear nueva categoría", description = "Guarda una nueva categoría y devuelve su representación con enlaces HATEOAS")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Categoría creada correctamente"),
        @ApiResponse(responseCode = "400", description = "Error al crear la categoría")
    })
    public ResponseEntity<EntityModel<CategoriasProducto>> createCategoria(@RequestBody CategoriasProducto categoria) {
        CategoriasProducto nueva = categoriasProductoServices.save(categoria);
        return ResponseEntity
                .created(linkTo(methodOn(CategoriasProductoControllerV2.class).getCategoriaById(nueva.getIdCatesProducto())).toUri())
                .body(assembler.toModel(nueva));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Actualizar categoría", description = "Actualiza una categoría por ID con enlaces HATEOAS")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Categoría actualizada correctamente"),
        @ApiResponse(responseCode = "404", description = "Categoría no encontrada")
    })
    public ResponseEntity<EntityModel<CategoriasProducto>> updateCategoria(@PathVariable Long id, @RequestBody CategoriasProducto categoria) {
        CategoriasProducto actualizada = categoriasProductoServices.update(id, categoria);
        if (actualizada == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(actualizada));
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Modificar parcialmente una categoría", description = "Modifica parcialmente una categoría por ID con enlaces HATEOAS")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Categoría modificada correctamente"),
        @ApiResponse(responseCode = "404", description = "Categoría no encontrada")
    })
    public ResponseEntity<EntityModel<CategoriasProducto>> patchCategoria(@PathVariable Long id, @RequestBody CategoriasProducto categoria) {
        CategoriasProducto actualizada = categoriasProductoServices.patch(id, categoria);
        if (actualizada == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(actualizada));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Eliminar categoría", description = "Elimina una categoría por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Categoría eliminada correctamente"),
        @ApiResponse(responseCode = "404", description = "Categoría no encontrada")
    })
    public ResponseEntity<Void> deleteCategoria(@PathVariable Long id) {
        CategoriasProducto categoria = categoriasProductoServices.CategoriaProductoId(id);
        if (categoria == null) {
            return ResponseEntity.notFound().build();
        }
        categoriasProductoServices.delete(id);
        return ResponseEntity.noContent().build();
    }
}
