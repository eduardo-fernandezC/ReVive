package com.ReVive.cl.ReVive.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import com.ReVive.cl.ReVive.model.Roles;
import com.ReVive.cl.ReVive.model.Sucursal;
import com.ReVive.cl.ReVive.model.Usuarios;
import com.ReVive.cl.ReVive.repository.UsuariosRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class UsuariosServiceTest {

    @Autowired
    private UsuariosServices usuariosServices;

    @MockBean
    private UsuariosRepository usuariosRepository;

    private Usuarios createUsuario() {
        return new Usuarios(1L, "12345678-9", "Juan", "Perez",
                "Calle Falsa 123", "juan@mail.com", "pass", 
                500.0, new Sucursal(), new Roles());
    }

    @Test
    public void testFindAll() {
        when(usuariosRepository.findAll()).thenReturn(List.of(createUsuario()));
        List<Usuarios> lista = usuariosServices.findAll();
        assertNotNull(lista);
        assertEquals(1, lista.size());
    }

    @Test
    public void testFindById() {
        when(usuariosRepository.findById(1L)).thenReturn(Optional.of(createUsuario()));
        Usuarios u = usuariosServices.findById(1L);
        assertNotNull(u);
        assertEquals(1L, u.getIdUsuario());
    }

    @Test
    public void testSave() {
        Usuarios u = createUsuario();
        when(usuariosRepository.save(u)).thenReturn(u);
        Usuarios resultado = usuariosServices.save(u);
        assertNotNull(resultado);
        assertEquals("Juan", resultado.getNombreUsuario());
    }

    @Test
    public void testUpdate() {
        Usuarios existente = createUsuario();
        Usuarios nuevo = new Usuarios(1L, "12345678-9", "Carlos", "Lopez",
                "Av. Siempre Viva", "carlos@mail.com", "new", 600.0, new Sucursal(), new Roles());
        when(usuariosRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(usuariosRepository.save(any())).thenReturn(nuevo);
        Usuarios resultado = usuariosServices.update(1L, nuevo);
        assertNotNull(resultado);
        assertEquals("Carlos", resultado.getNombreUsuario());
    }

    @Test
    public void testPatch() {
        Usuarios existente = createUsuario();
        Usuarios patch = new Usuarios();
        patch.setNombreUsuario("Carlos");
        when(usuariosRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(usuariosRepository.save(any())).thenReturn(existente);
        Usuarios resultado = usuariosServices.patch(1L, patch);
        assertNotNull(resultado);
        assertEquals("Carlos", resultado.getNombreUsuario());
    }

    @Test
    public void testDelete() {
        doNothing().when(usuariosRepository).deleteById(1L);
        usuariosServices.delete(1L);
        verify(usuariosRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testFindByNombreUsuarios() {
        when(usuariosRepository.findByNombreUsuario("Juan")).thenReturn(List.of(createUsuario()));
        List<Usuarios> lista = usuariosServices.findByNombreUsuarios("Juan");
        assertNotNull(lista);
        assertEquals(1, lista.size());
    }

    @Test
    public void testFindByRunUsuario() {
        when(usuariosRepository.findByRunUsuario("12345678-9")).thenReturn(createUsuario());
        Usuarios u = usuariosServices.findByRunUsuario("12345678-9");
        assertNotNull(u);
        assertEquals("12345678-9", u.getRunUsuario());
    }

    @Test
    public void testFindByIdRol() {
        when(usuariosRepository.findByRolIdRoles(1L)).thenReturn(List.of(createUsuario()));
        List<Usuarios> list = usuariosServices.findByIdRol(1L);
        assertNotNull(list);
        assertEquals(1, list.size());
    }

    @Test
    public void testFindUsuariosConSalarioMayorAlPromedio() {
        when(usuariosRepository.findUsuariosConSalarioMayorAlPromedio()).thenReturn(List.of(createUsuario()));
        List<Usuarios> list = usuariosServices.findUsuariosConSalarioMayorAlPromedio();
        assertNotNull(list);
        assertEquals(1, list.size());
    }

    @Test
    public void testFindByRolAndSucursal() {
        Usuarios usuario = createUsuario();
        when(usuariosRepository.findByRol_NombreRolesAndSucursal_RazonSocialSucursal("Administrador", "Sucursal A"))
            .thenReturn(List.of(usuario));

        List<Usuarios> resultado = usuariosServices.findByRolAndSucursal("Administrador", "Sucursal A");

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(usuariosRepository, times(1))
            .findByRol_NombreRolesAndSucursal_RazonSocialSucursal("Administrador", "Sucursal A");
    }

}
