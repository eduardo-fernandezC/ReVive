package com.ReVive.cl.ReVive.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ReVive.cl.ReVive.model.Usuarios;
import com.ReVive.cl.ReVive.repository.UsuariosRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UsuariosServices {

    @Autowired
    private UsuariosRepository usuariosRepository;

    public Usuarios findById(Long id) {
        return usuariosRepository.findById(id).orElse(null);
    }

    public List<Usuarios> findAll() {
        return usuariosRepository.findAll();
    }

    public Usuarios save(Usuarios usuario) {
        return usuariosRepository.save(usuario);
    }

    public Usuarios update(Long id, Usuarios usuario) {
        Usuarios usuarioToUpdate = usuariosRepository.findById(id).orElse(null);
        if (usuarioToUpdate != null) {
            usuarioToUpdate.setNombreUsuario(usuario.getNombreUsuario());
            usuarioToUpdate.setApellidoUsuario(usuario.getApellidoUsuario());
            usuarioToUpdate.setDireccionUsuario(usuario.getDireccionUsuario());
            usuarioToUpdate.setCorreoUsuario(usuario.getCorreoUsuario());
            usuarioToUpdate.setSucursal(usuario.getSucursal());
            usuarioToUpdate.setRol(usuario.getRol());
            usuarioToUpdate.setContraseniaUsuario(usuario.getContraseniaUsuario());
            usuarioToUpdate.setSalario(usuario.getSalario());
            return usuariosRepository.save(usuarioToUpdate);
        } else {
            return null;
        }
    }

    public Usuarios patch(Long id, Usuarios usuario) {
        Usuarios usuarioToPatch = usuariosRepository.findById(id).orElse(null);
        if (usuarioToPatch != null) {
            if (usuario.getNombreUsuario() != null) {
                usuarioToPatch.setNombreUsuario(usuario.getNombreUsuario());
            }
            if (usuario.getApellidoUsuario() != null) {
                usuarioToPatch.setApellidoUsuario(usuario.getApellidoUsuario());
            }
            if (usuario.getDireccionUsuario() != null) {
                usuarioToPatch.setDireccionUsuario(usuario.getDireccionUsuario());
            }
            if (usuario.getCorreoUsuario() != null) {
                usuarioToPatch.setCorreoUsuario(usuario.getCorreoUsuario());
            }
            if (usuario.getSucursal() != null) {
                usuarioToPatch.setSucursal(usuario.getSucursal());
            }
            if (usuario.getRol() != null) {
                usuarioToPatch.setRol(usuario.getRol());
            }
            if (usuario.getContraseniaUsuario() != null) {
                usuarioToPatch.setContraseniaUsuario(usuario.getContraseniaUsuario());
            }
            if (usuario.getSalario() != null) {
                usuarioToPatch.setSalario(usuario.getSalario());
            }
            return usuariosRepository.save(usuarioToPatch);
        } else {
            return null;
        }
    }

    public void delete(Long id) {
        usuariosRepository.deleteById(id);
    }
    public List<Usuarios> findByNombreUsuarios(String nombreUsuario) {
        return usuariosRepository.findByNombreUsuario(nombreUsuario);
    }
    public Usuarios findByRunUsuario(String runUsuario) {
        return usuariosRepository.findByRunUsuario(runUsuario);
    }

    public List<Usuarios> findByIdRol(Long idRol) {
    return usuariosRepository.findByRolIdRoles(idRol);
    }

    public List<Usuarios> findByNombreRol(String nombreRol) {
    return usuariosRepository.findByRolNombreRoles(nombreRol);
    }

    public List<Usuarios> findUsuariosConSalarioMayorAlPromedio() {
        return usuariosRepository.findUsuariosConSalarioMayorAlPromedio();
    }

}