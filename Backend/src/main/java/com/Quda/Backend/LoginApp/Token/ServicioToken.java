package com.Quda.Backend.LoginApp.Token;

import antlr.Token;
import com.Quda.Backend.TiendaApp.Servicio.ServicioUsuario;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class ServicioToken {

    private final JpaToken jpaToken;


    public void guardarToken(TokenConfirmacion token){
        jpaToken.save(token);
    }

    public TokenConfirmacion validarToken(String token){
        LocalDateTime tiempoValidacion =LocalDateTime.now();
        TokenConfirmacion tokenAConfirmar =null;
        try{
             tokenAConfirmar = jpaToken.buscarToken(token);
        }
        catch (RuntimeException e){
            throw e;
        }

        tokenAConfirmar.setConfirmedAt(tiempoValidacion);

            if (LocalDateTime.now().isAfter(tokenAConfirmar.getExpired())){
             throw new RuntimeException("CONFIRMACION EXPIRADA");
                }

            return tokenAConfirmar;
    }

}
