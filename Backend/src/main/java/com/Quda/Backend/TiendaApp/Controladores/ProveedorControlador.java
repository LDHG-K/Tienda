package com.Quda.Backend.Controladores;

import com.Quda.Backend.Entidades.Category;
import com.Quda.Backend.Entidades.Supplier;
import com.Quda.Backend.Servicio.ServicioProveedor;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/Proveedor")
@AllArgsConstructor
public class ProveedorControlador {

    private final ServicioProveedor servicioProveedor;

    @Transactional
    @GetMapping("/{id}")
    public ResponseEntity<Supplier> darProveedor(@PathVariable("id") Integer id){
        Supplier buscada = null;
        HttpStatus status = HttpStatus.OK;
        try{
            buscada = servicioProveedor.buscarProveedor(id).get();
        }
        catch (RuntimeException e){
            status = HttpStatus.NO_CONTENT;
            System.out.println(e.getMessage());
        }
        return new ResponseEntity(buscada,status);
    }

    @GetMapping
    public ResponseEntity<List<Category>> darTodosLosProveedores(){
        HttpStatus status = HttpStatus.OK;
        List<Supplier> proveedores = servicioProveedor.listarProveedores();
        if (proveedores.isEmpty()){status=HttpStatus.NO_CONTENT;}
        return new ResponseEntity(proveedores,status);
    }

    @PostMapping
    public ResponseEntity<HttpStatus> crearProveedor(@Valid @RequestBody Supplier proveedor){

        HttpStatus status = HttpStatus.OK;
        try {
            servicioProveedor.crearProveedor(proveedor);

        }
        catch (RuntimeException e){
            System.out.println("ERROR /n"+e.getMessage());
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity(status);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity eliminarProveedor( @PathVariable("id") Integer id){
        HttpStatus status = HttpStatus.OK;
        try {
            servicioProveedor.eliminarProveedor(id);
        }
        catch (RuntimeException e){
            System.out.println(e.getMessage());
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity(status) ;
    }

    @PutMapping("/{id}")
    public ResponseEntity editarProveedor(@Valid @RequestBody Supplier proveedor, @PathVariable ("id") String id){
        HttpStatus status = HttpStatus.OK;
        try {
            servicioProveedor.editarProveedor(proveedor,id);
        }
        catch(RuntimeException e){
            System.out.println(e.getMessage());
            status = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity(status);
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
