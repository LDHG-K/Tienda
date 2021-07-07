package com.Quda.Backend.TiendaApp.Controladores;
import com.Quda.Backend.LoginApp.Token.ServicioToken;
import com.Quda.Backend.TiendaApp.Entidad.User;
import com.Quda.Backend.TiendaApp.Servicio.ServicioUsuario;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/Usuarios")
@AllArgsConstructor
public class UsuarioControlador {

    private final ServicioUsuario servicioUsuario;

    // ==============================================================================

    @GetMapping ("{id}")
    public ResponseEntity<User> darUsuario (@PathVariable("id") String id){
        HttpStatus status = HttpStatus.NOT_FOUND;
        Optional<User> usuario =servicioUsuario.buscarUsuario(id);
        if (!usuario.isEmpty()){
            status= HttpStatus.FOUND;
        }
        return new ResponseEntity(usuario.get(), status);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> eliminarUsuario (@PathVariable("id") String id){
        HttpStatus status = HttpStatus.BAD_REQUEST;
        Optional<User> usuario =servicioUsuario.eliminarUsuario(id);
        if (!usuario.isEmpty()){
            status= HttpStatus.OK;
        }
        return new ResponseEntity(status);
    }
    @PutMapping ("/{id}")
    public ResponseEntity<HttpStatus> editarUsuarios (@Valid @RequestBody User user, @PathVariable ("id") String id){
        HttpStatus status = HttpStatus.OK;
        try {
            Optional<User> usuario =servicioUsuario.editarUsuario(user,id);
        }catch (RuntimeException e){
            System.out.println(e.getMessage());
            status= HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity(status);
    }
    // LOGIN / REGISTRO =======================================================================
    @PostMapping("Registro")
    public ResponseEntity<HttpHeaders> crearCuenta (@Valid @RequestBody User usuario){
        HttpStatus status = HttpStatus.BAD_REQUEST;
        try {
            if (!servicioUsuario.crearUsuario(usuario).isEmpty()){
                status= HttpStatus.CREATED;
            }
        }
        catch (RuntimeException e){ System.out.println(e.getMessage()); }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Result", "/Usuarios/"+status);
        return new ResponseEntity(headers, status);
    }

    @GetMapping("Registro/{token}")
    public ResponseEntity<HttpStatus> validarCuenta(@PathVariable("token") String token){
        HttpStatus status = HttpStatus.OK;

        try {
            servicioUsuario.desbloquearUsuario(token);
        }
        catch (RuntimeException e){
            System.out.println(e.getMessage());
            status = HttpStatus.BAD_REQUEST;
        }

        return new ResponseEntity<>(status);
    }


    // Validacion =============================================================================

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
