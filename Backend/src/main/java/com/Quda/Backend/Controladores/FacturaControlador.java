package com.Quda.Backend.Controladores;

import com.Quda.Backend.Controladores.RespuestasDTO.FacturaControlador.FacturaCompletaDTO;
import com.Quda.Backend.Entidades.Bill;
import com.Quda.Backend.Servicio.ServicioDetalleFactura;
import com.Quda.Backend.Servicio.ServicioFactura;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

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

    //========================================================================================================
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

}
