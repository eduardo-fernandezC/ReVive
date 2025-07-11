package com.ReVive.cl.ReVive.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ReVive.cl.ReVive.model.CategoriasResiduos;
import com.ReVive.cl.ReVive.model.Residuos;

@Repository
public interface ResiduosRepository extends JpaRepository<Residuos, Long>{

    List<Residuos> findByCantidadResiduos(int cantidadResiduos);

    Residuos findByIdResiduos(Long idResiduos);

    List<Residuos> findByCategoriaResiduos(CategoriasResiduos categoriasresiduos);

    List<Residuos> findByCategoriaResiduos_NombreCatesResiduosAndSucursal_RazonSocialSucursal(String categoria, String sucursal);

    void deleteByCategoriaResiduos(CategoriasResiduos categoriasresiduos);

}
