/**
 * Ensamblador de modelos para la entidad DetalleVenta.
 * Esta clase se utiliza para convertir objetos DetalleVenta en modelos de entidad HATEOAS,
 * agregando enlaces hipermedia para operaciones RESTful relacionadas con detalles de venta.
 * Implementa RepresentationModelAssembler para integrar con Spring HATEOAS.
 */
package com.ReVive.cl.ReVive.assemblers;

import com.ReVive.cl.ReVive.controller.DetalleVentaControllerV2; // Importa el controlador V2 para detalles de venta, usado para generar enlaces HATEOAS
import com.ReVive.cl.ReVive.model.DetalleVenta; // Importa la entidad DetalleVenta que representa un detalle de venta en el sistema
import org.springframework.hateoas.EntityModel; // Importa EntityModel para envolver entidades con enlaces
import org.springframework.hateoas.server.RepresentationModelAssembler; // Importa la interfaz para ensambladores de modelos
import org.springframework.stereotype.Component; // Anotación para marcar esta clase como un componente de Spring
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo; // Importa estático para construir enlaces
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn; // Importa estático para referenciar métodos de controladores

@Component // Marca esta clase como un bean gestionado por Spring, permitiendo su inyección automática
public class DetalleVentaModelAssembler implements RepresentationModelAssembler<DetalleVenta, EntityModel<DetalleVenta>> {

    /**
     * Convierte una entidad DetalleVenta en un EntityModel con enlaces HATEOAS.
     * Este método agrega enlaces a operaciones relacionadas como obtener, actualizar, parchear y eliminar el detalle de venta,
     * facilitando la navegación RESTful en la API.
     *
     * @param detalleVenta La entidad DetalleVenta a convertir
     * @return EntityModel envuelto con enlaces para operaciones relacionadas
     */
    @SuppressWarnings("null") // Suprime advertencias de nulidad, ya que Spring HATEOAS maneja los valores
    @Override
    public EntityModel<DetalleVenta> toModel(DetalleVenta detalleVenta) {
        return EntityModel.of(detalleVenta, // Crea un EntityModel con el detalle de venta y los siguientes enlaces
            linkTo(methodOn(DetalleVentaControllerV2.class).getDetalleById(detalleVenta.getIdDetalleVenta())).withSelfRel(), // Enlace a sí mismo (self)
            linkTo(methodOn(DetalleVentaControllerV2.class).getAllDetalles()).withRel("detalleVentas"), // Enlace a la lista de todos los detalles de venta
            linkTo(methodOn(DetalleVentaControllerV2.class).updateDetalle(detalleVenta.getIdDetalleVenta(), detalleVenta)).withRel("actualizar"), // Enlace para actualizar completamente el detalle de venta
            linkTo(methodOn(DetalleVentaControllerV2.class).deleteDetalle(detalleVenta.getIdDetalleVenta())).withRel("eliminar"), // Enlace para eliminar el detalle de venta
            linkTo(methodOn(DetalleVentaControllerV2.class).patchDetalle(detalleVenta.getIdDetalleVenta(), detalleVenta)).withRel("actualizar-parcial") // Enlace para actualización parcial (patch)
        );
    }
}
