package com.Quda.Backend.Controladores;

import com.Quda.Backend.Entidades.Category;
import com.Quda.Backend.Entidades.Supplier;
import com.Quda.Backend.Servicio.ServicioProveedor;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Proveedor")
@AllArgsConstructor
public class ProveedorControlador {

    private final ServicioProveedor servicioProveedor;

    @Transactional
    @GetMapping("/{id}")
    public HttpEntity<HttpStatus> darProveedor(@PathVariable("id") Integer id){
        Supplier buscada = null;
        try{
            buscada = servicioProveedor.buscarProveedor(id).get();
        }
        catch (RuntimeException e){
            System.out.println(e.getMessage());
        }
        return new HttpEntity(buscada);
    }

    @GetMapping
    public HttpEntity<List<Category>> darTodosLosProveedores(){
        return new HttpEntity(servicioProveedor.listarProveedores());
    }

    @PostMapping
    public HttpEntity<HttpStatus> crearProveedor(@RequestBody Supplier proveedor){
        servicioProveedor.crearProveedor(proveedor);
        return new HttpEntity<>(HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    public HttpEntity eliminarProveedor(@PathVariable("id") Integer id){
        servicioProveedor.eliminarProveedor(id);
        return new HttpEntity(HttpStatus.OK) ;
    }

    @PutMapping("/{id}")
    public HttpEntity editarProveedor(@RequestBody Supplier proveedor, @PathVariable ("id") String id){
        return new HttpEntity(servicioProveedor.editarProveedor(proveedor,id));
    }


}
