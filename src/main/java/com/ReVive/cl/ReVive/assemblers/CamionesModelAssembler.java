package com.ReVive.cl.ReVive.assemblers;

import com.ReVive.cl.ReVive.model.Camiones;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import com.ReVive.cl.ReVive.controller.CamionesControllerV2;

@Component
public class CamionesModelAssembler implements RepresentationModelAssembler<Camiones, EntityModel<Camiones>> { 

    @SuppressWarnings("null")
    @Override
    public EntityModel<Camiones> toModel(Camiones camion) {
        return EntityModel.of(camion, 
            linkTo(methodOn(CamionesControllerV2.class).getCamionById(camion.getIdCamion())).withSelfRel(), 
            linkTo(methodOn(CamionesControllerV2.class).getAllCamiones()).withRel("camiones"), 
            linkTo(methodOn(CamionesControllerV2.class).updateCamion(camion.getIdCamion(), camion)).withRel("actualizar"), 
            linkTo(methodOn(CamionesControllerV2.class).deleteCamion(camion.getIdCamion())).withRel("eliminar"), 
            linkTo(methodOn(CamionesControllerV2.class).patchCamion(camion.getIdCamion(), camion)).withRel("actualizar-parcial") 
        );
    }
}
