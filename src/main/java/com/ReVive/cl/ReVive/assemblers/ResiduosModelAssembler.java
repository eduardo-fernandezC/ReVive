package com.ReVive.cl.ReVive.assemblers;

import com.ReVive.cl.ReVive.controller.ResiduosControllerV2;
import com.ReVive.cl.ReVive.model.Residuos;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ResiduosModelAssembler implements RepresentationModelAssembler<Residuos, EntityModel<Residuos>> {

    @SuppressWarnings("null")
    @Override
    public EntityModel<Residuos> toModel(Residuos residuos) {
        return EntityModel.of(residuos,
                linkTo(methodOn(ResiduosControllerV2.class).getResiduosById(residuos.getIdResiduos())).withSelfRel(),
                linkTo(methodOn(ResiduosControllerV2.class).getAllResiduos()).withRel("residuos"),
                linkTo(methodOn(ResiduosControllerV2.class).updateResiduos(residuos.getIdResiduos(), residuos)).withRel("actualizar"),
                linkTo(methodOn(ResiduosControllerV2.class).patchResiduos(residuos.getIdResiduos(), residuos)).withRel("actualizar-parcial"),
                linkTo(methodOn(ResiduosControllerV2.class).deleteResiduos(residuos.getIdResiduos())).withRel("eliminar")
        );
    }
}
