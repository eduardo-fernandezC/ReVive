package com.ReVive.cl.ReVive.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ReVive.cl.ReVive.model.Region;
import com.ReVive.cl.ReVive.repository.RegionRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class RegionServices {

    @Autowired
    private RegionRepository regionRepository;

    public List<Region> findAll() {
        return regionRepository.findAll();
    }

    public Region save(Region region) {
        return regionRepository.save(region);
    }

    public Region update(Long id, Region region) {
        Region regionToUpdate = regionRepository.findById(id).orElse(null);
        if (regionToUpdate != null) {
            regionToUpdate.setNombreRegion(region.getNombreRegion());
            regionToUpdate.setCodigoRegion(region.getCodigoRegion());
            return regionRepository.save(regionToUpdate);
        } else {
            return null;
        }
    }

    public Region patch(Long id, Region region) {
        Region regionToPatch = regionRepository.findById(id).orElse(null);
        if (regionToPatch != null) {
            if (region.getNombreRegion() != null) {
                regionToPatch.setNombreRegion(region.getNombreRegion());
            }
            if (region.getCodigoRegion() != 0) {
                regionToPatch.setCodigoRegion(region.getCodigoRegion());
            }
            return regionRepository.save(regionToPatch);
        } else {
            return null;
        }
    }

    public void delete(Long id) {
        regionRepository.deleteById(id);
    }
    
    public Region findByIdRegion(Long idRegion) {
        return regionRepository.findByIdRegion(idRegion);
    }
    
}