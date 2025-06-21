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

import com.ReVive.cl.ReVive.model.Comuna;
import com.ReVive.cl.ReVive.model.Region;
import com.ReVive.cl.ReVive.repository.ComunaRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class ComunaServiceTest {

    @Autowired
    private ComunaServices comunaServices;

    @MockBean
    private ComunaRepository comunaRepository;

    private Comuna createComuna() {
        return new Comuna(1L, 101, "Providencia", new Region());
    }

    @Test
    public void testFindAll() {
        when(comunaRepository.findAll()).thenReturn(List.of(createComuna()));
        List<Comuna> lista = comunaServices.findAll();
        assertNotNull(lista);
        assertEquals(1, lista.size());
    }

    @Test
    public void testFindById() {
        when(comunaRepository.findById(1L)).thenReturn(Optional.of(createComuna()));
        Comuna encontrada = comunaServices.findById(1L);
        assertNotNull(encontrada);
        assertEquals(1L, encontrada.getIdComuna());
    }

    @Test
    public void testSave() {
        Comuna comuna = createComuna();
        when(comunaRepository.save(comuna)).thenReturn(comuna);
        Comuna guardada = comunaServices.save(comuna);
        assertNotNull(guardada);
        assertEquals("Providencia", guardada.getNombreComuna());
    }

    @Test
    public void testUpdate() {
        Comuna existente = createComuna();
        Comuna nueva = new Comuna(1L, 102, "La Florida", new Region());
        when(comunaRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(comunaRepository.save(any())).thenReturn(nueva);
        Comuna actualizada = comunaServices.update(1L, nueva);
        assertNotNull(actualizada);
        assertEquals("La Florida", actualizada.getNombreComuna());
    }

    @Test
    public void testPatch() {
        Comuna existente = createComuna();
        Comuna patchData = new Comuna();
        patchData.setNombreComuna("San Joaquín");
        patchData.setCodigoComuna(107);
        patchData.setRegion(new Region(2L, 13, "Región Metropolitana"));
        when(comunaRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(comunaRepository.save(any())).thenReturn(existente);
        Comuna resultado = comunaServices.patch(1L, patchData);
        assertNotNull(resultado);
        assertEquals("San Joaquín", resultado.getNombreComuna());
    }

    @Test
    public void testDelete() {
        doNothing().when(comunaRepository).deleteById(1L);
        comunaServices.delete(1L);
        verify(comunaRepository, times(1)).deleteById(1L);
    }
}
