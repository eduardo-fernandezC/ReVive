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

import com.ReVive.cl.ReVive.model.Region;
import com.ReVive.cl.ReVive.repository.RegionRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class RegionServiceTest {

    @Autowired
    private RegionServices regionServices;

    @MockBean
    private RegionRepository regionRepository;

    private Region createRegion() {
        return new Region(1L, 10, "Región Test");
    }

    @Test
    public void testFindAll() {
        when(regionRepository.findAll()).thenReturn(List.of(createRegion()));
        List<Region> lista = regionServices.findAll();
        assertNotNull(lista);
        assertEquals(1, lista.size());
    }

    @Test
    public void testSave() {
        Region region = createRegion();
        when(regionRepository.save(region)).thenReturn(region);
        Region guardada = regionServices.save(region);
        assertNotNull(guardada);
        assertEquals("Región Test", guardada.getNombreRegion());
    }

    @Test
    public void testUpdate() {
        Region existente = createRegion();
        Region nueva = new Region(1L, 11, "Región Actualizada");
        when(regionRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(regionRepository.save(any())).thenReturn(nueva);
        Region actualizada = regionServices.update(1L, nueva);
        assertNotNull(actualizada);
        assertEquals("Región Actualizada", actualizada.getNombreRegion());
    }

    @Test
    public void testPatch() {
        Region existente = createRegion();
        Region patchData = new Region();
        patchData.setNombreRegion("Parcheada");
        when(regionRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(regionRepository.save(any())).thenReturn(existente);
        Region resultado = regionServices.patch(1L, patchData);
        assertNotNull(resultado);
        assertEquals("Parcheada", resultado.getNombreRegion());
    }

    @Test
    public void testDelete() {
        doNothing().when(regionRepository).deleteById(1L);
        regionServices.delete(1L);
        verify(regionRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testFindByIdRegion() {
        when(regionRepository.findByIdRegion(1L)).thenReturn(createRegion());
        Region encontrada = regionServices.findByIdRegion(1L);
        assertNotNull(encontrada);
        assertEquals(1L, encontrada.getIdRegion());
    }
}
