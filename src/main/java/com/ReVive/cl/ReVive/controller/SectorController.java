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

import com.ReVive.cl.ReVive.model.Sector;
import com.ReVive.cl.ReVive.service.SectorServices;

@RestController
@RequestMapping("/api/v1/sectores")
public class SectorController {

    @Autowired
    private SectorServices sectorServices;

    @GetMapping
    public ResponseEntity<List<Sector>> listar() {
        List<Sector> sectores = sectorServices.findAll();
        if (sectores.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(sectores);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sector> buscar(@PathVariable Long id) {
        Sector sector = sectorServices.findByIdSector(id);
        if (sector != null) {
            return ResponseEntity.ok(sector);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Sector> guardar(@RequestBody Sector sector) {
        Sector sectorGuardar = sectorServices.save(sector);
        return ResponseEntity.status(HttpStatus.CREATED).body(sectorGuardar);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Sector> actualizar(@PathVariable Long id, @RequestBody Sector sector) {
        Sector actualizado = sectorServices.update(id, sector);
        if (actualizado != null) {
            return ResponseEntity.ok(actualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Sector> patchSector(@PathVariable Long id, @RequestBody Sector sector) {
        Sector actualizado = sectorServices.patch(id, sector);
        if (actualizado != null) {
            return ResponseEntity.ok(actualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            sectorServices.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
