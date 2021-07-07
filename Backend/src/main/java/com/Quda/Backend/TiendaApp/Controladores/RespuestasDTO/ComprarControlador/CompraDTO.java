package com.Quda.Backend.TiendaApp.Controladores.RespuestasDTO.ComprarControlador;

import com.Quda.Backend.TiendaApp.Entidad.Bill;
import lombok.Data;

import java.util.HashMap;

@Data
public class CompraDTO {
    private HashMap<Integer,Integer> productos;
    private Bill factura;
}
