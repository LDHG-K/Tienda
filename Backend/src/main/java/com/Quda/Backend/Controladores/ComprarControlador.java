package com.Quda.Backend.Controladores;

import com.Quda.Backend.Controladores.RespuestasDTO.ComprarControlador.CompraDTO;
import com.Quda.Backend.Entidades.Bill;
import com.Quda.Backend.Servicio.ServicioCompras;
import com.Quda.Backend.Servicio.ServicioFactura;
import com.Quda.Backend.Servicio.ServicioProducto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/Comprar")
@AllArgsConstructor
public class ComprarControlador {

    private final ServicioCompras servicioCompra;

    @PostMapping("/{usuario}")
    public HttpEntity<HttpStatus> crearFactura(@RequestBody CompraDTO input, @PathVariable ("usuario") String usuario){
        HttpStatus status = HttpStatus.ACCEPTED;
        try {
            servicioCompra.registrarCompra(input.getProductos(),usuario,input.getFactura());
        }catch (RuntimeException e){
            System.out.println(e.getMessage());
            status = HttpStatus.BAD_REQUEST;
        }

        return new HttpEntity<>(status);
    }

}
