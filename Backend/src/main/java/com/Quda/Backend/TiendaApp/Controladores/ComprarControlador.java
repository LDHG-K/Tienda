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
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/Comprar")
@AllArgsConstructor
public class ComprarControlador {

    private final ServicioCompras servicioCompra;

    @PostMapping("/{usuario}")
    public HttpEntity<HttpStatus> crearFactura(@Valid @RequestBody CompraDTO input, @PathVariable ("usuario") String usuario){
        HttpStatus status = HttpStatus.ACCEPTED;
        try {
            servicioCompra.registrarCompra(input.getProductos(),usuario,input.getFactura());
        }catch (RuntimeException e){
            System.out.println(e.getMessage());
            status = HttpStatus.BAD_REQUEST;
        }

        return new HttpEntity<>(status);
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
