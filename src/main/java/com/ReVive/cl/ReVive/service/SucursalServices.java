package com.ReVive.cl.ReVive.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ReVive.cl.ReVive.model.Sucursal;
import com.ReVive.cl.ReVive.repository.SucursalRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class SucursalServices {

    @Autowired
    private SucursalRepository sucursalRepository;

    public List<Sucursal> findAll() {
        return sucursalRepository.findAll();
    }

    public Sucursal save(Sucursal sucursal) {
        return sucursalRepository.save(sucursal);
    }

    public Sucursal update(Long id, Sucursal sucursal) {
        Sucursal sucursalToUpdate = sucursalRepository.findById(id).orElse(null);
        if (sucursalToUpdate != null) {
            sucursalToUpdate.setRazonSocialSucursal(sucursal.getRazonSocialSucursal());
            sucursalToUpdate.setDireccionSucursal(sucursal.getDireccionSucursal());
            return sucursalRepository.save(sucursalToUpdate);
        } else {
            return null;
        }
    }

    public Sucursal patch(Long id, Sucursal sucursal) {
        Sucursal sucursalToPatch = sucursalRepository.findById(id).orElse(null);
        if (sucursalToPatch != null) {
            if (sucursal.getRazonSocialSucursal() != null) {
                sucursalToPatch.setRazonSocialSucursal(sucursal.getRazonSocialSucursal());
            }
            if (sucursal.getDireccionSucursal() != null) {
                sucursalToPatch.setDireccionSucursal(sucursal.getDireccionSucursal());
            }
            return sucursalRepository.save(sucursalToPatch);
        } else {
            return null;
        }
    }

    public void delete(Long id) {
        sucursalRepository.deleteById(id);
    }
    public Sucursal findByRazonSocial(String razonSocialSucursal){
        return sucursalRepository.findByRazonSocialSucursal(razonSocialSucursal);
    }
    public Sucursal findByIdSucursal(Long idSucursal) {
        return sucursalRepository.findById(idSucursal).orElse(null);
    }
}