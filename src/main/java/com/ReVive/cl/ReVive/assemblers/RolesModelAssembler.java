package com.ReVive.cl.ReVive.assemblers;

import com.ReVive.cl.ReVive.controller.RolesControllerV2;
import com.ReVive.cl.ReVive.model.Roles;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class RolesModelAssembler implements RepresentationModelAssembler<Roles, EntityModel<Roles>> {

    @SuppressWarnings("null")
    @Override
    public EntityModel<Roles> toModel(Roles roles) {
        return EntityModel.of(roles,
                linkTo(methodOn(RolesControllerV2.class).getRolById(roles.getIdRoles())).withSelfRel(),
                linkTo(methodOn(RolesControllerV2.class).getAllRoles()).withRel("roles"),
                linkTo(methodOn(RolesControllerV2.class).updateRol(roles.getIdRoles(), roles)).withRel("actualizar"),
                linkTo(methodOn(RolesControllerV2.class).patchRol(roles.getIdRoles(), roles)).withRel("actualizar-parcial"),
                linkTo(methodOn(RolesControllerV2.class).deleteRol(roles.getIdRoles())).withRel("eliminar")
        );
    }
}
