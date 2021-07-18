package com.Quda.Backend.MailApp.Controladores;

import com.Quda.Backend.MailApp.Correos.CorreoCompra;
import com.Quda.Backend.MailApp.Correos.CorreoRegistro;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

@Service
@AllArgsConstructor
public class MailController {

    private final CorreoCompra correoCompra;
    private final CorreoRegistro correoRegistro;

    @Transactional
    public void correoRegistro (String correo, String asunto, String nombre, String token)
            throws MessagingException, UnsupportedEncodingException
    {
        correoRegistro.correoNotificacionRegistro(correo,asunto,nombre,token);
    }

    @Transactional
    public void correoCompra (String correo, String asunto, String nombre, String token)
            throws MessagingException, UnsupportedEncodingException
    {
        correoCompra.correoNotificacionCompra(correo,asunto,nombre,token);
    }


}
