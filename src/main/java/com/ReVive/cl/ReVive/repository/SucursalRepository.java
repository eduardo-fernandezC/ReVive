package com.ReVive.cl.ReVive.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ReVive.cl.ReVive.model.Sucursal;

@Repository
public interface SucursalRepository extends JpaRepository<Sucursal, Long>{
    Sucursal findByRazonSocialSucursal(String razonSocialSucursal);
    Sucursal findByIdSucursal(Long idSucursal);
}
