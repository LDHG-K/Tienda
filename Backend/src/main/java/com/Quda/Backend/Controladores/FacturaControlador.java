package com.Quda.Backend.Controladores;

import com.Quda.Backend.Controladores.RespuestasDTO.FacturaControlador.FacturaCompletaDTO;
import com.Quda.Backend.Entidades.Bill;
import com.Quda.Backend.Servicio.ServicioDetalleFactura;
import com.Quda.Backend.Servicio.ServicioFactura;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping ("/Factura")
@AllArgsConstructor
public class FacturaControlador  {

    private final ServicioFactura servicioFactura;
    private final ServicioDetalleFactura servicioDetalleFactura;

    @GetMapping("/{id}")
    public HttpEntity<Bill> buscarFactura(@PathVariable Integer id){

        return new HttpEntity<>(servicioFactura.buscarFactura(id).get());
    }

    @GetMapping("/{id}/Detalles")
    public HttpEntity<FacturaCompletaDTO> buscarFacturaConDetalles(@PathVariable("id") Integer id){

        FacturaCompletaDTO respuesta =FacturaCompletaDTO.builder()
                .factura(servicioFactura.buscarFactura(id).get())
                .listaProductos(servicioDetalleFactura.buscarDetallesDeUnaFactura(id))
                .build();

        return new HttpEntity<>(respuesta);
    }


}
