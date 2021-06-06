package com.Quda.Backend.Controladores;

import com.Quda.Backend.Entidades.Category;
import com.Quda.Backend.Servicio.ServicioCategoria;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public HttpEntity<HttpStatus> crearCategoria(@RequestBody Category categoria){
        servicioCategoria.crearCategoria(categoria);
        return new HttpEntity<>(HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    public HttpEntity eliminarCategoria(@PathVariable("id") Integer id){
        servicioCategoria.eliminarCategoria(id);
        return new HttpEntity(HttpStatus.OK) ;
    }

    @PutMapping("/{id}")
    public HttpEntity editarCategoria(@RequestBody Category categoria, @PathVariable ("id") String id){
        return new HttpEntity(servicioCategoria.editarCategoria(categoria,id));
    }

}
