package com.ReVive.cl.ReVive.assemblers;

import com.ReVive.cl.ReVive.controller.VentasControllerV2;
import com.ReVive.cl.ReVive.model.Ventas;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class VentasModelAssembler implements RepresentationModelAssembler<Ventas, EntityModel<Ventas>> {

    @SuppressWarnings("null")
    @Override
    public EntityModel<Ventas> toModel(Ventas venta) {
        return EntityModel.of(venta,
                linkTo(methodOn(VentasControllerV2.class).getVentaById(venta.getIdVenta())).withSelfRel(),
                linkTo(methodOn(VentasControllerV2.class).getAllVentas()).withRel("ventas"),
                linkTo(methodOn(VentasControllerV2.class).updateVenta(venta.getIdVenta(), venta)).withRel("actualizar"),
                linkTo(methodOn(VentasControllerV2.class).patchVenta(venta.getIdVenta(), venta)).withRel("actualizar-parcial"),
                linkTo(methodOn(VentasControllerV2.class).deleteVenta(venta.getIdVenta())).withRel("eliminar")
        );
    }
}
