package com.Quda.Backend.TiendaApp.Controladores;

import com.Quda.Backend.TiendaApp.Dominio.DTOS.Usuario;
import com.Quda.Backend.TiendaApp.Entidad.User;
import com.Quda.Backend.TiendaApp.Servicio.ServicioUsuario;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
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
    public ResponseEntity<Usuario> darUsuario (@PathVariable("id") String id){
        HttpStatus status = HttpStatus.NOT_FOUND;
        Optional<User> usuario =servicioUsuario.buscarUsuario(id);
        if (!usuario.isEmpty()){
            status= HttpStatus.FOUND;
        }
        return new ResponseEntity(servicioUsuario.usuarioEntityToUsuarioDTO(usuario.get()), status);
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
    public ResponseEntity<HttpStatus> editarUsuarios (@Valid @RequestBody Usuario user, @PathVariable ("id") String id){
        HttpStatus status = HttpStatus.OK;

        try {
            Optional<User> usuario =servicioUsuario.editarUsuario(servicioUsuario.usuarioDTOToUsuarioEntiry(user),id);
        }catch (RuntimeException e){
            System.out.println(e.getMessage());
            status= HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity(status);
    }
    // LOGIN / REGISTRO =======================================================================
    @PostMapping("Registro")
    public ResponseEntity<HttpHeaders> crearCuenta (@Valid @RequestBody Usuario usuario){
        HttpStatus status = HttpStatus.BAD_REQUEST;
        try {
            if (!servicioUsuario.crearUsuario(servicioUsuario.usuarioDTOToUsuarioEntiry(usuario)).isEmpty()){
                status= HttpStatus.CREATED;
            }
        }
        catch (RuntimeException | MessagingException | UnsupportedEncodingException e){ System.out.println(e.getMessage()); }
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
