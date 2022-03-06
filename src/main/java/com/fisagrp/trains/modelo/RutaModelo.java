/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fisagrp.trains.modelo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * RutaModelo.java clase entidad que contiene las rutas de la compañia de tren
 *
 * @author jbalcazar
 */
public class RutaModelo {

    private int id;

    private String ciudades;

    private List<ParadaModelo> listaParadas;

    public RutaModelo() {
    }

    public RutaModelo(String ciudades) {
        this.ciudades = ciudades;
        generarListaParada();
    }

    private void generarListaParada() {
        listaParadas = new ArrayList<>();
        StringBuilder ciudadParada = new StringBuilder();
        getListaCiudades().forEach(ciudad -> {
            ciudadParada.append(ciudad);
            if (ciudadParada.length() == 2) {
                ParadaModelo parada = new ParadaModelo(ciudadParada.substring(0, 1),
                        ciudadParada.substring(1, 2), 0);
                listaParadas.add(parada);
                ciudadParada.setLength(0);
                ciudadParada.append(ciudad);
            }
        });
    }

    public List<String> getListaCiudades() {
        return new ArrayList<>(Arrays.asList(this.ciudades.split("")));
    }

    public String getPrimerParada() {
        return Character.toString(ciudades.charAt(0));
    }

    public String getUltimaParada() {
        return Character.toString(ciudades.charAt(getTotalParadas()));
    }

    public int getTotalDistancia() {
        return listaParadas.stream().mapToInt(parada -> parada.getDistancia()).sum();
    }

    public int getTotalCiudades() {
        return ciudades.length();
    }

    public int getTotalParadas() {
        return getTotalCiudades() - 1;
    }

    @Override
    public String toString() {
        return "{" + "id=" + id + ", ciudades=" + ciudades
                + ", paradas= " + getTotalParadas()
                + ", listaParadas=" + listaParadas + '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCiudades() {
        return ciudades;
    }

    public void setCiudades(String ciudades) {
        this.ciudades = ciudades;
        generarListaParada();
    }

    public List<ParadaModelo> getListaParadas() {
        return listaParadas;
    }

}
