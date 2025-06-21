package com.ReVive.cl.ReVive.assemblers;

import com.ReVive.cl.ReVive.controller.SectorControllerV2;
import com.ReVive.cl.ReVive.model.Sector;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class SectorModelAssembler implements RepresentationModelAssembler<Sector, EntityModel<Sector>> {

    @SuppressWarnings("null")
    @Override
    public EntityModel<Sector> toModel(Sector sector) {
        return EntityModel.of(sector,
                linkTo(methodOn(SectorControllerV2.class).getSectorById(sector.getIdSector())).withSelfRel(),
                linkTo(methodOn(SectorControllerV2.class).getAllSectores()).withRel("sectores"),
                linkTo(methodOn(SectorControllerV2.class).updateSector(sector.getIdSector(), sector)).withRel("actualizar"),
                linkTo(methodOn(SectorControllerV2.class).patchSector(sector.getIdSector(), sector)).withRel("actualizar-parcial"),
                linkTo(methodOn(SectorControllerV2.class).deleteSector(sector.getIdSector())).withRel("eliminar")
        );
    }
}
