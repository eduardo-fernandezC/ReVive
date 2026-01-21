/**
 * Ensamblador de modelos para la entidad Camiones.
 * Esta clase se utiliza para convertir objetos Camiones en modelos de entidad HATEOAS,
 * agregando enlaces hipermedia para operaciones RESTful relacionadas con camiones.
 * Implementa RepresentationModelAssembler para integrar con Spring HATEOAS.
 */
package com.ReVive.cl.ReVive.assemblers;

import com.ReVive.cl.ReVive.model.Camiones; // Importa la entidad Camiones que representa un camión en el sistema
import org.springframework.hateoas.EntityModel; // Importa EntityModel para envolver entidades con enlaces
import org.springframework.hateoas.server.RepresentationModelAssembler; // Importa la interfaz para ensambladores de modelos
import org.springframework.stereotype.Component; // Anotación para marcar esta clase como un componente de Spring
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo; // Importa estático para construir enlaces
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn; // Importa estático para referenciar métodos de controladores
import com.ReVive.cl.ReVive.controller.CamionesControllerV2; // Importa el controlador V2 para camiones, usado para generar enlaces HATEOAS

@Component // Marca esta clase como un bean gestionado por Spring, permitiendo su inyección automática
public class CamionesModelAssembler implements RepresentationModelAssembler<Camiones, EntityModel<Camiones>> {

    /**
     * Convierte una entidad Camiones en un EntityModel con enlaces HATEOAS.
     * Este método agrega enlaces a operaciones relacionadas como obtener, actualizar, parchear y eliminar el camión,
     * facilitando la navegación RESTful en la API.
     *
     * @param camion La entidad Camiones a convertir
     * @return EntityModel envuelto con enlaces para operaciones relacionadas
     */
    @SuppressWarnings("null") // Suprime advertencias de nulidad, ya que Spring HATEOAS maneja los valores
    @Override
    public EntityModel<Camiones> toModel(Camiones camion) {
        return EntityModel.of(camion, // Crea un EntityModel con el camión y los siguientes enlaces
            linkTo(methodOn(CamionesControllerV2.class).getCamionById(camion.getIdCamion())).withSelfRel(), // Enlace a sí mismo (self)
            linkTo(methodOn(CamionesControllerV2.class).getAllCamiones()).withRel("camiones"), // Enlace a la lista de todos los camiones
            linkTo(methodOn(CamionesControllerV2.class).updateCamion(camion.getIdCamion(), camion)).withRel("actualizar"), // Enlace para actualizar completamente el camión
            linkTo(methodOn(CamionesControllerV2.class).deleteCamion(camion.getIdCamion())).withRel("eliminar"), // Enlace para eliminar el camión
            linkTo(methodOn(CamionesControllerV2.class).patchCamion(camion.getIdCamion(), camion)).withRel("actualizar-parcial") // Enlace para actualización parcial (patch)
        );
    }
}
