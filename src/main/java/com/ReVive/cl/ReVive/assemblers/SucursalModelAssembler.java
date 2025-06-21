package com.ReVive.cl.ReVive.assemblers;

import com.ReVive.cl.ReVive.controller.SucursalControllerV2;
import com.ReVive.cl.ReVive.model.Sucursal;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class SucursalModelAssembler implements RepresentationModelAssembler<Sucursal, EntityModel<Sucursal>> {

    @SuppressWarnings("null")
    @Override
    public EntityModel<Sucursal> toModel(Sucursal sucursal) {
        return EntityModel.of(sucursal,
                linkTo(methodOn(SucursalControllerV2.class).getSucursalById(sucursal.getIdSucursal())).withSelfRel(),
                linkTo(methodOn(SucursalControllerV2.class).getAllSucursales()).withRel("sucursales"),
                linkTo(methodOn(SucursalControllerV2.class).updateSucursal(sucursal.getIdSucursal(), sucursal)).withRel("actualizar"),
                linkTo(methodOn(SucursalControllerV2.class).patchSucursal(sucursal.getIdSucursal(), sucursal)).withRel("actualizar-parcial"),
                linkTo(methodOn(SucursalControllerV2.class).deleteSucursal(sucursal.getIdSucursal())).withRel("eliminar")
        );
    }
}
