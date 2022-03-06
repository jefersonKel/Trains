package com.fisagrp.trains.excepcion;

/**
 * UsuarioException.java Clase de Exception para errores de la logica de
 * empleados
 *
 * @author jbalcazar
 */
public class ArchivoException extends Exception {

    private static final long serialVersionUID = 1L;

    public ArchivoException() {
        super();
    }

    public ArchivoException(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return getMessage();
    }

}
