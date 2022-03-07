package com.fisagrp.trains.controlador;

import com.fisagrp.trains.excepcion.ArchivoException;
import com.fisagrp.trains.excepcion.RutaException;
import com.fisagrp.trains.modelo.ParadaModelo;
import com.fisagrp.trains.servicios.InformacionClienteServicio;
import com.fisagrp.trains.servicios.RutaServicio;
import io.swagger.v3.oas.annotations.Operation;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * EmpleadoController.java clase rest para API Train
 *
 * @author jbalcazar
 */
@RestController
@RequestMapping("/train")
public class TrainController {

    @Autowired
    RutaServicio rutasServicio;

    @Autowired
    InformacionClienteServicio informacionClienteServicio;

    @GetMapping("/Test")
    public String test() {
        Logger.getLogger(TrainController.class.getName()).log(Level.SEVERE, "Test");
        return "Test";
    }

    @Operation(summary = "Ingresar nuevas distancias de rutas del tren")
    @PutMapping("/IngresarDistancia")
    public List<String> IngresarDistanias(@RequestBody List<ParadaModelo> listaParadas) {
        rutasServicio.agregaParadas(listaParadas);
        List<String> resultados = new ArrayList<>();
        try {
            resultados = informacionClienteServicio.procesarInformacionCliente();
        } catch (RutaException | ArchivoException ex) {
            resultados.add(ex.getMessage());
        }
        return resultados;
    }

    @Operation(summary = "Lista informacion de rutas del tren")
    @GetMapping("/InformacionRuta")
    public List<String> ListarInformacion() {
        List<String> resultados = new ArrayList<>();
        try {
            resultados = informacionClienteServicio.procesarInformacionCliente();
        } catch (RutaException | ArchivoException ex) {
            resultados.add(ex.getMessage());
        }

        return resultados;
    }

}
