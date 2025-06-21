package com.ReVive.cl.ReVive.assemblers;

import com.ReVive.cl.ReVive.controller.UsuariosControllerV2;
import com.ReVive.cl.ReVive.model.Usuarios;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UsuariosModelAssembler implements RepresentationModelAssembler<Usuarios, EntityModel<Usuarios>> {

    @SuppressWarnings("null")
    @Override
    public EntityModel<Usuarios> toModel(Usuarios usuario) {
        return EntityModel.of(usuario,
                linkTo(methodOn(UsuariosControllerV2.class).getUsuarioById(usuario.getIdUsuario())).withSelfRel(),
                linkTo(methodOn(UsuariosControllerV2.class).getAllUsuarios()).withRel("usuarios"),
                linkTo(methodOn(UsuariosControllerV2.class).updateUsuario(usuario.getIdUsuario(), usuario)).withRel("actualizar"),
                linkTo(methodOn(UsuariosControllerV2.class).patchUsuario(usuario.getIdUsuario(), usuario)).withRel("actualizar-parcial"),
                linkTo(methodOn(UsuariosControllerV2.class).deleteUsuario(usuario.getIdUsuario())).withRel("eliminar")
        );
    }
}
