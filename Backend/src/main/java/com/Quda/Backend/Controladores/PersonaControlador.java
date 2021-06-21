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
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.ServerRequest;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    public ResponseEntity<Person> darPersona(@PathVariable ("id") String id){
        return new ResponseEntity(servicioPersona.buscarPersona(id).get(),HttpStatus.ACCEPTED);
    }

    @GetMapping("/{estado}")
    public ResponseEntity<List<Person>> darPersonasPorEstado(@PathVariable ("estado") Integer estado){
        return new ResponseEntity(servicioPersona.enlistarPersonas(estado),HttpStatus.ACCEPTED);
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
