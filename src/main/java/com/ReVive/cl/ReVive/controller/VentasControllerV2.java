package com.ReVive.cl.ReVive.controller;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.ReVive.cl.ReVive.assemblers.VentasModelAssembler;
import com.ReVive.cl.ReVive.model.Ventas;
import com.ReVive.cl.ReVive.service.VentasServices;
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

@RestController
@RequestMapping("/api/v2/ventas")
public class VentasControllerV2 {

    @Autowired
    private VentasServices ventasServices;

    @Autowired
    private VentasModelAssembler assembler;

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

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Ventas>> getVentaById(@PathVariable Long id) {
        Ventas venta = ventasServices.findByIdVentas(id);
        if (venta == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(venta));
    }

    @GetMapping(value = "/fecha/{fecha}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<Ventas>>> getVentasByFecha(@PathVariable String fecha) {
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

    @GetMapping(value = "/usuario-sucursal", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<Ventas>>> getVentasByUsuarioYSucursal(
            @RequestParam String usuario,
            @RequestParam String sucursal) {

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

    @GetMapping(value = "/sucursal-categoria/{sucursal}/{categoria}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<Ventas>>> getVentasBySucursalYCategoria(
            @PathVariable String sucursal,
            @PathVariable String categoria) {

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

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Ventas>> createVenta(@RequestBody Ventas venta) {
        Ventas nuevaVenta = ventasServices.save(venta);
        return ResponseEntity
                .created(linkTo(methodOn(VentasControllerV2.class).getVentaById(nuevaVenta.getIdVenta())).toUri())
                .body(assembler.toModel(nuevaVenta));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Ventas>> updateVenta(@PathVariable Long id, @RequestBody Ventas venta) {
        Ventas updated = ventasServices.update(id, venta);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(updated));
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Ventas>> patchVenta(@PathVariable Long id, @RequestBody Ventas venta) {
        Ventas patched = ventasServices.patch(id, venta);
        if (patched == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(patched));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Void> deleteVenta(@PathVariable Long id) {
        Ventas venta = ventasServices.findByIdVentas(id);
        if (venta == null) {
            return ResponseEntity.notFound().build();
        }
        ventasServices.delete(id);
        return ResponseEntity.noContent().build();
    }
}
