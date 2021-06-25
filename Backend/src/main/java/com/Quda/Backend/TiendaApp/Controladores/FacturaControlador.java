package com.Quda.Backend.Controladores;

import com.Quda.Backend.Controladores.RespuestasDTO.FacturaControlador.FacturaCompletaDTO;
import com.Quda.Backend.Entidades.Bill;
import com.Quda.Backend.Servicio.ServicioDetalleFactura;
import com.Quda.Backend.Servicio.ServicioFactura;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping ("/Factura")
@AllArgsConstructor
public class FacturaControlador  {

    private final ServicioFactura servicioFactura;
    private final ServicioDetalleFactura servicioDetalleFactura;

    @GetMapping("/{Usuario}/Facturas")
    public ResponseEntity<HttpStatus> buscarFacturasDelUsuario(@PathVariable("Usuario") String id){

        List<Bill> facturas = null;
        HttpStatus status = HttpStatus.FOUND;
        try {
            facturas = servicioFactura.buscarFacturasDelUsuario(id);
        }
        catch (RuntimeException e){
            System.out.println(e.getMessage());
            status = HttpStatus.FOUND;
        }
        return new ResponseEntity(facturas,status);
    }


    @GetMapping("/{id}")
    public ResponseEntity<HttpStatus> buscarFactura(@PathVariable Integer id){

        HttpStatus status = HttpStatus.FOUND;
        Bill factura = null;
        try {
            factura = servicioFactura.buscarFactura(id).get();
        }
        catch (RuntimeException e){
            System.out.println(e.getMessage());
            status = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity(factura,status);
    }

    @GetMapping("/{id}/Detalles")
    public ResponseEntity<FacturaCompletaDTO> buscarFacturaConDetalles(@PathVariable("id") Integer id){
        HttpStatus status = HttpStatus.FOUND;
        FacturaCompletaDTO respuesta = null;
        try{
            respuesta = FacturaCompletaDTO.builder()
                            .factura(servicioFactura.buscarFactura(id).get())
                            .listaProductos(servicioDetalleFactura.buscarDetallesDeUnaFactura(id))
                            .build();
        }
        catch (RuntimeException e){
            System.out.println( e.getMessage());
            status = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity(respuesta,status);
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
