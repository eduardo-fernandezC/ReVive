package com.ReVive.cl.ReVive.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ReVive.cl.ReVive.model.Region;
import com.ReVive.cl.ReVive.service.RegionServices;

@RestController
@RequestMapping("/api/v1/regiones")
public class RegionController {

    @Autowired
    private RegionServices regionServices;

    @GetMapping
    public ResponseEntity<List<Region>> listar() {
        List<Region> regiones = regionServices.findAll();
        if (regiones.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(regiones);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Region> buscar(@PathVariable Long id) {
        Region region = regionServices.findByIdRegion(id);
        if (region != null) {
            return ResponseEntity.ok(region);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Region> guardar(@RequestBody Region region) {
        Region regionGuardar = regionServices.save(region);
        return ResponseEntity.status(HttpStatus.CREATED).body(regionGuardar);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Region> actualizar(@PathVariable Long id, @RequestBody Region region) {
        Region actualizado = regionServices.update(id, region);
        if (actualizado != null) {
            return ResponseEntity.ok(actualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Region> patchRegion(@PathVariable Long id, @RequestBody Region region) {
        Region actualizado = regionServices.patch(id, region);
        if (actualizado != null) {
            return ResponseEntity.ok(actualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            regionServices.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
