package com.ReVive.cl.ReVive.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ReVive.cl.ReVive.model.Camiones;
import com.ReVive.cl.ReVive.repository.CamionesRepository;

import jakarta.transaction.Transactional;


@Service
@Transactional
public class CamionesServices {

    @Autowired
    private CamionesRepository camionesRepository;

    public Camiones findById(Long id) {
        return camionesRepository.findById(id).orElse(null);
    }

    public List<Camiones> findAll() { 
        return camionesRepository.findAll();
    }

    public Camiones save(Camiones camiones) {    
        return camionesRepository.save(camiones);
    }

    public Camiones update(Long id,Camiones camiones) {
        Camiones camionToUpdate = camionesRepository.findById(id).orElse(null);
        if (camionToUpdate != null) {
            camionToUpdate.setPatenteCamion(camiones.getPatenteCamion());
            camionToUpdate.setSector(camiones.getSector());
            return camionesRepository.save(camionToUpdate);
        } else {
            return null;
        }
    }

    public Camiones patch(Long id, Camiones camion) {

        Camiones camionToPatch = camionesRepository.findById(id).orElse(null);
        if (camionToPatch != null) {
            if (camion.getPatenteCamion() != null) {
                camionToPatch.setPatenteCamion(camion.getPatenteCamion());
            }
            if (camion.getSector() != null) {
                camionToPatch.setSector(camion.getSector());
            }
            return camionesRepository.save(camionToPatch);
        } else {
            return null;
        }
        
    }

    public void delete(Long id) {
        camionesRepository.deleteById(id);
    }

    public Camiones buscarPorPatente(String patente) {
        return camionesRepository.buscarPorPatente(patente);
    }

    public List<Camiones> buscarPorSucursalYComuna(String nombreSucursal, String nombreComuna) {
        return camionesRepository.buscarCamionesPorSucursalYComuna(nombreSucursal, nombreComuna);
    }

}