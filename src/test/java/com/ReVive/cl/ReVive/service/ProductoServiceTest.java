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

import com.ReVive.cl.ReVive.model.CategoriasProducto;
import com.ReVive.cl.ReVive.model.Producto;
import com.ReVive.cl.ReVive.model.Sucursal;
import com.ReVive.cl.ReVive.repository.ProductoRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class ProductoServiceTest {

    @Autowired
    private ProductoServices productoServices;

    @MockBean
    private ProductoRepository productoRepository;

    private Producto createProducto() {
        CategoriasProducto categoria = new CategoriasProducto(1L, "Categoría Test");
        Sucursal sucursal = new Sucursal(1L, "Sucursal Test", "Dirección Test");
        return new Producto(1L, "Producto Test", "Descripción Test", 1000, 10, categoria, sucursal);
    }

    @Test
    public void testFindAll() {
        when(productoRepository.findAll()).thenReturn(List.of(createProducto()));
        List<Producto> lista = productoServices.findAll();
        assertNotNull(lista);
        assertEquals(1, lista.size());
        verify(productoRepository, times(1)).findAll();
    }

    @Test
    public void testSave() {
        Producto producto = createProducto();
        when(productoRepository.save(producto)).thenReturn(producto);
        Producto guardado = productoServices.save(producto);
        assertNotNull(guardado);
        assertEquals("Producto Test", guardado.getNombreProducto());
        verify(productoRepository, times(1)).save(producto);
    }

    @Test
    public void testUpdate() {
        Producto existente = createProducto();
        Producto nuevo = new Producto(1L, "Producto Actualizado", "Descripción", 1500, 15, existente.getCategoria(), existente.getSucursal());
        
        when(productoRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(productoRepository.save(any())).thenReturn(nuevo);
        
        Producto actualizado = productoServices.update(1L, nuevo);
        assertNotNull(actualizado);
        assertEquals("Producto Actualizado", actualizado.getNombreProducto());
        verify(productoRepository, times(1)).findById(1L);
        verify(productoRepository, times(1)).save(any(Producto.class));
    }

    @Test
    public void testPatch() {
        Producto existente = createProducto();
        Producto patchData = new Producto();
        patchData.setNombreProducto("Producto Parcheado");
        
        when(productoRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(productoRepository.save(any())).thenReturn(existente);
        
        Producto resultado = productoServices.patch(1L, patchData);
        assertNotNull(resultado);
        assertEquals("Producto Parcheado", resultado.getNombreProducto());
        verify(productoRepository, times(1)).findById(1L);
        verify(productoRepository, times(1)).save(any(Producto.class));
    }

    @Test
    public void testDelete() {
        doNothing().when(productoRepository).deleteById(1L);
        productoServices.delete(1L);
        verify(productoRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testFindByNombreProducto() {
        when(productoRepository.findByNombreProducto("Producto Test")).thenReturn(createProducto());
        Producto encontrada = productoServices.findByNombreProducto("Producto Test");
        assertNotNull(encontrada);
        assertEquals("Producto Test", encontrada.getNombreProducto());
        verify(productoRepository, times(1)).findByNombreProducto("Producto Test");
    }

    @Test
    public void testFindByNombreProductoAndCategoria() {
        Producto producto = createProducto();

        when(productoRepository.findByNombreProductoAndCategoria_NombreCatesProducto("Producto Test", "Categoría Test"))
            .thenReturn(List.of(producto));

        List<Producto> resultado = productoServices.findByNombreProductoAndCategoria("Producto Test", "Categoría Test");

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Producto Test", resultado.get(0).getNombreProducto());
        verify(productoRepository, times(1)).findByNombreProductoAndCategoria_NombreCatesProducto("Producto Test", "Categoría Test");
    }

    @Test
    public void testFindProductosByCategoriaAndSucursal() {
        Producto producto = createProducto();

        when(productoRepository.findProductosByCategoriaAndSucursal("Categoría Test", "Sucursal Test"))
            .thenReturn(List.of(producto));

        List<Producto> resultado = productoServices.findProductosByCategoriaAndSucursal("Categoría Test", "Sucursal Test");

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Producto Test", resultado.get(0).getNombreProducto());
        verify(productoRepository, times(1)).findProductosByCategoriaAndSucursal("Categoría Test", "Sucursal Test");
    }

}
