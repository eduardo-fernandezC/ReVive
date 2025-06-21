package com.ReVive.cl.ReVive.assemblers;

import com.ReVive.cl.ReVive.controller.RegionControllerV2;
import com.ReVive.cl.ReVive.model.Region;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class RegionModelAssembler implements RepresentationModelAssembler<Region, EntityModel<Region>> {

    @SuppressWarnings("null")
    @Override
    public EntityModel<Region> toModel(Region region) {
        return EntityModel.of(region,
                linkTo(methodOn(RegionControllerV2.class).getRegionById(region.getIdRegion())).withSelfRel(),
                linkTo(methodOn(RegionControllerV2.class).getAllRegiones()).withRel("regiones"),
                linkTo(methodOn(RegionControllerV2.class).updateRegion(region.getIdRegion(), region)).withRel("actualizar"),
                linkTo(methodOn(RegionControllerV2.class).patchRegion(region.getIdRegion(), region)).withRel("actualizar-parcial"),
                linkTo(methodOn(RegionControllerV2.class).deleteRegion(region.getIdRegion())).withRel("eliminar")
        );
    }
}
