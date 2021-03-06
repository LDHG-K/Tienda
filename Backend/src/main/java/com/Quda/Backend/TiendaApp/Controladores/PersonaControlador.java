package com.Quda.Backend.TiendaApp.Controladores;


import com.Quda.Backend.TiendaApp.Dominio.DTOS.Persona;
import com.Quda.Backend.TiendaApp.Entidad.Person;
import com.Quda.Backend.TiendaApp.Servicio.ServicioPersona;
import com.Quda.Backend.TiendaApp.Servicio.ServicioUsuario;
import lombok.AllArgsConstructor;
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

@RestController
@RequestMapping("/Personas")
@AllArgsConstructor
public class PersonaControlador {

    private final ServicioPersona servicioPersona;
    private final ServicioUsuario servicioUsuario;
    //------------------------------!Pendiente!-----------------------------------------------------------
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> eliminarPersona(@PathVariable ("id") Integer id){
       return null;
    }
    //----------------------------------------------------------------------------------------------------

    @PutMapping ("/{id}")
    public ResponseEntity<HttpStatus> editarPersona (@Valid @RequestBody Persona person, @PathVariable ("id") String id){
        HttpStatus status = HttpStatus.OK;

        Optional<Person> persona = null;
        try {
            persona = servicioPersona.editarPersona(servicioUsuario.personaDTOToPersonaEntity(person) , id);
        }
        catch (RuntimeException e){
            System.out.println("No se logró editar la persona :"+e.getMessage());
            status = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity(status);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Persona> darPersona(@PathVariable ("id") String id){
        HttpStatus status = HttpStatus.OK;
        Optional<Person> persona = null;

        try{
            persona = servicioPersona.buscarPersona(id);
        }
        catch (RuntimeException e){
            System.out.println(e.getMessage());
            status = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity(servicioUsuario.personaEntityToPersonaDTO(persona.get()),HttpStatus.ACCEPTED);
    }

    @GetMapping("/Lista/{estado}")
    public ResponseEntity<List<Persona>> darPersonasPorEstado(@PathVariable ("estado") Integer estado){
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
