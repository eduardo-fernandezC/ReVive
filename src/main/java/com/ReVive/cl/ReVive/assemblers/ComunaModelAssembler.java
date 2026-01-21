/**
 * Ensamblador de modelos para la entidad Comuna.
 * Esta clase se utiliza para convertir objetos Comuna en modelos de entidad HATEOAS,
 * agregando enlaces hipermedia para operaciones RESTful relacionadas con comunas.
 * Implementa RepresentationModelAssembler para integrar con Spring HATEOAS.
 */
package com.ReVive.cl.ReVive.assemblers;

import com.ReVive.cl.ReVive.controller.ComunaControllerV2; // Importa el controlador V2 para comunas, usado para generar enlaces HATEOAS
import com.ReVive.cl.ReVive.model.Comuna; // Importa la entidad Comuna que representa una comuna en el sistema
import org.springframework.hateoas.EntityModel; // Importa EntityModel para envolver entidades con enlaces
import org.springframework.hateoas.server.RepresentationModelAssembler; // Importa la interfaz para ensambladores de modelos
import org.springframework.stereotype.Component; // Anotación para marcar esta clase como un componente de Spring
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo; // Importa estático para construir enlaces
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn; // Importa estático para referenciar métodos de controladores

@Component // Marca esta clase como un bean gestionado por Spring, permitiendo su inyección automática
public class ComunaModelAssembler implements RepresentationModelAssembler<Comuna, EntityModel<Comuna>> {

    /**
     * Convierte una entidad Comuna en un EntityModel con enlaces HATEOAS.
     * Este método agrega enlaces a operaciones relacionadas como obtener, actualizar, parchear y eliminar la comuna,
     * facilitando la navegación RESTful en la API.
     *
     * @param comuna La entidad Comuna a convertir
     * @return EntityModel envuelto con enlaces para operaciones relacionadas
     */
    @SuppressWarnings("null") // Suprime advertencias de nulidad, ya que Spring HATEOAS maneja los valores
    @Override
    public EntityModel<Comuna> toModel(Comuna comuna) {
        return EntityModel.of(comuna, // Crea un EntityModel con la comuna y los siguientes enlaces
            linkTo(methodOn(ComunaControllerV2.class).getComunaById(comuna.getIdComuna())).withSelfRel(), // Enlace a sí mismo (self)
            linkTo(methodOn(ComunaControllerV2.class).getAllComunas()).withRel("comunas"), // Enlace a la lista de todas las comunas
            linkTo(methodOn(ComunaControllerV2.class).updateComuna(comuna.getIdComuna(), comuna)).withRel("actualizar"), // Enlace para actualizar completamente la comuna
            linkTo(methodOn(ComunaControllerV2.class).patchComuna(comuna.getIdComuna(), comuna)).withRel("actualizar-parcial"), // Enlace para actualización parcial (patch)
            linkTo(methodOn(ComunaControllerV2.class).deleteComuna(comuna.getIdComuna())).withRel("eliminar") // Enlace para eliminar la comuna
        );
    }
}
