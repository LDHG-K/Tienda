package com.Quda.Backend.TiendaApp.Controladores;


import com.Quda.Backend.TiendaApp.Entidad.Product;
import com.Quda.Backend.TiendaApp.Servicio.ServicioProducto;
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
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/Productos")
public class ProductoControlador {

    private final ServicioProducto servicioProducto;

    // CRUD   =========================================================================================
    @PostMapping
    public ResponseEntity<Product> crearProducto(@Valid @RequestBody Product product){
        Optional<Product> response = null;
        HttpStatus status = HttpStatus.CREATED;
        try{
            response = servicioProducto.crearProducto(product);
        }catch (RuntimeException e){
            System.out.println("Error al crear el producto por : "+e.getMessage());
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity(response,status);
    }
    @GetMapping("/{id}")
    public HttpEntity<Product> buscarProducto(@PathVariable("id") Integer id){
        Optional<Product> response = null;
        HttpStatus status = HttpStatus.FOUND;
        try {
            response = servicioProducto.buscarProducto(id);
        }catch (RuntimeException e) {
            System.out.println(e.getMessage());
            status = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity(response,status);
    }
    @PutMapping
    public ResponseEntity<HttpStatus> actualizarProducto(@Valid @RequestBody Product product){
        Optional<Product> response = null;
        HttpStatus status = HttpStatus.OK;
        try{
            response = servicioProducto.editarProducto(product);
        }catch (RuntimeException e){
            System.out.println(e.getMessage());
            status = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity(status);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> eliminarProducto(@PathVariable("id") Integer id){
        HttpStatus status = HttpStatus.OK;
        try {
            servicioProducto.eliminarProducto(id);
        }
        catch (RuntimeException e){
            System.out.println(e.getMessage());
            status = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity(status);
    }
    // STOCK ==========================================================================================

    @PostMapping("/{id}/{cantidad}")
    public HttpEntity<HttpStatus> agregarStockAUnProducto(@PathVariable Integer id, @PathVariable Integer cantidad){
        HttpStatus status = HttpStatus.OK;
        try{
            if(cantidad<=0){
                throw new RuntimeException("No se pueden añadir cantidades vacias o negativas");
            }
            servicioProducto.agregarStockAlProducto(id,cantidad);
        }catch (RuntimeException e){
            System.out.println(e.getMessage());
            status = HttpStatus.BAD_REQUEST;
        }
        return new HttpEntity<>(status);
    }

    // LISTAR   =======================================================================================

    @GetMapping("/all")
    public ResponseEntity<List<Product>> listarTodos(){
        return new ResponseEntity(servicioProducto.listarProductos(), HttpStatus.ACCEPTED);
    }
    @GetMapping("/all-Disponibles")
    public ResponseEntity<List<Product>> listarProductosDisponibles(){
        return new ResponseEntity<>(servicioProducto.listarProductosDisponibles(),HttpStatus.ACCEPTED);
    }
    @GetMapping("/Categoria/{id}")
    public ResponseEntity<List<Product>> listarPorCategoria(@PathVariable ("id") Integer id){
        return new ResponseEntity(servicioProducto.listarProductosCategoria(id), HttpStatus.ACCEPTED);
    }
    @GetMapping("/Proveedor/{id}")
    public ResponseEntity<List<Product>> listarPorProveedor(@PathVariable ("id") Integer id){
        return new ResponseEntity(servicioProducto.listarProductosProveedor(id), HttpStatus.ACCEPTED);
    }
    @GetMapping("/Objetivo/{id}")
    public ResponseEntity<List<Product>> listarPorObjetivo(@PathVariable ("id") Integer id){
        return new ResponseEntity(servicioProducto.listarProductosObjetivo(id), HttpStatus.ACCEPTED);
    }

    // VALIDACION =====================================================================================

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
