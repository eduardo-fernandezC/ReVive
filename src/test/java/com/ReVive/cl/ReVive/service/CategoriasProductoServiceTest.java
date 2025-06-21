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
import com.ReVive.cl.ReVive.repository.CategoriasProductoRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class CategoriasProductoServiceTest {

    @Autowired
    private CategoriasProductoServices categoriasProductoServices;

    @MockBean
    private CategoriasProductoRepository categoriasProductoRepository;

    private CategoriasProducto createCategoria() {
        return new CategoriasProducto(1L, "Desinfectantes");
    }

    @Test
    public void testFindAll() {
        when(categoriasProductoRepository.findAll()).thenReturn(List.of(createCategoria()));
        List<CategoriasProducto> lista = categoriasProductoServices.findAll();
        assertNotNull(lista);
        assertEquals(1, lista.size());
    }

    @Test
    public void testSave() {
        CategoriasProducto categoria = createCategoria();
        when(categoriasProductoRepository.save(categoria)).thenReturn(categoria);
        CategoriasProducto guardada = categoriasProductoServices.save(categoria);
        assertNotNull(guardada);
        assertEquals("Desinfectantes", guardada.getNombreCatesProducto());
    }

    @Test
    public void testUpdate() {
        CategoriasProducto existente = createCategoria();
        CategoriasProducto nueva = new CategoriasProducto(1L, "Actualizada");
        when(categoriasProductoRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(categoriasProductoRepository.save(any())).thenReturn(nueva);
        CategoriasProducto actualizada = categoriasProductoServices.update(1L, nueva);
        assertNotNull(actualizada);
        assertEquals("Actualizada", actualizada.getNombreCatesProducto());
    }

    @Test
    public void testPatch() {
        CategoriasProducto existente = createCategoria();
        CategoriasProducto patchData = new CategoriasProducto();
        patchData.setNombreCatesProducto("Parcheada");
        when(categoriasProductoRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(categoriasProductoRepository.save(any())).thenReturn(existente);
        CategoriasProducto resultado = categoriasProductoServices.patch(1L, patchData);
        assertNotNull(resultado);
        assertEquals("Parcheada", resultado.getNombreCatesProducto());
    }

    @Test
    public void testDelete() {
        CategoriasProducto categoria = createCategoria();
        when(categoriasProductoRepository.findByIdCatesProducto(1L)).thenReturn(categoria);
        doNothing().when(categoriasProductoRepository).deleteByIdCatesProducto(1L);
        categoriasProductoServices.delete(1L);
        verify(categoriasProductoRepository, times(1)).findByIdCatesProducto(1L);
        verify(categoriasProductoRepository, times(1)).deleteByIdCatesProducto(1L);
    }




    @Test
    public void testCategoriaProductoId() {
        CategoriasProducto categoria = createCategoria();
        when(categoriasProductoRepository.findByIdCatesProducto(1L)).thenReturn(categoria);
        CategoriasProducto encontrada = categoriasProductoServices.CategoriaProductoId(1L);
        assertNotNull(encontrada);
        assertEquals(1L, encontrada.getIdCatesProducto());
    }

    @Test
    public void testFindByNombre() {
        when(categoriasProductoRepository.findByNombreCatesProducto("Desinfectantes")).thenReturn(createCategoria());
        CategoriasProducto encontrada = categoriasProductoServices.findByNombre("Desinfectantes");
        assertNotNull(encontrada);
        assertEquals("Desinfectantes", encontrada.getNombreCatesProducto());
    }
}
