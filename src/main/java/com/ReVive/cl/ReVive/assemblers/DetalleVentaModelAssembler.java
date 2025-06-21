package com.ReVive.cl.ReVive.assemblers;

import com.ReVive.cl.ReVive.controller.DetalleVentaControllerV2;
import com.ReVive.cl.ReVive.model.DetalleVenta;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class DetalleVentaModelAssembler implements RepresentationModelAssembler<DetalleVenta, EntityModel<DetalleVenta>> {

    @SuppressWarnings("null")
    @Override
    public EntityModel<DetalleVenta> toModel(DetalleVenta detalleVenta) {
        return EntityModel.of(detalleVenta,
            linkTo(methodOn(DetalleVentaControllerV2.class).getDetalleById(detalleVenta.getIdDetalleVenta())).withSelfRel(),
            linkTo(methodOn(DetalleVentaControllerV2.class).getAllDetalles()).withRel("detalleVentas"),
            linkTo(methodOn(DetalleVentaControllerV2.class).updateDetalle(detalleVenta.getIdDetalleVenta(), detalleVenta)).withRel("actualizar"),
            linkTo(methodOn(DetalleVentaControllerV2.class).deleteDetalle(detalleVenta.getIdDetalleVenta())).withRel("eliminar"),
            linkTo(methodOn(DetalleVentaControllerV2.class).patchDetalle(detalleVenta.getIdDetalleVenta(), detalleVenta)).withRel("actualizar-parcial")
        );
    }
}
