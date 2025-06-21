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

import com.ReVive.cl.ReVive.model.Camiones;
import com.ReVive.cl.ReVive.model.Sector;
import com.ReVive.cl.ReVive.model.Sucursal;
import com.ReVive.cl.ReVive.repository.CamionesRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class CamionesServiceTest {

    @Autowired
    private CamionesServices camionesServices;

    @MockBean
    private CamionesRepository camionesRepository;

    private Camiones createCamion() {
        return new Camiones(1L, "ABC1234", new Sector(), new Sucursal());
    }

    @Test
    public void testFindAll() {
        when(camionesRepository.findAll()).thenReturn(List.of(createCamion()));
        List<Camiones> camiones = camionesServices.findAll();
        assertNotNull(camiones);
        assertEquals(1, camiones.size());
    }

    @Test
    public void testFindById() {
        when(camionesRepository.findById(1L)).thenReturn(Optional.of(createCamion()));
        Camiones camion = camionesServices.findById(1L);
        assertNotNull(camion);
        assertEquals("ABC1234", camion.getPatenteCamion());
    }

    @Test
    public void testSave() {
        Camiones camion = createCamion();
        when(camionesRepository.save(camion)).thenReturn(camion);
        Camiones savedCamion = camionesServices.save(camion);
        assertNotNull(savedCamion);
        assertEquals("ABC1234", savedCamion.getPatenteCamion());
    }

    @Test
    public void testUpdate() {
        Camiones existing = createCamion();
        Camiones updated = new Camiones(1L, "XYZ0000", new Sector(), new Sucursal());

        when(camionesRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(camionesRepository.save(any(Camiones.class))).thenReturn(updated);

        Camiones result = camionesServices.update(1L, updated);
        assertNotNull(result);
        assertEquals("XYZ0000", result.getPatenteCamion());
    }

    @Test
    public void testPatch() {
        Camiones existing = createCamion();
        Camiones patchData = new Camiones();
        patchData.setPatenteCamion("ZZZ9999");

        when(camionesRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(camionesRepository.save(any(Camiones.class))).thenReturn(existing);

        Camiones result = camionesServices.patch(1L, patchData);
        assertNotNull(result);
        assertEquals("ZZZ9999", result.getPatenteCamion());
    }

    @Test
    public void testDeleteById() {
        doNothing().when(camionesRepository).deleteById(1L);
        camionesServices.delete(1L);
        verify(camionesRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testBuscarPorPatente() {
        Camiones camion = createCamion();
        when(camionesRepository.buscarPorPatente("ABC1234")).thenReturn(camion);

        Camiones result = camionesServices.buscarPorPatente("ABC1234");
        assertNotNull(result);
        assertEquals("ABC1234", result.getPatenteCamion());
    }

    @Test
    public void testBuscarPorSucursalYComuna() {
        Camiones camion = createCamion();
        when(camionesRepository.buscarCamionesPorSucursalYComuna("SucursalX", "ComunaY"))
            .thenReturn(List.of(camion));

        List<Camiones> resultado = camionesServices.buscarPorSucursalYComuna("SucursalX", "ComunaY");

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("ABC1234", resultado.get(0).getPatenteCamion());
    }

}
