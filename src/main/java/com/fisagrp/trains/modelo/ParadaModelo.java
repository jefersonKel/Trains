/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fisagrp.trains.modelo;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * ParadaModelo.java clase entidad de una parada entre ciudades
 *
 * @author jbalcazar
 */
public class ParadaModelo {

    private String ciudadInicial;

    private String ciudadFinal;

    private int distancia;

    public ParadaModelo() {
        this.distancia = 0;
    }

    public ParadaModelo(String ciudadInicial, String ciudadFinal, int distancia) {
        this.ciudadInicial = ciudadInicial;
        this.ciudadFinal = ciudadFinal;
        this.distancia = distancia;
    }

    @JsonIgnore
    public String getCiudades() {
        return ciudadInicial + ciudadFinal;
    }

    public boolean isParadaIgual(ParadaModelo parada) {
        return isParadaIgual(parada.ciudadInicial, parada.ciudadFinal);
    }

    public boolean isParadaIgual(String cInicial, String cFinal) {
        return ciudadInicial.equals(cInicial) && ciudadFinal.equals(cFinal);
    }

    @Override
    public String toString() {
        return "ParadaModelo{" + "ciudadInicial=" + ciudadInicial + ", ciudadFinal=" + ciudadFinal + ", distancia=" + distancia + '}';
    }

    public String getCiudadInicial() {
        return ciudadInicial;
    }

    public void setCiudadInicial(String ciudadInicial) {
        this.ciudadInicial = ciudadInicial;
    }

    public String getCiudadFinal() {
        return ciudadFinal;
    }

    public void setCiudadFinal(String ciudadFinal) {
        this.ciudadFinal = ciudadFinal;
    }

    public int getDistancia() {
        return distancia;
    }

    public void setDistancia(int distancia) {
        this.distancia = distancia;
    }
}
