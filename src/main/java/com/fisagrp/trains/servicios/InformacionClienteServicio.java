/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fisagrp.trains.servicios;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fisagrp.trains.enumeracion.TipoConteoParada;
import com.fisagrp.trains.enumeracion.TipoInformacion;
import com.fisagrp.trains.excepcion.ArchivoException;
import com.fisagrp.trains.excepcion.RutaException;
import com.fisagrp.trains.modelo.InformacionClienteModelo;
import com.fisagrp.trains.modelo.RutaModelo;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * InformacionClienteServicio.java clase de servicio para logica de calculo para
 * informacion de clientes, por defecto existen 4 tipos de informacion: @see
 * com.fisagrp.trains.enumeracion.TipoInformacion
 *
 * @author jbalcazar
 */
@Service
public class InformacionClienteServicio {

    @Autowired
    RutaServicio rutaServicio;

    private final static String RUTAJSON = "src/main/resources/json/InformacionCliente.json";

    /**
     * Recupera datos de muestra de un JSON que contiene la lista informacion
     * que se debe mostrar a los usuario del tren
     *
     *
     * @return lista de informacions
     * @throws com.fisagrp.trains.excepcion.ArchivoException
     */
    public List<InformacionClienteModelo> getListaInformacionCliente() throws ArchivoException {
        try {
            String json = new String(Files.readAllBytes(Paths.get(RUTAJSON)));
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(json, new TypeReference<List<InformacionClienteModelo>>() {
            });
        } catch (IOException ex) {
            Logger.getLogger(InformacionClienteServicio.class.getName()).log(Level.SEVERE, null, ex);
            throw new ArchivoException("Error al leer archivo JSON");
        }
    }

    /**
     * Procesa la informacion que se debe mostrar al usuario con sus repectivos
     * resultados
     *
     * @return lista de resultados sobre la informacion
     * @throws com.fisagrp.trains.excepcion.RutaException
     * @throws com.fisagrp.trains.excepcion.ArchivoException
     */
    public List<String> procesarInformacionCliente() throws RutaException, ArchivoException {

        List<InformacionClienteModelo> listaInformacion = getListaInformacionCliente();
        List<RutaModelo> listaRutaMuestra = rutaServicio.getListaRutaMuestra();

        listaInformacion.stream().forEach(informacion -> {

            //LLenar distancia de la lista de paradas en memoria
            informacion.getRuta().getListaParadas()
                    .stream().forEach(parada -> {
                        parada.setDistancia(
                                rutaServicio.getParada(parada).getDistancia());
                    });

            Logger.getLogger(InformacionClienteServicio.class.getName())
                    .log(Level.INFO, "Procesar informacion: {0}", informacion);

            Logger.getLogger(InformacionClienteServicio.class.getName())
                    .log(Level.INFO, "Ruta: {0}", informacion.getRuta());

            switch (informacion.getTipo()) {
                case DISTANCIA:

                    //Verifica si existe alguna parda sin distancia ingresada
                    if (informacion.getRuta().getListaParadas().stream()
                            .anyMatch(parada -> parada.getDistancia() == 0)) {
                        informacion.setResultado("NO SUCH ROUTE");
                    } else {
                        //Si todas las parada tienen distancia, calcula la distancia total
                        informacion.setResultado(
                                MessageFormat.format(TipoInformacion.DISTANCIA.getDescripcion(),
                                        new Object[]{informacion.getRuta().getCiudades()})
                                + "Resultado: " + Integer.toString(informacion.getRuta().getTotalDistancia()));
                    }
                    break;

                case NUMERO_VIAJES:

                    Long cantidad = listaRutaMuestra.stream()
                            .filter(ruta -> ruta.getPrimerParada().equals(informacion.getRuta().getPrimerParada())
                            && ruta.getUltimaParada().equals(informacion.getRuta().getUltimaParada()))
                            .filter(ruta
                                    -> (informacion.getTipoConteo().equals(TipoConteoParada.MAXIMO)
                            && ruta.getTotalParadas() <= informacion.getValor())
                            || (informacion.getTipoConteo().equals(TipoConteoParada.IGUAL)
                            && informacion.getValor() == ruta.getTotalParadas())
                            ).count();

                    informacion.setResultado(
                            MessageFormat.format(TipoInformacion.NUMERO_VIAJES.getDescripcion(),
                                    new Object[]{informacion.getRuta().getPrimerParada(),
                                        informacion.getRuta().getUltimaParada()})
                            + "Resultado: " + Long.toString(cantidad));

                    break;

                case NUMERO_RUTAS:

                    List<RutaModelo> listaRutaMuestraFiltro
                            = listaRutaMuestra.stream()
                                    .filter(ruta -> ruta.getPrimerParada().equals(informacion.getRuta().getPrimerParada())
                                    && ruta.getUltimaParada().equals(informacion.getRuta().getUltimaParada()))
                                    .filter(ruta
                                            -> informacion.getTipoConteo().equals(TipoConteoParada.MENOR)
                                    ? ruta.getTotalDistancia() < informacion.getValor() : false)
                                    .collect(Collectors.toList());

                    informacion.setResultado(MessageFormat.format(TipoInformacion.NUMERO_RUTAS.getDescripcion(),
                            new Object[]{informacion.getRuta().getPrimerParada(),
                                informacion.getRuta().getUltimaParada(), informacion.getValor()})
                            + " Resultado: " + listaRutaMuestraFiltro.size());

                    break;

                case LONGITUD_CORTA:

                    List<RutaModelo> listaRutaGenerada = rutaServicio.getListaRutasGenerar(informacion.getRuta().getPrimerParada(),
                            informacion.getRuta().getUltimaParada());
                    if (listaRutaGenerada == null || listaRutaGenerada.isEmpty()) {
                        informacion.setResultado("NO SUCH ROUTE");
                    } else {
                        RutaModelo ruta = rutaServicio.getListaRutasGenerar(
                                informacion.getRuta().getPrimerParada(),
                                informacion.getRuta().getUltimaParada()).stream()
                                .min((ruta1, ruta2) -> Integer.compare(ruta1.getTotalDistancia(), ruta2.getTotalDistancia())).get();

                        informacion.setResultado(MessageFormat.format(TipoInformacion.LONGITUD_CORTA.getDescripcion(),
                                new Object[]{informacion.getRuta().getPrimerParada(),
                                    informacion.getRuta().getUltimaParada(), informacion.getValor()})
                                + "Resultado: Ruta " + ruta.getCiudades() + " distancia " + ruta.getTotalDistancia());
                    }

                    break;

                default:
                    break;
            }
            Logger.getLogger(InformacionClienteServicio.class.getName())
                    .log(Level.INFO, "Resultado:{0}", informacion.getResultado());
        });
        return listaInformacion.stream()
                .map(informacion -> "#" + informacion.getId() + " " + informacion.getResultado())
                .collect(Collectors.toList());
    }
}
