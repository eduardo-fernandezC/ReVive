package com.ReVive.cl.ReVive.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ReVive.cl.ReVive.model.Residuos;
import com.ReVive.cl.ReVive.repository.ResiduosRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ResiduosServices {

    @Autowired
    private ResiduosRepository residuosRepository;

    public List<Residuos> findAll() {
        return residuosRepository.findAll();
    }

    public Residuos save(Residuos residuos) {
        return residuosRepository.save(residuos);
    }

    public Residuos update(Long id, Residuos residuos) {
        Residuos residuosToUpdate = residuosRepository.findById(id).orElse(null);
        if (residuosToUpdate != null) {
            residuosToUpdate.setCantidadResiduos(residuos.getCantidadResiduos());
            residuosToUpdate.setCategoriaResiduos(residuos.getCategoriaResiduos());
            residuosToUpdate.setSucursal(residuos.getSucursal());
            return residuosRepository.save(residuosToUpdate);
        } else {
            return null;
        }
    }

    public Residuos patch(Long id, Residuos residuos) {
        Residuos residuosToPatch = residuosRepository.findById(id).orElse(null);
        if (residuosToPatch != null) {
            if (residuos.getCantidadResiduos() != 0) {
                residuosToPatch.setCantidadResiduos(residuos.getCantidadResiduos());
            }
            if (residuos.getCategoriaResiduos() != null) {
                residuosToPatch.setCategoriaResiduos(residuos.getCategoriaResiduos());
            }
            if (residuos.getSucursal() != null) {
                residuosToPatch.setSucursal(residuos.getSucursal());
            }
            return residuosRepository.save(residuosToPatch);
        } else {
            return null;
        }
    }

    public void delete(Long id) {
        residuosRepository.deleteById(id);
    }
    public List<Residuos> findByCantidadResiduos(int cantidadResiduos){
        return residuosRepository.findByCantidadResiduos(cantidadResiduos);
    }
    public Residuos findByIdResiduos(Long idResiduos) {
        return residuosRepository.findById(idResiduos).orElse(null);
    }
}