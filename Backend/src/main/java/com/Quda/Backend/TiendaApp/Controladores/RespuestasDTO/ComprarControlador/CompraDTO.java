package com.Quda.Backend.Controladores.RespuestasDTO.ComprarControlador;

import com.Quda.Backend.Entidades.Bill;
import lombok.Data;

import java.util.HashMap;

@Data
public class CompraDTO {
    private HashMap<Integer,Integer> productos;
    private Bill factura;
}
