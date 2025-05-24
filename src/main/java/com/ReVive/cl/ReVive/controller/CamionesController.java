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

import com.ReVive.cl.ReVive.model.Camiones;
import com.ReVive.cl.ReVive.service.CamionesServices;



@RestController

@RequestMapping("/api/v1/camiones")
public class CamionesController {

  @Autowired
  private CamionesServices camionesServices;

  @GetMapping
  public ResponseEntity<List<Camiones>> listar() {
    List<Camiones> camiones = camionesServices.findAll();
    if (camiones.isEmpty()) {
      return ResponseEntity.noContent().build();
    }
    return ResponseEntity.ok(camiones);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Camiones> buscar(@PathVariable Long id) {
    Camiones camion = camionesServices.findById(id);
    if (camion != null) {
        return ResponseEntity.ok(camion);
    } else {
        return ResponseEntity.notFound().build();
    }
  }

  @GetMapping("/buscarPatente/{patente}")
    public ResponseEntity<Camiones> buscarPorPatente(@PathVariable String patente) {
        Camiones camion = camionesServices.buscarPorPatente(patente);
        if (camion != null) {
            return ResponseEntity.ok(camion);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

  @PostMapping
  public ResponseEntity<Camiones> guardar(@RequestBody Camiones camiones) {
    Camiones camionesGuardar = camionesServices.save(camiones);
    return ResponseEntity.status(HttpStatus.CREATED).body(camionesGuardar);
  }


  @PutMapping("/{id}")
  public ResponseEntity<Camiones> actualizar(@PathVariable Long id, @RequestBody Camiones camiones) {
    Camiones actualizado = camionesServices.update(id, camiones);
    if (actualizado != null) {
      return ResponseEntity.ok(actualizado);
    } else {
      return ResponseEntity.notFound().build();
    }
  }


  @PatchMapping("/{id}")
  public ResponseEntity<Camiones> patchCamiones(@PathVariable Long id, @RequestBody Camiones camiones) {
    Camiones actualizado = camionesServices.patch(id, camiones);
    if (actualizado != null) {
      return ResponseEntity.ok(actualizado);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> eliminar(@PathVariable Long id) {
    try {
      camionesServices.delete(id);
      return ResponseEntity.noContent().build();
    } catch (Exception e) {
      return ResponseEntity.notFound().build();
    }
  }

}

