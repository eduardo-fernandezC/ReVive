package com.ReVive.cl.ReVive.controller;

import com.ReVive.cl.ReVive.assemblers.ProductoModelAssembler;
import com.ReVive.cl.ReVive.model.Producto;
import com.ReVive.cl.ReVive.service.ProductoServices;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v2/productos")
@Tag(name = "Producto V2", description = "Operaciones HATEOAS relacionadas con productos")
public class ProductoControllerV2 {

    @Autowired
    private ProductoServices productoServices;

    @Autowired
    private ProductoModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Listar todos los productos", description = "Devuelve todos los productos registrados")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Productos encontrados"),
        @ApiResponse(responseCode = "204", description = "No hay productos registrados")
    })
    public ResponseEntity<CollectionModel<EntityModel<Producto>>> getAllProductos() {
        List<EntityModel<Producto>> productos = productoServices.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        if (productos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(
                CollectionModel.of(productos,
                        linkTo(methodOn(ProductoControllerV2.class).getAllProductos()).withSelfRel())
        );
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtener producto por ID", description = "Busca un producto por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Producto encontrado"),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    public ResponseEntity<EntityModel<Producto>> getProductoById(@PathVariable Long id) {
        Producto producto = productoServices.findById(id);
        if (producto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(producto));
    }

    @GetMapping(value = "/nombre/{nombre}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Buscar producto por nombre", description = "Obtiene un producto por su nombre exacto")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Producto encontrado"),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    public ResponseEntity<EntityModel<Producto>> getProductoByNombre(@PathVariable String nombre) {
        Producto producto = productoServices.findByNombreProducto(nombre);
        if (producto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(producto));
    }

    @GetMapping(value = "/nombre-categoria", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Buscar productos por nombre y categoría", description = "Busca productos por nombre y categoría")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Productos encontrados"),
        @ApiResponse(responseCode = "204", description = "No se encontraron productos")
    })
    public ResponseEntity<CollectionModel<EntityModel<Producto>>> getProductosByNombreYCategoria(
            @RequestParam String nombre,
            @RequestParam String categoria) {
        List<Producto> productos = productoServices.findByNombreProductoAndCategoria(nombre, categoria);

        if (productos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<EntityModel<Producto>> productosModel = productos.stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(
                productosModel,
                linkTo(methodOn(ProductoControllerV2.class).getProductosByNombreYCategoria(nombre, categoria)).withSelfRel()
        ));
    }

    @GetMapping(value = "/categoria-sucursal", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Buscar productos por categoría y sucursal", description = "Filtra productos según su categoría y sucursal")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Productos encontrados"),
        @ApiResponse(responseCode = "204", description = "No se encontraron productos")
    })
    public ResponseEntity<CollectionModel<EntityModel<Producto>>> getProductosByCategoriaYSucursal(
            @RequestParam String categoria,
            @RequestParam String sucursal) {
        List<Producto> productos = productoServices.findProductosByCategoriaAndSucursal(categoria, sucursal);

        if (productos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<EntityModel<Producto>> productosModel = productos.stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(
                productosModel,
                linkTo(methodOn(ProductoControllerV2.class).getProductosByCategoriaYSucursal(categoria, sucursal)).withSelfRel()
        ));
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Crear producto", description = "Registra un nuevo producto en el sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Producto creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Error al crear producto")
    })
    public ResponseEntity<EntityModel<Producto>> createProducto(@RequestBody Producto producto) {
        Producto newProducto = productoServices.save(producto);
        return ResponseEntity
                .created(linkTo(methodOn(ProductoControllerV2.class).getProductoById(newProducto.getIdProducto())).toUri())
                .body(assembler.toModel(newProducto));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Actualizar producto", description = "Actualiza todos los datos de un producto")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Producto actualizado"),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    public ResponseEntity<EntityModel<Producto>> updateProducto(@PathVariable Long id, @RequestBody Producto producto) {
        Producto updated = productoServices.update(id, producto);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(updated));
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Modificar parcialmente un producto", description = "Actualiza parcialmente los datos de un producto")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Producto modificado"),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    public ResponseEntity<EntityModel<Producto>> patchProducto(@PathVariable Long id, @RequestBody Producto producto) {
        Producto patched = productoServices.patch(id, producto);
        if (patched == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(patched));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Eliminar producto", description = "Elimina un producto por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Producto eliminado correctamente"),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    public ResponseEntity<Void> deleteProducto(@PathVariable Long id) {
        Producto producto = productoServices.findById(id);
        if (producto == null) {
            return ResponseEntity.notFound().build();
        }
        productoServices.delete(id);
        return ResponseEntity.noContent().build();
    }
}
