package com.ReVive.cl.ReVive.controller;

import com.ReVive.cl.ReVive.assemblers.VentasModelAssembler;
import com.ReVive.cl.ReVive.model.Ventas;
import com.ReVive.cl.ReVive.service.VentasServices;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v2/ventas")
@Tag(name = "Ventas", description = "API para gestión de ventas")
public class VentasControllerV2 {

    @Autowired
    private VentasServices ventasServices;

    @Autowired
    private VentasModelAssembler assembler;

    @Operation(summary = "Obtener todas las ventas", description = "Devuelve una colección de todas las ventas registradas")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de ventas encontrada"),
        @ApiResponse(responseCode = "204", description = "No hay ventas registradas")
    })
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<Ventas>>> getAllVentas() {
        List<EntityModel<Ventas>> ventas = ventasServices.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        if (ventas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(
                CollectionModel.of(ventas,
                        linkTo(methodOn(VentasControllerV2.class).getAllVentas()).withSelfRel())
        );
    }

    @Operation(summary = "Obtener venta por ID", description = "Obtiene una venta específica por su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Venta encontrada"),
        @ApiResponse(responseCode = "404", description = "Venta no encontrada")
    })
    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Ventas>> getVentaById(
            @Parameter(description = "ID de la venta", required = true) @PathVariable Long id) {
        Ventas venta = ventasServices.findByIdVentas(id);
        if (venta == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(venta));
    }

    @Operation(summary = "Obtener ventas por fecha", description = "Busca ventas realizadas en una fecha específica (formato yyyy-MM-dd)")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Ventas encontradas para la fecha indicada"),
        @ApiResponse(responseCode = "204", description = "No hay ventas para la fecha indicada"),
        @ApiResponse(responseCode = "400", description = "Formato de fecha inválido")
    })
    @GetMapping(value = "/fecha/{fecha}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<Ventas>>> getVentasByFecha(
            @Parameter(description = "Fecha de venta en formato yyyy-MM-dd", example = "2025-07-07")
            @PathVariable String fecha) {
        try {
            Date fechaVenta = Date.valueOf(fecha);
            List<EntityModel<Ventas>> ventas = ventasServices.findByfechaVentas(fechaVenta).stream()
                    .map(assembler::toModel)
                    .collect(Collectors.toList());

            if (ventas.isEmpty()) {
                return ResponseEntity.noContent().build();
            }

            return ResponseEntity.ok(
                    CollectionModel.of(ventas,
                            linkTo(methodOn(VentasControllerV2.class).getVentasByFecha(fecha)).withSelfRel())
            );
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Obtener ventas por usuario y sucursal", description = "Busca ventas filtrando por nombre de usuario y sucursal")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Ventas encontradas"),
        @ApiResponse(responseCode = "204", description = "No hay ventas para el usuario y sucursal indicados")
    })
    @GetMapping(value = "/usuario-sucursal", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<Ventas>>> getVentasByUsuarioYSucursal(
            @Parameter(description = "Nombre del usuario", required = true) @RequestParam String usuario,
            @Parameter(description = "Nombre de la sucursal", required = true) @RequestParam String sucursal) {

        List<EntityModel<Ventas>> ventas = ventasServices.findByUsuarioAndSucursal(usuario, sucursal).stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        if (ventas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(
                CollectionModel.of(ventas,
                        linkTo(methodOn(VentasControllerV2.class).getVentasByUsuarioYSucursal(usuario, sucursal)).withSelfRel())
        );
    }

    @Operation(summary = "Obtener ventas por sucursal y categoría", description = "Busca ventas filtrando por sucursal y categoría")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Ventas encontradas"),
        @ApiResponse(responseCode = "204", description = "No hay ventas para la sucursal y categoría indicadas")
    })
    @GetMapping(value = "/sucursal-categoria/{sucursal}/{categoria}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<Ventas>>> getVentasBySucursalYCategoria(
            @Parameter(description = "Nombre de la sucursal", required = true) @PathVariable String sucursal,
            @Parameter(description = "Nombre de la categoría", required = true) @PathVariable String categoria) {

        List<EntityModel<Ventas>> ventas = ventasServices.buscarVentasPorSucursalYCategoria(sucursal, categoria).stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        if (ventas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(
                CollectionModel.of(ventas,
                        linkTo(methodOn(VentasControllerV2.class).getVentasBySucursalYCategoria(sucursal, categoria)).withSelfRel())
        );
    }

    @Operation(summary = "Crear una nueva venta", description = "Registra una nueva venta en el sistema")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Venta creada exitosamente")
    })
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Ventas>> createVenta(
            @Parameter(description = "Datos de la nueva venta", required = true) @RequestBody Ventas venta) {
        Ventas nuevaVenta = ventasServices.save(venta);
        return ResponseEntity
                .created(linkTo(methodOn(VentasControllerV2.class).getVentaById(nuevaVenta.getIdVenta())).toUri())
                .body(assembler.toModel(nuevaVenta));
    }

    @Operation(summary = "Actualizar una venta", description = "Actualiza todos los datos de una venta existente por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Venta actualizada correctamente"),
        @ApiResponse(responseCode = "404", description = "Venta no encontrada")
    })
    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Ventas>> updateVenta(
            @Parameter(description = "ID de la venta a actualizar", required = true) @PathVariable Long id,
            @Parameter(description = "Datos actualizados de la venta", required = true) @RequestBody Ventas venta) {
        Ventas updated = ventasServices.update(id, venta);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(updated));
    }

    @Operation(summary = "Actualizar parcialmente una venta", description = "Actualiza parcialmente una venta existente por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Venta actualizada parcialmente correctamente"),
        @ApiResponse(responseCode = "404", description = "Venta no encontrada")
    })
    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Ventas>> patchVenta(
            @Parameter(description = "ID de la venta a actualizar parcialmente", required = true) @PathVariable Long id,
            @Parameter(description = "Datos parciales para actualizar la venta", required = true) @RequestBody Ventas venta) {
        Ventas patched = ventasServices.patch(id, venta);
        if (patched == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(patched));
    }

    @Operation(summary = "Eliminar una venta", description = "Elimina una venta existente por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Venta eliminada correctamente"),
        @ApiResponse(responseCode = "404", description = "Venta no encontrada")
    })
    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Void> deleteVenta(
            @Parameter(description = "ID de la venta a eliminar", required = true) @PathVariable Long id) {
        Ventas venta = ventasServices.findByIdVentas(id);
        if (venta == null) {
            return ResponseEntity.notFound().build();
        }
        ventasServices.delete(id);
        return ResponseEntity.noContent().build();
    }
}
