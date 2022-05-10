package com.Quda.Backend.LoginApp.RegisterToken;

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

        TokenConfirmacion tokenAConfirmar =null;
        try{
             tokenAConfirmar = jpaToken.buscarToken(token);
        }
        catch (RuntimeException e){
            throw e;
        }
        LocalDateTime tiempoValidacion =LocalDateTime.now();
        tokenAConfirmar.setConfirmedAt(tiempoValidacion);

            if (LocalDateTime.now().isAfter(tokenAConfirmar.getExpired())){
             throw new RuntimeException("CONFIRMACION EXPIRADA");
                }

            return tokenAConfirmar;
    }

}
