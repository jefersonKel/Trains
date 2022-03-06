package com.fisagrp.trains.excepcion;

/**
 * UsuarioException.java Clase de Exception para errores de la logica de
 * empleados
 *
 * @author jbalcazar
 */
public class RutaException extends Exception {

    private static final long serialVersionUID = 1L;

    public RutaException() {
        super();
    }

    public RutaException(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return getMessage();
    }

}
