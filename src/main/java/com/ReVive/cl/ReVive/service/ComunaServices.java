package com.ReVive.cl.ReVive.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ReVive.cl.ReVive.model.Comuna;
import com.ReVive.cl.ReVive.repository.ComunaRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ComunaServices {

    @Autowired
    private ComunaRepository comunaRepository;

    public List<Comuna> findAll() {
        return comunaRepository.findAll();
    }

    public Comuna findById(Long id) {
        return comunaRepository.findById(id).orElse(null);
    }

    public Comuna save(Comuna comuna) {
        return comunaRepository.save(comuna);
    }

    public Comuna update(Long id, Comuna comuna) {
        Comuna comunaToUpdate = comunaRepository.findById(id).orElse(null);
        if (comunaToUpdate != null) {
            comunaToUpdate.setNombreComuna(comuna.getNombreComuna());
            comunaToUpdate.setCodigoComuna(comuna.getCodigoComuna());
            comunaToUpdate.setRegion(comuna.getRegion());
            return comunaRepository.save(comunaToUpdate);
        } else {
            return null;
        }
    }

    public Comuna patch(Long id, Comuna comuna) {
        Comuna comunaToPatch = comunaRepository.findById(id).orElse(null);
        if (comunaToPatch != null) {
            if (comuna.getNombreComuna() != null) {
                comunaToPatch.setNombreComuna(comuna.getNombreComuna());
            }
            if (comuna.getCodigoComuna() != 0) {
                comunaToPatch.setCodigoComuna(comuna.getCodigoComuna());
            }
            if (comuna.getRegion() != null) {
                comunaToPatch.setRegion(comuna.getRegion());
            }
            return comunaRepository.save(comunaToPatch);
        } else {
            return null;
        }
    }

    public void delete(Long id) {
        comunaRepository.deleteById(id);
    }
}
