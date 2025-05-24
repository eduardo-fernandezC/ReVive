package com.ReVive.cl.ReVive.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ReVive.cl.ReVive.model.Roles;
@Repository

public interface RolesRepository extends JpaRepository<Roles, Long>{
    Roles findByNombreRoles(String nombreRoles);
    Roles findByIdRoles(Long idRoles);
}
