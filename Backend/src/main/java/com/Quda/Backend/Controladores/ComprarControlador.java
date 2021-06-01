package com.Quda.Backend.Controladores;

import com.Quda.Backend.Entidades.Bill;
import com.Quda.Backend.Servicio.ServicioCompras;
import com.Quda.Backend.Servicio.ServicioFactura;
import com.Quda.Backend.Servicio.ServicioProducto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/Comprar")
@AllArgsConstructor
public class ComprarControlador {

    private final ServicioCompras servicioCompra;
    private final ServicioProducto servicioProducto;

    @PostMapping
    public HttpEntity<HttpStatus> crearFactura(@RequestBody HashMap productos){


        //servicioCompra.registrarCompra();

        return null;
    }


}
