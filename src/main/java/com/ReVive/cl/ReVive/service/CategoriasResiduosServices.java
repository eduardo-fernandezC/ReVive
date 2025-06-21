package com.ReVive.cl.ReVive.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ReVive.cl.ReVive.model.CategoriasResiduos;
import com.ReVive.cl.ReVive.repository.CategoriasResiduosRepository;
import com.ReVive.cl.ReVive.repository.ResiduosRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CategoriasResiduosServices {

    @Autowired
    private CategoriasResiduosRepository categoriasResiduosRepository;

    @Autowired
    private ResiduosRepository residuosRepository;

    
    public List<CategoriasResiduos> findAll() {
        return categoriasResiduosRepository.findAll();
    }

    public CategoriasResiduos save(CategoriasResiduos categoriaResiduos) {
        return categoriasResiduosRepository.save(categoriaResiduos);
    }

    public CategoriasResiduos update(Long id, CategoriasResiduos categoriaResiduos) {
        CategoriasResiduos categoriaToUpdate = categoriasResiduosRepository.findById(id).orElse(null);
        if (categoriaToUpdate != null) {
            categoriaToUpdate.setNombreCatesResiduos(categoriaResiduos.getNombreCatesResiduos());
            return categoriasResiduosRepository.save(categoriaToUpdate);
        } else {
            return null;
        }
    }

    public CategoriasResiduos patch(Long id, CategoriasResiduos categoriaResiduos) {
        CategoriasResiduos categoriaToUpdate = categoriasResiduosRepository.findById(id).orElse(null);
        if (categoriaToUpdate != null) {
            categoriaToUpdate.setNombreCatesResiduos(categoriaResiduos.getNombreCatesResiduos());
            return categoriasResiduosRepository.save(categoriaToUpdate);
        } else {
            return null;
        }
    }

    public void delete(Long id) {
        CategoriasResiduos categoriasresiduos = categoriasResiduosRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("residuo no encontrado"));


        residuosRepository.deleteByCategoriaResiduos(categoriasresiduos);

        categoriasResiduosRepository.deleteById(id);

    }
    public CategoriasResiduos findByNombreCatesResiduos(String nombreCatesResiduos){
        return categoriasResiduosRepository.findByNombreCatesResiduos(nombreCatesResiduos);
        
    }
    public CategoriasResiduos findByIdCatesResiduos(Long idCatesResiduos){
        return categoriasResiduosRepository.findByIdCatesResiduos(idCatesResiduos);
    }
    
}