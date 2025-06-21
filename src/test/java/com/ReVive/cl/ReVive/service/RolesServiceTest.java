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
import com.ReVive.cl.ReVive.repository.RolesRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class RolesServiceTest {

    @Autowired
    private RolesServices rolesServices;

    @MockBean
    private RolesRepository rolesRepository;

    private Roles createRoles(String name) {
        return new Roles(1L, name);
    }

    @Test
    public void testFindAll() {
        when(rolesRepository.findAll()).thenReturn(List.of(createRoles("ADMIN")));
        List<Roles> lista = rolesServices.findAll();
        assertNotNull(lista);
        assertEquals(1, lista.size());
    }

    @Test
    public void testSave() {
        Roles r = new Roles(null, "USER");
        when(rolesRepository.save(r)).thenReturn(createRoles("USER"));
        Roles resultado = rolesServices.save(r);
        assertNotNull(resultado);
        assertEquals("USER", resultado.getNombreRoles());
    }

    @Test
    public void testUpdate() {
        Roles existente = createRoles("USER");
        Roles nueva = new Roles(1L, "ADMIN");
        when(rolesRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(rolesRepository.save(any())).thenReturn(nueva);
        Roles resultado = rolesServices.update(1L, nueva);
        assertNotNull(resultado);
        assertEquals("ADMIN", resultado.getNombreRoles());
    }

    @Test
    public void testPatch() {
        Roles existente = createRoles("USER");
        Roles patch = new Roles();
        patch.setNombreRoles("ADMIN");

        when(rolesRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(rolesRepository.save(any())).thenReturn(existente);

        Roles resultado = rolesServices.patch(1L, patch);
        assertNotNull(resultado);
        assertEquals("ADMIN", resultado.getNombreRoles());
    }

    @Test
    public void testDelete() {
        doNothing().when(rolesRepository).deleteById(1L);
        rolesServices.delete(1L);
        verify(rolesRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testFindByNombreRoles() {
        when(rolesRepository.findByNombreRoles("ADMIN")).thenReturn(createRoles("ADMIN"));
        Roles resultado = rolesServices.findByNombreRoles("ADMIN");
        assertNotNull(resultado);
        assertEquals("ADMIN", resultado.getNombreRoles());
    }

    @Test
    public void testFindByIdRoles() {
        when(rolesRepository.findById(1L)).thenReturn(Optional.of(createRoles("USER")));
        Roles resultado = rolesServices.findByIdRoles(1L);
        assertNotNull(resultado);
        assertEquals(1L, resultado.getIdRoles());
    }
}
