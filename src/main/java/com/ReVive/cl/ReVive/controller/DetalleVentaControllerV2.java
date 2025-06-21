package com.ReVive.cl.ReVive.controller;

import com.ReVive.cl.ReVive.assemblers.DetalleVentaModelAssembler;
import com.ReVive.cl.ReVive.model.DetalleVenta;
import com.ReVive.cl.ReVive.service.DetalleVentaServices;
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
@RequestMapping("/api/v2/detalleVentas")
public class DetalleVentaControllerV2 {

    @Autowired
    private DetalleVentaServices detalleVentaServices;

    @Autowired
    private DetalleVentaModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<DetalleVenta>>> getAllDetalles() {
        List<EntityModel<DetalleVenta>> detalles = detalleVentaServices.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        if (detalles.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(
                CollectionModel.of(detalles,
                linkTo(methodOn(DetalleVentaControllerV2.class).getAllDetalles()).withSelfRel())
        );
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<DetalleVenta>> getDetalleById(@PathVariable Long id) {
        DetalleVenta detalle = detalleVentaServices.findById(id);
        if (detalle == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(detalle));
    }

    @GetMapping(value = "/buscar-detalle", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<DetalleVenta>>> buscarDetallePorUsuarioYCategoria(
            @RequestParam String usuario,
            @RequestParam String categoria) {
        List<DetalleVenta> detalles = detalleVentaServices.buscarDetallePorUsuarioYCategoria(usuario, categoria);

        if (detalles.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<EntityModel<DetalleVenta>> detallesModel = detalles.stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(
                detallesModel,
                linkTo(methodOn(DetalleVentaControllerV2.class)
                        .buscarDetallePorUsuarioYCategoria(usuario, categoria)).withSelfRel()
        ));
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<DetalleVenta>> createDetalle(@RequestBody DetalleVenta detalleVenta) {
        DetalleVenta newDetalle = detalleVentaServices.save(detalleVenta);
        return ResponseEntity
                .created(linkTo(methodOn(DetalleVentaControllerV2.class).getDetalleById(newDetalle.getIdDetalleVenta())).toUri())
                .body(assembler.toModel(newDetalle));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<DetalleVenta>> updateDetalle(@PathVariable Long id, @RequestBody DetalleVenta detalleVenta) {
        DetalleVenta updated = detalleVentaServices.update(id, detalleVenta);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(updated));
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<DetalleVenta>> patchDetalle(@PathVariable Long id, @RequestBody DetalleVenta detalleVenta) {
        DetalleVenta patched = detalleVentaServices.patch(id, detalleVenta);
        if (patched == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(patched));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Void> deleteDetalle(@PathVariable Long id) {
        DetalleVenta detalle = detalleVentaServices.findById(id);
        if (detalle == null) {
            return ResponseEntity.notFound().build();
        }
        detalleVentaServices.delete(id);
        return ResponseEntity.noContent().build();
    }
}
