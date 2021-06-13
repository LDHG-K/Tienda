package com.Quda.Backend.Controladores.RespuestasDTO.FacturaControlador;

import com.Quda.Backend.Entidades.Bill;
import com.Quda.Backend.Entidades.BillsProduct;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class FacturaCompletaDTO {
    private Bill factura;
    private List<BillsProduct> listaProductos;
}
