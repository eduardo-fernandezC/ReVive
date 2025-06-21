package com.ReVive.cl.ReVive.assemblers;

import com.ReVive.cl.ReVive.model.CategoriasProducto;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import com.ReVive.cl.ReVive.controller.CategoriasProductoControllerV2;

@Component
public class CategoriasProductoModelAssembler implements RepresentationModelAssembler<CategoriasProducto, EntityModel<CategoriasProducto>> {

    @SuppressWarnings("null")
    @Override
    public EntityModel<CategoriasProducto> toModel(CategoriasProducto categoria) {
        return EntityModel.of(categoria,
                linkTo(methodOn(CategoriasProductoControllerV2.class).getCategoriaById(categoria.getIdCatesProducto())).withSelfRel(),
                linkTo(methodOn(CategoriasProductoControllerV2.class).getAllCategorias()).withRel("categorias"),
                linkTo(methodOn(CategoriasProductoControllerV2.class).updateCategoria(categoria.getIdCatesProducto(), categoria)).withRel("actualizar"),
                linkTo(methodOn(CategoriasProductoControllerV2.class).deleteCategoria(categoria.getIdCatesProducto())).withRel("eliminar"),
                linkTo(methodOn(CategoriasProductoControllerV2.class).patchCategoria(categoria.getIdCatesProducto(), categoria)).withRel("actualizar-parcial")
        );
    }
}
