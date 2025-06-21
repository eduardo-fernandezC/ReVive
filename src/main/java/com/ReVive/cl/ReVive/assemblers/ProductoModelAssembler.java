package com.ReVive.cl.ReVive.assemblers;

import com.ReVive.cl.ReVive.controller.ProductoControllerV2;
import com.ReVive.cl.ReVive.model.Producto;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ProductoModelAssembler implements RepresentationModelAssembler<Producto, EntityModel<Producto>> {

    @SuppressWarnings("null")
    @Override
    public EntityModel<Producto> toModel(Producto producto) {
        return EntityModel.of(producto,
            linkTo(methodOn(ProductoControllerV2.class).getProductoById(producto.getIdProducto())).withSelfRel(),
            linkTo(methodOn(ProductoControllerV2.class).getAllProductos()).withRel("productos"),
            linkTo(methodOn(ProductoControllerV2.class).updateProducto(producto.getIdProducto(), producto)).withRel("actualizar"),
            linkTo(methodOn(ProductoControllerV2.class).deleteProducto(producto.getIdProducto())).withRel("eliminar"),
            linkTo(methodOn(ProductoControllerV2.class).patchProducto(producto.getIdProducto(), producto)).withRel("actualizar-parcial")
        );
    }
}
