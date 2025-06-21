package com.ReVive.cl.ReVive.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ReVive.cl.ReVive.model.CategoriasResiduos;

@Repository
public interface CategoriasResiduosRepository extends JpaRepository<CategoriasResiduos, Long>{

    CategoriasResiduos findByNombreCatesResiduos(String nombreCatesResiduos);

    CategoriasResiduos findByIdCatesResiduos(Long idCatesResiduos);
}
