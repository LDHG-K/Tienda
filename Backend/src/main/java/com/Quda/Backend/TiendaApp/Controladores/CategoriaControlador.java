package com.Quda.Backend.TiendaApp.Controladores;

import com.Quda.Backend.TiendaApp.Entidad.Category;
import com.Quda.Backend.TiendaApp.Servicio.ServicioCategoria;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/Categoria")
@AllArgsConstructor
public class CategoriaControlador {

    private final ServicioCategoria servicioCategoria;

    @Transactional
    @GetMapping("/{id}")
    public HttpEntity<HttpStatus> darCategoria(@PathVariable ("id") Integer id){
        Category buscada = null;
        try{
            buscada =servicioCategoria.buscarCategoria(id).get();
        }
        catch (RuntimeException e){
            System.out.println(e.getMessage());
        }
        return new HttpEntity(buscada);
    }

    @GetMapping
    public HttpEntity<List<Category>> darTodasLasCategorias(){
        return new HttpEntity(servicioCategoria.listarCategorias());
    }

    @PostMapping
    public HttpEntity<HttpStatus> crearCategoria(@Valid @RequestBody Category categoria){
        servicioCategoria.crearCategoria(categoria);
        return new HttpEntity<>(HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    public HttpEntity eliminarCategoria(@PathVariable("id") Integer id){
        servicioCategoria.eliminarCategoria(id);
        return new HttpEntity(HttpStatus.OK) ;
    }

    @PutMapping("/{id}")
    public HttpEntity editarCategoria(@Valid @RequestBody Category categoria, @PathVariable ("id") String id){
        return new HttpEntity(servicioCategoria.editarCategoria(categoria,id));
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
