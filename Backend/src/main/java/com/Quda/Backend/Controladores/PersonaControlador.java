package com.Quda.Backend.Controladores;


import com.Quda.Backend.Entidades.Person;
import com.Quda.Backend.Entidades.User;
import com.Quda.Backend.Servicio.ServicioPersona;
import com.sun.net.httpserver.Headers;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.ServerRequest;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/Personas")
@AllArgsConstructor
public class PersonaControlador {

    private final ServicioPersona servicioPersona;

    @PutMapping ("/{id}")
    public ResponseEntity<HttpStatus> editarPersona (@Valid @RequestBody Person person, @PathVariable ("id") String id){
        HttpStatus status = HttpStatus.OK;
        servicioPersona.editarPersona(person , id);
        return new ResponseEntity(status);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Person> darPersona(@PathVariable ("id") Integer id){
        return new ResponseEntity(servicioPersona.buscarPersona(id).get(),HttpStatus.ACCEPTED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> eliminarPersona(@PathVariable ("id") Integer id){
        servicioPersona.eliminarPersona(id);
        return new ResponseEntity(HttpStatus.OK);
    }




}
