package com.fisagrp.trains;

import com.fisagrp.trains.excepcion.ArchivoException;
import com.fisagrp.trains.modelo.RutaModelo;
import com.fisagrp.trains.servicios.RutaServicio;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.util.Assert;

@SpringBootTest
class RutaServicioTests {

    @MockBean
    RutaServicio rutaServicio;

    RutaModelo RUTA = new RutaModelo(1, "CDC");
    RutaModelo RUTA2 = new RutaModelo(1, "ACDCB");

    @Test
    void testLeerArchivoJson() throws ArchivoException {
        Mockito.when(rutaServicio.getListaRutaMuestra()).thenReturn(Arrays.asList(RUTA));
        Assert.notEmpty(rutaServicio.getListaRutaMuestra(), "Error al leer archivo JSON");
    }

    @Test
    void testRutasGenerar() {
        Mockito.when(rutaServicio.getListaRutasGenerar("A", "B")).thenReturn(Arrays.asList(RUTA2));
        Assert.notEmpty(rutaServicio.getListaRutasGenerar("A", "B"), "No genero rutas");
    }
    
}
