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

import com.ReVive.cl.ReVive.model.Sucursal;
import com.ReVive.cl.ReVive.repository.SucursalRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class SucursalServiceTest {

    @Autowired
    private SucursalServices sucursalServices;

    @MockBean
    private SucursalRepository sucursalRepository;

    private Sucursal createSucursal() {
        return new Sucursal(1L, "Sucursal1", "Direccion1");
    }

    @Test
    public void testFindAll() {
        when(sucursalRepository.findAll()).thenReturn(List.of(createSucursal()));
        List<Sucursal> lista = sucursalServices.findAll();
        assertNotNull(lista);
        assertEquals(1, lista.size());
    }

    @Test
    public void testSave() {
        Sucursal s = new Sucursal(null, "Sucursal Nueva", "Direccion Nueva");
        Sucursal guardada = createSucursal();
        when(sucursalRepository.save(s)).thenReturn(guardada);
        Sucursal resultado = sucursalServices.save(s);
        assertNotNull(resultado);
        assertEquals("Sucursal1", resultado.getRazonSocialSucursal());
    }

    @Test
    public void testUpdate() {
        Sucursal existente = createSucursal();
        Sucursal nueva = new Sucursal(1L, "Sucursal2", "Direccion2");
        when(sucursalRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(sucursalRepository.save(any())).thenReturn(nueva);
        Sucursal resultado = sucursalServices.update(1L, nueva);
        assertNotNull(resultado);
        assertEquals("Sucursal2", resultado.getRazonSocialSucursal());
    }

    @Test
    public void testPatch() {
        Sucursal existente = createSucursal();
        Sucursal patch = new Sucursal();
        patch.setRazonSocialSucursal("Sucursal Patched");

        when(sucursalRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(sucursalRepository.save(any())).thenReturn(existente);

        Sucursal resultado = sucursalServices.patch(1L, patch);
        assertNotNull(resultado);
        assertEquals("Sucursal Patched", resultado.getRazonSocialSucursal());
    }

    @Test
    public void testDelete() {
        doNothing().when(sucursalRepository).deleteById(1L);
        sucursalServices.delete(1L);
        verify(sucursalRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testFindByRazonSocial() {
        when(sucursalRepository.findByRazonSocialSucursal("Sucursal1")).thenReturn(createSucursal());
        Sucursal resultado = sucursalServices.findByRazonSocial("Sucursal1");
        assertNotNull(resultado);
        assertEquals("Sucursal1", resultado.getRazonSocialSucursal());
    }

    @Test
    public void testFindByIdSucursal() {
        when(sucursalRepository.findById(1L)).thenReturn(Optional.of(createSucursal()));
        Sucursal resultado = sucursalServices.findByIdSucursal(1L);
        assertNotNull(resultado);
        assertEquals(1L, resultado.getIdSucursal());
    }
}
