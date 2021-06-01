package com.Quda.Backend.Controladores;


import com.Quda.Backend.Entidades.Product;
import com.Quda.Backend.Servicio.ServicioProducto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("/Productos")
public class ProductoControlador {

    private final ServicioProducto servicioProducto;

    // CRUD   =========================================================================================
    @PostMapping
    public HttpEntity<Product> crearProducto(@Valid @RequestBody Product product){
        return new ResponseEntity(servicioProducto.crearProducto(product).get(),HttpStatus.CREATED);
    }
    @GetMapping("/{id}")
    public HttpEntity<Product> buscarProducto(@PathVariable("id") Integer id){
        return new ResponseEntity(servicioProducto.buscarProducto(id).get(),HttpStatus.OK);
    }
    @PutMapping
    public HttpEntity<HttpStatus> actualizarProducto(@Valid @RequestBody Product product){
        servicioProducto.editarProducto(product).get();
        return new ResponseEntity(HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public HttpEntity<HttpStatus> eliminarProducto(@PathVariable("id") Integer id){
        servicioProducto.eliminarProducto(id);
        return new ResponseEntity(HttpStatus.OK);
    }
    // LISTAR   =======================================================================================

    @GetMapping("/all")
    public ResponseEntity<List<Product>> listarTodos(){
        return new ResponseEntity(servicioProducto.listarProductos(), HttpStatus.ACCEPTED);
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
