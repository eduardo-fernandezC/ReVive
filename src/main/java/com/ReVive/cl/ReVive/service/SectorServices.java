package com.ReVive.cl.ReVive.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ReVive.cl.ReVive.model.Sector;
import com.ReVive.cl.ReVive.repository.SectorRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class SectorServices {

    @Autowired
    private SectorRepository sectorRepository;

    public List<Sector> findAll() {
        return sectorRepository.findAll();
    }

    public Sector save(Sector sector) {
        return sectorRepository.save(sector);
    }

    public Sector update(Long id, Sector sector) {
        Sector sectorToUpdate = sectorRepository.findById(id).orElse(null);
        if (sectorToUpdate != null) {
            sectorToUpdate.setNombreSector(sector.getNombreSector());
            sectorToUpdate.setDireccionSector(sector.getDireccionSector());
            sectorToUpdate.setComuna(sector.getComuna());
            return sectorRepository.save(sectorToUpdate);
        } else {
            return null;
        }
    }

    public Sector patch(Long id, Sector sector) {
        Sector sectorToPatch = sectorRepository.findById(id).orElse(null);
        if (sectorToPatch != null) {
            if (sector.getNombreSector() != null) {
                sectorToPatch.setNombreSector(sector.getNombreSector());
            }
            if (sector.getDireccionSector() != null) {
                sectorToPatch.setDireccionSector(sector.getDireccionSector());
            }
            if (sector.getComuna() != null) {
                sectorToPatch.setComuna(sector.getComuna());
            }
            return sectorRepository.save(sectorToPatch);
        } else {
            return null;
        }
    }

    public void delete(Long id) {
        sectorRepository.deleteById(id);
    }
    public Sector findByIdSector(Long idSector) {
        return sectorRepository.findByIdSector(idSector);
    }
}