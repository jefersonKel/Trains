package com.fisagrp.trains.enumeracion;

import java.util.Arrays;
import java.util.List;

/**
 * TipoRol.java clase enumeracion para clasificar la informacion que se le va a
 * indicar a los clientes
 *
 * @author jbalcazar
 */
public enum TipoInformacion {

    DISTANCIA("Distancia de la ruta {0} "),
    NUMERO_VIAJES("El número de viajes que comienzan en {0} y terminan en {0} "),
    NUMERO_RUTAS("El número de rutas diferentes de {0} a {1} con una distancia menor a {2} "),
    LONGITUD_CORTA("La longitud de la ruta más corta entre {0} a {1} ");

    private final String descripcion;
    public static final List<TipoInformacion> ENUMS = Arrays.asList(TipoInformacion.values());

    private TipoInformacion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

}
