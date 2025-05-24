package com.ReVive.cl.ReVive.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ReVive.cl.ReVive.model.Usuarios;

@Repository
public interface UsuariosRepository extends JpaRepository<Usuarios, Long>{

    Usuarios findByRunUsuario(String runUsuario);
    
    List<Usuarios> findByNombreUsuario(String nombreUsuario);

    List<Usuarios> findByRolIdRoles(Long idRol);

    @Query("SELECT u FROM Usuarios u WHERE u.rol.nombreRoles = :nombreRol")
    List<Usuarios> findByRolNombreRoles(String nombreRol);

    @Query("SELECT u FROM Usuarios u WHERE u.salario > (SELECT AVG(u2.salario) FROM Usuarios u2)")
    List<Usuarios> findUsuariosConSalarioMayorAlPromedio();

}
