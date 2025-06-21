package com.ReVive.cl.ReVive.assemblers;

import com.ReVive.cl.ReVive.controller.ComunaControllerV2;
import com.ReVive.cl.ReVive.model.Comuna;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ComunaModelAssembler implements RepresentationModelAssembler<Comuna, EntityModel<Comuna>> {

    @SuppressWarnings("null")
    @Override
    public EntityModel<Comuna> toModel(Comuna comuna) {
        return EntityModel.of(comuna,
            linkTo(methodOn(ComunaControllerV2.class).getComunaById(comuna.getIdComuna())).withSelfRel(),
            linkTo(methodOn(ComunaControllerV2.class).getAllComunas()).withRel("comunas"),
            linkTo(methodOn(ComunaControllerV2.class).updateComuna(comuna.getIdComuna(), comuna)).withRel("actualizar"),
            linkTo(methodOn(ComunaControllerV2.class).patchComuna(comuna.getIdComuna(), comuna)).withRel("actualizar-parcial"),
            linkTo(methodOn(ComunaControllerV2.class).deleteComuna(comuna.getIdComuna())).withRel("eliminar")
        );
    }
}
