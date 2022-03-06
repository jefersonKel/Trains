package com.fisagrp.trains.enumeracion;

import java.util.Arrays;
import java.util.List;

/**
 * TipoConteoParada.java clase enumeracion para clasificar los tipos de conteo
 * de paradas
 *
 * @author jbalcazar
 */
public enum TipoConteoParada {

    IGUAL,
    MAXIMO,
    MAYOR,
    MENOR;

    public static final List<TipoConteoParada> ENUMS = Arrays.asList(TipoConteoParada.values());

}
