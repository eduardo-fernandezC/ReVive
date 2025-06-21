package com.ReVive.cl.ReVive.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import com.ReVive.cl.ReVive.model.Sucursal;
import com.ReVive.cl.ReVive.model.Usuarios;
import com.ReVive.cl.ReVive.model.Ventas;
import com.ReVive.cl.ReVive.repository.VentasRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class VentasServiceTest {

    @Autowired
    private VentasServices ventasServices;

    @MockBean
    private VentasRepository ventasRepository;

    private Ventas createVenta() {
        return new Ventas(1L, Date.valueOf("2025-01-01"), 1000, new Usuarios(), new Sucursal());
    }

    @Test
    public void testFindAll() {
        when(ventasRepository.findAll()).thenReturn(List.of(createVenta()));
        List<Ventas> lista = ventasServices.findAll();
        assertNotNull(lista);
        assertEquals(1, lista.size());
    }

    @Test
    public void testFindByIdVentas() {
        when(ventasRepository.findById(1L)).thenReturn(Optional.of(createVenta()));
        Ventas encontrada = ventasServices.findByIdVentas(1L);
        assertNotNull(encontrada);
        assertEquals(1L, encontrada.getIdVenta());
    }

    @Test
    public void testSave() {
        Ventas venta = createVenta();
        when(ventasRepository.save(venta)).thenReturn(venta);
        Ventas guardada = ventasServices.save(venta);
        assertNotNull(guardada);
        assertEquals(1000, guardada.getTotalVenta());
    }

    @Test
    public void testUpdate() {
        Ventas existente = createVenta();
        Ventas nueva = new Ventas(1L, Date.valueOf("2025-02-02"), 2000, new Usuarios(), new Sucursal());

        when(ventasRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(ventasRepository.save(any(Ventas.class))).thenReturn(nueva);

        Ventas actualizada = ventasServices.update(1L, nueva);

        assertNotNull(actualizada);
        assertEquals(2000, actualizada.getTotalVenta());
        assertEquals(Date.valueOf("2025-02-02"), actualizada.getFechaVenta());
    }

    @Test
    public void testPatch() {
        Ventas existente = createVenta();
        Ventas patchData = new Ventas();
        patchData.setTotalVenta(3000);

        when(ventasRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(ventasRepository.save(any(Ventas.class))).thenReturn(existente);

        Ventas resultado = ventasServices.patch(1L, patchData);

        assertNotNull(resultado);
        assertEquals(3000, resultado.getTotalVenta());
    }

    @Test
    public void testDelete() {
        Ventas venta = createVenta();

        when(ventasRepository.findById(1L)).thenReturn(Optional.of(venta));
        doNothing().when(ventasRepository).deleteById(1L);

        ventasServices.delete(1L);

        verify(ventasRepository, times(1)).findById(1L);
        verify(ventasRepository, times(1)).deleteById(1L);
    }


    @Test
    public void testFindByfechaVentas() {
        Date fecha = Date.valueOf("2025-01-01");
        when(ventasRepository.findByfechaVenta(fecha)).thenReturn(List.of(createVenta()));
        List<Ventas> encontrados = ventasServices.findByfechaVentas(fecha);
        assertNotNull(encontrados);
        assertEquals(1, encontrados.size());
        assertEquals(fecha, encontrados.get(0).getFechaVenta());
    }

    @Test
    public void testFindByUsuarioAndSucursal() {
        Ventas venta = createVenta();

        when(ventasRepository.findByUsuario_NombreUsuarioAndSucursal_RazonSocialSucursal("Juan", "Sucursal A"))
            .thenReturn(List.of(venta));

        List<Ventas> resultado = ventasServices.findByUsuarioAndSucursal("Juan", "Sucursal A");

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(ventasRepository, times(1))
            .findByUsuario_NombreUsuarioAndSucursal_RazonSocialSucursal("Juan", "Sucursal A");
    }

    @Test
    public void testBuscarVentasPorSucursalYCategoria() {
        Ventas venta = createVenta();

        when(ventasRepository.buscarVentasPorSucursalYCategoria("Sucursal A", "Bebidas"))
            .thenReturn(List.of(venta));

        List<Ventas> resultado = ventasServices.buscarVentasPorSucursalYCategoria("Sucursal A", "Bebidas");

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(ventasRepository, times(1))
            .buscarVentasPorSucursalYCategoria("Sucursal A", "Bebidas");
    }

}
