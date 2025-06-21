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

import com.ReVive.cl.ReVive.model.DetalleVenta;
import com.ReVive.cl.ReVive.model.Producto;
import com.ReVive.cl.ReVive.model.Ventas;
import com.ReVive.cl.ReVive.repository.DetalleVentaRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class DetalleVentaServiceTest {

    @Autowired
    private DetalleVentaServices detalleVentaServices;

    @MockBean
    private DetalleVentaRepository detalleVentaRepository;

    private DetalleVenta createDetalleVenta() {
        Ventas venta = new Ventas(1L, Date.valueOf("2025-01-01"), 1000, null, null);
        Producto producto = new Producto(1L, "Producto Test", "Descripción", 500, 5, null, null);
        return new DetalleVenta(1L, 2, 500, 1000, venta, producto);
    }

    @Test
    public void testFindAll() {
        when(detalleVentaRepository.findAll()).thenReturn(List.of(createDetalleVenta()));
        List<DetalleVenta> lista = detalleVentaServices.findAll();
        assertNotNull(lista);
        assertEquals(1, lista.size());
    }

    @Test
    public void testFindById() {
        when(detalleVentaRepository.findById(1L)).thenReturn(Optional.of(createDetalleVenta()));
        DetalleVenta encontrado = detalleVentaServices.findById(1L);
        assertNotNull(encontrado);
        assertEquals(1L, encontrado.getIdDetalleVenta());
    }

    @Test
    public void testSave() {
        DetalleVenta dv = createDetalleVenta();
        when(detalleVentaRepository.save(dv)).thenReturn(dv);
        DetalleVenta guardado = detalleVentaServices.save(dv);
        assertNotNull(guardado);
        assertEquals(1000, guardado.getSubtotal());
    }

    @Test
    public void testUpdate() {
        DetalleVenta existente = createDetalleVenta();
        DetalleVenta nuevo = new DetalleVenta(1L, 3, 600, 1800, existente.getVentas(), existente.getProducto());

        when(detalleVentaRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(detalleVentaRepository.save(any(DetalleVenta.class))).thenReturn(nuevo);

        DetalleVenta actualizado = detalleVentaServices.update(1L, nuevo);
        assertNotNull(actualizado);
        assertEquals(3, actualizado.getCantidad());
        assertEquals(600, actualizado.getPrecioUnitario());
        assertEquals(1800, actualizado.getSubtotal());
    }

    @Test
    public void testPatch() {
        DetalleVenta existente = createDetalleVenta();
        DetalleVenta patchData = new DetalleVenta();
        patchData.setCantidad(4);
        patchData.setSubtotal(2000);

        when(detalleVentaRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(detalleVentaRepository.save(any(DetalleVenta.class))).thenReturn(existente);

        DetalleVenta resultado = detalleVentaServices.patch(1L, patchData);
        assertNotNull(resultado);
        assertEquals(4, resultado.getCantidad());
        assertEquals(2000, resultado.getSubtotal());
    }

    @Test
    public void testDelete() {
        doNothing().when(detalleVentaRepository).deleteById(1L);
        detalleVentaServices.delete(1L);
        verify(detalleVentaRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testFindByVentas() {
        DetalleVenta dv = createDetalleVenta();
        when(detalleVentaRepository.findByVentas(dv.getVentas())).thenReturn(List.of(dv));
        List<DetalleVenta> encontrados = detalleVentaServices.findByVentas(dv.getVentas());
        assertNotNull(encontrados);
        assertEquals(1, encontrados.size());
    }

    @Test
    public void testFindByProducto() {
        DetalleVenta dv = createDetalleVenta();
        when(detalleVentaRepository.findByProducto(dv.getProducto())).thenReturn(List.of(dv));
        List<DetalleVenta> encontrados = detalleVentaServices.findByProducto(dv.getProducto());
        assertNotNull(encontrados);
        assertEquals(1, encontrados.size());
    }
    
    @Test
    public void testBuscarDetallePorUsuarioYCategoria() {
        DetalleVenta dv = createDetalleVenta();

        when(detalleVentaRepository.buscarDetallePorUsuarioYCategoria("usuarioTest", "categoriaTest"))
            .thenReturn(List.of(dv));

        List<DetalleVenta> resultado = detalleVentaServices.buscarDetallePorUsuarioYCategoria("usuarioTest", "categoriaTest");

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(1000, resultado.get(0).getSubtotal());
    }

}
