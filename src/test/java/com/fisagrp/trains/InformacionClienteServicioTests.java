package com.fisagrp.trains;

import com.fisagrp.trains.enumeracion.TipoConteoParada;
import com.fisagrp.trains.enumeracion.TipoInformacion;
import com.fisagrp.trains.excepcion.ArchivoException;
import com.fisagrp.trains.modelo.InformacionClienteModelo;
import com.fisagrp.trains.servicios.InformacionClienteServicio;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.util.Assert;

@SpringBootTest
class InformacionClienteServicioTests {

    @MockBean
    InformacionClienteServicio informacionClienteServicio;

    InformacionClienteModelo INFORMACION = new InformacionClienteModelo(1,
            "ABC", TipoInformacion.DISTANCIA, null, 0);

    @Test
    void testLeerArchivoJson() throws ArchivoException {
        Mockito.when(informacionClienteServicio.getListaInformacionCliente()).thenReturn(Arrays.asList(INFORMACION));
        Assert.notEmpty(informacionClienteServicio.getListaInformacionCliente(), "Error al leer archivo JSON");
    }

}
