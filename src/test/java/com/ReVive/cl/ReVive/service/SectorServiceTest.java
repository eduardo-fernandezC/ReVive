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
import com.ReVive.cl.ReVive.model.Sector;
import com.ReVive.cl.ReVive.repository.SectorRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class SectorServiceTest {

    @Autowired
    private SectorServices sectorServices;

    @MockBean
    private SectorRepository sectorRepository;

    private Sector createSector() {
        return new Sector(1L, "Sector1", "Direccion1", new Comuna());
    }

    @Test
    public void testFindAll() {
        when(sectorRepository.findAll()).thenReturn(List.of(createSector()));
        List<Sector> lista = sectorServices.findAll();
        assertNotNull(lista);
        assertEquals(1, lista.size());
    }

    @Test
    public void testSave() {
        Sector s = new Sector(null, "Sector Nuevo", "Direccion Nueva", new Comuna());
        Sector guardado = new Sector(1L, "Sector Nuevo", "Direccion Nueva", new Comuna());
        when(sectorRepository.save(s)).thenReturn(guardado);
        Sector resultado = sectorServices.save(s);
        assertNotNull(resultado);
        assertEquals("Sector Nuevo", resultado.getNombreSector());
    }

    @Test
    public void testUpdate() {
        Sector existente = createSector();
        Sector nueva = new Sector(1L, "Sector2", "Direccion2", new Comuna());
        when(sectorRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(sectorRepository.save(any())).thenReturn(nueva);
        Sector resultado = sectorServices.update(1L, nueva);
        assertNotNull(resultado);
        assertEquals("Sector2", resultado.getNombreSector());
    }

    @Test
    public void testPatch() {
        Sector existente = createSector();
        Sector patch = new Sector();
        patch.setNombreSector("Sector Patched");

        when(sectorRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(sectorRepository.save(any())).thenReturn(existente);

        Sector resultado = sectorServices.patch(1L, patch);
        assertNotNull(resultado);
        assertEquals("Sector Patched", resultado.getNombreSector());
    }

    @Test
    public void testDelete() {
        doNothing().when(sectorRepository).deleteById(1L);
        sectorServices.delete(1L);
        verify(sectorRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testFindByIdSector() {
        when(sectorRepository.findByIdSector(1L)).thenReturn(createSector());
        Sector resultado = sectorServices.findByIdSector(1L);
        assertNotNull(resultado);
        assertEquals(1L, resultado.getIdSector());
    }
}
