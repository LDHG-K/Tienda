package com.Quda.Backend.TiendaApp.Dominio.DTOS.RespuestasDTO.FacturaControlador;

import com.Quda.Backend.TiendaApp.Entidad.Bill;
import com.Quda.Backend.TiendaApp.Entidad.BillsProduct;
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
