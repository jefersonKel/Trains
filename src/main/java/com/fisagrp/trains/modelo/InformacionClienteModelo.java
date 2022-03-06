/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fisagrp.trains.modelo;

/**
 * InformacionClienteModelo.java clase entidad que contiene la informacion para
 * cliente
 *
 * @author jbalcazar
 */
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fisagrp.trains.enumeracion.TipoConteoParada;
import com.fisagrp.trains.enumeracion.TipoInformacion;

/**
 *
 * @author jbalcazar
 */
public class InformacionClienteModelo {

    @JsonProperty("id")
    private int id;

    @JsonProperty("ciudades")
    private String ciudades;

    @JsonProperty("tipo")
    private TipoInformacion tipo;

    @JsonProperty("tipoConteo")
    private TipoConteoParada tipoConteo;

    @JsonProperty("valor")
    private int valor;

    private String resultado;

    private RutaModelo ruta;

    @Override
    public String toString() {
        return "InformacionClienteModelo{" + "id=" + id + ", ciudades=" + ciudades + ", tipo=" + tipo + ", tipoConteo=" + tipoConteo + ", valor=" + valor + '}';
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
        this.ruta = new RutaModelo(ciudades);
    }

    public TipoInformacion getTipo() {
        return tipo;
    }

    public void setTipo(TipoInformacion tipo) {
        this.tipo = tipo;
    }

    public TipoConteoParada getTipoConteo() {
        return tipoConteo;
    }

    public void setTipoConteo(TipoConteoParada tipoConteo) {
        this.tipoConteo = tipoConteo;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public RutaModelo getRuta() {
        return this.ruta;
    }

}
