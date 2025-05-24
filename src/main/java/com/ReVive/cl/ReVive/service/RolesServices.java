package com.ReVive.cl.ReVive.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ReVive.cl.ReVive.model.Roles;
import com.ReVive.cl.ReVive.repository.RolesRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class RolesServices {

    @Autowired
    private RolesRepository rolesRepository;

    public List<Roles> findAll() {
        return rolesRepository.findAll();
    }

    public Roles save(Roles roles) {
        return rolesRepository.save(roles);
    }

    public Roles update(Long id, Roles roles) {
        Roles rolesToUpdate = rolesRepository.findById(id).orElse(null);
        if (rolesToUpdate != null) {
            rolesToUpdate.setNombreRoles(roles.getNombreRoles());
            return rolesRepository.save(rolesToUpdate);
        } else {
            return null;
        }
    }

    public Roles patch(Long id, Roles roles) {
        Roles rolesToPatch = rolesRepository.findById(id).orElse(null);
        if (rolesToPatch != null) {
            if (roles.getNombreRoles() != null) {
                rolesToPatch.setNombreRoles(roles.getNombreRoles());
            }
            return rolesRepository.save(rolesToPatch);
        } else {
            return null;
        }
    }

    public void delete(Long id) {
        rolesRepository.deleteById(id);
    }
    public Roles findByNombreRoles(String nombreRoles) {
        return rolesRepository.findByNombreRoles(nombreRoles);
    }
    public Roles findByIdRoles(Long id) {
        return rolesRepository.findById(id).orElse(null);
    }
}