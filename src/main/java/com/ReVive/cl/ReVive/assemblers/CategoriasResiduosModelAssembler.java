package com.ReVive.cl.ReVive.assemblers;


import com.ReVive.cl.ReVive.controller.CategoriasResiduosControllerV2;
import com.ReVive.cl.ReVive.model.CategoriasResiduos;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@Component
public class CategoriasResiduosModelAssembler implements RepresentationModelAssembler<CategoriasResiduos, EntityModel<CategoriasResiduos>> {

    @SuppressWarnings("null")
    @Override
    public EntityModel<CategoriasResiduos> toModel(CategoriasResiduos categoria) {
        return EntityModel.of(categoria,
                linkTo(methodOn(CategoriasResiduosControllerV2.class).getCategoriaById(categoria.getIdCatesResiduos())).withSelfRel(),
                linkTo(methodOn(CategoriasResiduosControllerV2.class).getAllCategorias()).withRel("categorias"),
                linkTo(methodOn(CategoriasResiduosControllerV2.class).updateCategoria(categoria.getIdCatesResiduos(), categoria)).withRel("actualizar"),
                linkTo(methodOn(CategoriasResiduosControllerV2.class).deleteCategoria(categoria.getIdCatesResiduos())).withRel("eliminar"),
                linkTo(methodOn(CategoriasResiduosControllerV2.class).patchCategoria(categoria.getIdCatesResiduos(), categoria)).withRel("actualizar-parcial")
        );
    }
}
