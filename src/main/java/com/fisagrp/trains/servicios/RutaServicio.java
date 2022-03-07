/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fisagrp.trains.servicios;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fisagrp.trains.excepcion.ArchivoException;
import com.fisagrp.trains.modelo.ParadaModelo;
import com.fisagrp.trains.modelo.RutaModelo;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * RutaServicio.java clase de servicio para administrar las rutas y distancias
 * entre ciudades, esta clase se contiene un patron de diseño singleton.
 *
 * @author jbalcazar
 */
@Service
@Scope("singleton")
public class RutaServicio {

    private final static String RUTAJSON = "src/main/resources/json/Rutas.json";
    private List<ParadaModelo> listaParadas = new ArrayList<>();

    /**
     * Recupera datos de muestra de rutas de un JSON.
     *
     * @return lista de rutas de muestra
     * @throws com.fisagrp.trains.excepcion.ArchivoException
     */
    public List<RutaModelo> getListaRutaMuestra() throws ArchivoException {
        try {
            String json = new String(Files.readAllBytes(Paths.get(RUTAJSON)));
            ObjectMapper mapper = new ObjectMapper();
            List<RutaModelo> listaRuta
                    = mapper.readValue(json, new TypeReference<List<RutaModelo>>() {
                    });

            //llena distancia con las paradas que estan en memoria
            if (!listaParadas.isEmpty()) {
                listaRuta.stream().forEach(ruta -> {
                    ruta.getListaParadas()
                            .stream().forEach(parada -> {
                                parada.setDistancia(
                                        getParada(parada).getDistancia());
                            });
                });
            }
            return listaRuta;
        } catch (IOException ex) {
            Logger.getLogger(InformacionClienteServicio.class.getName()).log(Level.SEVERE, null, ex);
            throw new ArchivoException("Error al leer archivo JSON");
        }
    }

    /**
     * Agrega paradas a la lista en memoria
     *
     * @param listaNueva lista de paradas
     */
    public void agregaParadas(List<ParadaModelo> listaNueva) {
        listaParadas.addAll(listaNueva.stream()
                .filter(paradaNueva -> !listaParadas.stream()
                .anyMatch(parada -> parada.isParadaIgual(paradaNueva)))
                .collect(Collectors.toList()));
    }

    /**
     * Genera una lista con varias rutas desde la lista de paradas en memoria,
     * uniendo las paradas para que contenta una ruta con ciudadInicial A y una
     * ciudadFinal B
     *
     * @param ciudadInicial Ciudad desde
     * @param ciudadFinal Ciudad hasta
     * @return lista de rutas generadas
     */
    public List<RutaModelo> getListaRutasGenerar(String ciudadInicial, String ciudadFinal) {

        List<RutaModelo> listaParadaGenerada = new ArrayList<>();

        if (isExisteParadaInicia(ciudadInicial, null) && isExisteParadaFinaliza(ciudadFinal)) {

            getParadasIniciaCiudad(ciudadInicial).stream()
                    .forEach(parada -> {
                        RutaModelo ruta = new RutaModelo();
                        ruta.setCiudades(parada.getCiudades());

                        if (!parada.isParadaIgual(ciudadInicial, ciudadFinal)) {

                            String ciudadAnterior = parada.getCiudadInicial();
                            String ciudadUltima = parada.getCiudadFinal();

                            while (ruta.getCiudades() != null && !ruta.getUltimaParada().equals(ciudadFinal)) {

                                if (isExisteParadaInicia(ciudadUltima, ciudadAnterior)) {
                                    ParadaModelo paradaInicia = getParadaInicia(ciudadUltima, ciudadAnterior);
                                    ciudadAnterior = ciudadUltima;
                                    ciudadUltima = paradaInicia.getCiudadFinal();
                                    ruta.setCiudades(ruta.getCiudades() + ciudadUltima);
                                } else {
                                    ruta.setCiudades(null);
                                }
                            }
                        }
                        if (ruta.getCiudades() != null && !listaParadaGenerada
                                .stream().anyMatch(r -> r.getCiudades().equals(ruta.getCiudades()))) {
                            listaParadaGenerada.add(ruta);
                        }
                    });

            //LLena las distancia entre rutas
            listaParadaGenerada.stream().forEach(ruta -> {
                ruta.getListaParadas()
                        .stream().forEach(parada -> {
                            parada.setDistancia(
                                    getParada(parada).getDistancia());
                        });
            });
        }
        return listaParadaGenerada;
    }

    /**
     * Valida si existe una parada que inicie con una ciudad A y como opcional
     * que no finalice en una ciudad B
     *
     * @param ciudadInicial Ciudad con la que debe incial
     * @param ciudadFinal Ciudad con la que no debe finalizar
     * @return bandera true/false
     */
    private boolean isExisteParadaInicia(String ciudadInicial, String ciudadFinal) {
        return listaParadas.stream()
                .anyMatch(parada -> (parada.getCiudadInicial().equals(ciudadInicial))
                && (ciudadFinal == null || !parada.getCiudadFinal().equals(ciudadFinal)));
    }

    /**
     * Valida si existe una parada que finalice en una ciudad A
     *
     * @param ciudadFinal Ciudad con la que debe finalizar la paradaS
     * @return bandera true/false
     */
    private boolean isExisteParadaFinaliza(String ciudadFinal) {
        return listaParadas.stream()
                .anyMatch(parada -> parada.getCiudadFinal().equals(ciudadFinal));
    }

    /**
     * Obtiene parada de la lista de paradas en memoria
     *
     * @param parada parada que se desea optener
     * @return parada existente en la lista en memoria
     */
    public ParadaModelo getParada(ParadaModelo parada) {
        return listaParadas.stream()
                .filter(paradaMemoria -> paradaMemoria.isParadaIgual(parada))
                .findFirst().orElse(new ParadaModelo());
    }

    /**
     * Obtiene todas las paradas de la lista en memoria que contengan como
     * ciudadInicial A
     *
     * @param ciudad ciudad inicial A
     * @return lista de paradas que inicien en la ciudad A
     */
    private List<ParadaModelo> getParadasIniciaCiudad(String ciudad) {
        return listaParadas.stream()
                .filter(parada -> parada.getCiudadInicial().equals(ciudad))
                .collect(Collectors.toList());
    }

    /**
     * Obtiene una parada de la lista en memoria que inicio con la ciudad A y
     * como opcional que no finalice en una ciudad B
     *
     * @param ciudadInicia, ciudad inicial A
     * @param ciudadFinal Ciudad con la que no debe finalizar
     * @return parada que inicien en la ciudad A
     */
    private ParadaModelo getParadaInicia(String ciudadInicial, String ciudadFinal) {
        return listaParadas.stream()
                .filter(parada -> (parada.getCiudadInicial().equals(ciudadInicial))
                && (ciudadFinal == null || !parada.getCiudadFinal().equals(ciudadFinal)))
                .findFirst().orElse(null);
    }

}
