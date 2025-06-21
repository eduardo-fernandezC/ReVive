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

import com.ReVive.cl.ReVive.model.CategoriasResiduos;
import com.ReVive.cl.ReVive.model.Residuos;
import com.ReVive.cl.ReVive.model.Sucursal;
import com.ReVive.cl.ReVive.repository.ResiduosRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class ResiduosServiceTest {

    @Autowired
    private ResiduosServices residuosServices;

    @MockBean
    private ResiduosRepository residuosRepository;

    private Residuos createResiduos() {
        return new Residuos(1L, 50, new CategoriasResiduos(1L, "Orgánicos"), 
                             new Sucursal(1L, "Sucursal Test", "Dirección Test"));
    }

    @Test
    public void testFindAll() {
        when(residuosRepository.findAll()).thenReturn(List.of(createResiduos()));
        List<Residuos> lista = residuosServices.findAll();
        assertNotNull(lista);
        assertEquals(1, lista.size());
    }

    @Test
    public void testSave() {
        Residuos r = createResiduos();
        r.setCantidadResiduos(30);
        when(residuosRepository.save(r)).thenReturn(r);
        Residuos guardado = residuosServices.save(r);
        assertNotNull(guardado);
        assertEquals(30, guardado.getCantidadResiduos());
    }

    @Test
    public void testUpdate() {
        Residuos existente = createResiduos();
        Residuos nueva = new Residuos(1L, 40,
            new CategoriasResiduos(2L, "Papel"),
            new Sucursal(2L, "Sucursal B", "Dirección B"));

        when(residuosRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(residuosRepository.save(any())).thenReturn(nueva);

        Residuos resultado = residuosServices.update(1L, nueva);
        assertNotNull(resultado);
        assertEquals(40, resultado.getCantidadResiduos());
        assertEquals("Papel", resultado.getCategoriaResiduos().getNombreCatesResiduos());
        assertEquals("Sucursal B", resultado.getSucursal().getRazonSocialSucursal());
    }

    @Test
    public void testPatch() {
        Residuos existente = createResiduos();
        Residuos patch = new Residuos();
        patch.setCantidadResiduos(15);
        patch.setCategoriaResiduos(new CategoriasResiduos(2L, "Cristales"));

        when(residuosRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(residuosRepository.save(any())).thenReturn(existente);

        Residuos resultado = residuosServices.patch(1L, patch);
        assertNotNull(resultado);
        assertEquals(15, resultado.getCantidadResiduos());
        assertEquals("Cristales", resultado.getCategoriaResiduos().getNombreCatesResiduos());
        assertEquals("Sucursal Test", resultado.getSucursal().getRazonSocialSucursal());
    }

    @Test
    public void testDelete() {
        doNothing().when(residuosRepository).deleteById(1L);
        residuosServices.delete(1L);
        verify(residuosRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testFindByCantidadResiduos() {
        when(residuosRepository.findByCantidadResiduos(50)).thenReturn(List.of(createResiduos()));
        List<Residuos> encontrados = residuosServices.findByCantidadResiduos(50);
        assertNotNull(encontrados);
        assertEquals(1, encontrados.size());
        assertEquals(50, encontrados.get(0).getCantidadResiduos());
    }

    @Test
    public void testFindByIdResiduos() {
        when(residuosRepository.findById(1L)).thenReturn(Optional.of(createResiduos()));
        Residuos encontrado = residuosServices.findByIdResiduos(1L);
        assertNotNull(encontrado);
        assertEquals(1L, encontrado.getIdResiduos());
    }

    @Test
    public void testFindByCategoriaAndSucursal() {
        Residuos residuos = createResiduos();

        when(residuosRepository.findByCategoriaResiduos_NombreCatesResiduosAndSucursal_RazonSocialSucursal("Orgánicos", "Sucursal Test"))
            .thenReturn(List.of(residuos));

        List<Residuos> resultado = residuosServices.findByCategoriaAndSucursal("Orgánicos", "Sucursal Test");

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Orgánicos", resultado.get(0).getCategoriaResiduos().getNombreCatesResiduos());
        assertEquals("Sucursal Test", resultado.get(0).getSucursal().getRazonSocialSucursal());

        verify(residuosRepository, times(1))
            .findByCategoriaResiduos_NombreCatesResiduosAndSucursal_RazonSocialSucursal("Orgánicos", "Sucursal Test");
    }

}
