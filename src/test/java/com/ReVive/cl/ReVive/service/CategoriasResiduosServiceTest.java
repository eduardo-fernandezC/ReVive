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
import com.ReVive.cl.ReVive.repository.CategoriasResiduosRepository;
import com.ReVive.cl.ReVive.repository.ResiduosRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class CategoriasResiduosServiceTest {

    @Autowired
    private CategoriasResiduosServices categoriasResiduosServices;

    @MockBean
    private CategoriasResiduosRepository categoriasResiduosRepository;

    @MockBean
    private ResiduosRepository residuosRepository;


    private CategoriasResiduos createCategoria() {
        return new CategoriasResiduos(1L, "Orgánicos");
    }

    @Test
    public void testFindAll() {
        when(categoriasResiduosRepository.findAll()).thenReturn(List.of(createCategoria()));
        List<CategoriasResiduos> lista = categoriasResiduosServices.findAll();
        assertNotNull(lista);
        assertEquals(1, lista.size());
    }

    @Test
    public void testSave() {
        CategoriasResiduos categoria = createCategoria();
        when(categoriasResiduosRepository.save(categoria)).thenReturn(categoria);
        CategoriasResiduos guardada = categoriasResiduosServices.save(categoria);
        assertNotNull(guardada);
        assertEquals("Orgánicos", guardada.getNombreCatesResiduos());
    }

    @Test
    public void testUpdate() {
        CategoriasResiduos existente = createCategoria();
        CategoriasResiduos nueva = new CategoriasResiduos(1L, "Actualizada");
        when(categoriasResiduosRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(categoriasResiduosRepository.save(any())).thenReturn(nueva);
        CategoriasResiduos actualizada = categoriasResiduosServices.update(1L, nueva);
        assertNotNull(actualizada);
        assertEquals("Actualizada", actualizada.getNombreCatesResiduos());
    }

    @Test
    public void testPatch() {
        CategoriasResiduos existente = createCategoria();
        CategoriasResiduos patchData = new CategoriasResiduos();
        patchData.setNombreCatesResiduos("Parcheada");
        when(categoriasResiduosRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(categoriasResiduosRepository.save(any())).thenReturn(existente);
        CategoriasResiduos resultado = categoriasResiduosServices.patch(1L, patchData);
        assertNotNull(resultado);
        assertEquals("Parcheada", resultado.getNombreCatesResiduos());
    }

    @Test
    public void testDelete() {
        CategoriasResiduos categoria = createCategoria();

        when(categoriasResiduosRepository.findById(1L)).thenReturn(Optional.of(categoria));
        doNothing().when(residuosRepository).deleteByCategoriaResiduos(categoria);
        doNothing().when(categoriasResiduosRepository).deleteById(1L);

        categoriasResiduosServices.delete(1L);

        verify(categoriasResiduosRepository, times(1)).findById(1L);
        verify(residuosRepository, times(1)).deleteByCategoriaResiduos(categoria);
        verify(categoriasResiduosRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testFindByNombreCatesResiduos() {
        when(categoriasResiduosRepository.findByNombreCatesResiduos("Orgánicos")).thenReturn(createCategoria());
        CategoriasResiduos encontrada = categoriasResiduosServices.findByNombreCatesResiduos("Orgánicos");
        assertNotNull(encontrada);
        assertEquals("Orgánicos", encontrada.getNombreCatesResiduos());
    }

    @Test
    public void testFindByIdCatesResiduos() {
        when(categoriasResiduosRepository.findByIdCatesResiduos(1L)).thenReturn(createCategoria());
        CategoriasResiduos encontrada = categoriasResiduosServices.findByIdCatesResiduos(1L);
        assertNotNull(encontrada);
        assertEquals(1L, encontrada.getIdCatesResiduos());
    }
}

