package com.ReVive.cl.ReVive.controller;

import com.ReVive.cl.ReVive.assemblers.CamionesModelAssembler;
import com.ReVive.cl.ReVive.model.Camiones;
import com.ReVive.cl.ReVive.service.CamionesServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v2/camiones")
@Tag(name = "Camiones V2", description = "Operaciones HATEOAS para camiones")
public class CamionesControllerV2 {

    @Autowired
    private CamionesServices camionesServices;

    @Autowired
    private CamionesModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Listar todos los camiones", description = "Obtiene una lista de todos los camiones con enlaces HATEOAS")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Camiones encontrados"),
        @ApiResponse(responseCode = "204", description = "No hay camiones disponibles")
    })
    public ResponseEntity<CollectionModel<EntityModel<Camiones>>> getAllCamiones() {
        List<EntityModel<Camiones>> camiones = camionesServices.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        if (camiones.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(CollectionModel.of(
                camiones,
                linkTo(methodOn(CamionesControllerV2.class).getAllCamiones()).withSelfRel()
        ));
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Buscar camión por ID", description = "Obtiene un camión por su ID con enlaces HATEOAS")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Camión encontrado"),
        @ApiResponse(responseCode = "404", description = "Camión no encontrado")
    })
    public ResponseEntity<EntityModel<Camiones>> getCamionById(@PathVariable Long id) {
        Camiones camion = camionesServices.findById(id);
        if (camion == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(camion));
    }

    @GetMapping(value = "/buscarPatente/{patente}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Buscar camión por patente", description = "Obtiene un camión por su patente con enlaces HATEOAS")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Camión encontrado"),
        @ApiResponse(responseCode = "404", description = "Camión no encontrado")
    })
    public ResponseEntity<EntityModel<Camiones>> getCamionByPatente(@PathVariable String patente) {
        Camiones camion = camionesServices.buscarPorPatente(patente);
        if (camion == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(camion));
    }

    @GetMapping(value = "/buscarPorSucursalYComuna", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Buscar camiones por sucursal y comuna", description = "Obtiene camiones por nombre de sucursal y comuna con enlaces HATEOAS")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Camiones encontrados"),
        @ApiResponse(responseCode = "204", description = "No hay camiones disponibles")
    })
    public ResponseEntity<CollectionModel<EntityModel<Camiones>>> getCamionesPorSucursalYComuna(
            @RequestParam String nombreSucursal,
            @RequestParam String nombreComuna) {
        List<Camiones> camionesList = camionesServices.buscarPorSucursalYComuna(nombreSucursal, nombreComuna);

        if (camionesList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<EntityModel<Camiones>> camiones = camionesList.stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(
                camiones,
                linkTo(methodOn(CamionesControllerV2.class)
                        .getCamionesPorSucursalYComuna(nombreSucursal, nombreComuna)).withSelfRel()
        ));
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Crear nuevo camión", description = "Guarda un nuevo camión y devuelve su representación con enlaces HATEOAS")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Camión creado correctamente"),
        @ApiResponse(responseCode = "400", description = "Error al crear el camión")
    })
    public ResponseEntity<EntityModel<Camiones>> createCamion(@RequestBody Camiones camiones) {
        Camiones nuevo = camionesServices.save(camiones);
        return ResponseEntity
                .created(linkTo(methodOn(CamionesControllerV2.class).getCamionById(nuevo.getIdCamion())).toUri())
                .body(assembler.toModel(nuevo));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Actualizar camión", description = "Actualiza un camión por ID con enlaces HATEOAS")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Camión actualizado correctamente"),
        @ApiResponse(responseCode = "404", description = "Camión no encontrado")
    })
    public ResponseEntity<EntityModel<Camiones>> updateCamion(@PathVariable Long id, @RequestBody Camiones camiones) {
        Camiones actualizado = camionesServices.update(id, camiones);
        if (actualizado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(actualizado));
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Modificar parcialmente camión", description = "Modifica parcialmente un camión por ID con enlaces HATEOAS")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Camión modificado correctamente"),
        @ApiResponse(responseCode = "404", description = "Camión no encontrado")
    })
    public ResponseEntity<EntityModel<Camiones>> patchCamion(@PathVariable Long id, @RequestBody Camiones camiones) {
        Camiones actualizado = camionesServices.patch(id, camiones);
        if (actualizado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(actualizado));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Eliminar camión", description = "Elimina un camión por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Camión eliminado correctamente"),
        @ApiResponse(responseCode = "404", description = "Camión no encontrado")
    })
    public ResponseEntity<Void> deleteCamion(@PathVariable Long id) {
        Camiones camion = camionesServices.findById(id);
        if (camion == null) {
            return ResponseEntity.notFound().build();
        }
        camionesServices.delete(id);
        return ResponseEntity.noContent().build();
    }
}
