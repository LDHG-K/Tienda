package com.Quda.Backend.MailApp.Controladores;

import com.Quda.Backend.MailApp.Correos.CorreoCompra;
import com.Quda.Backend.MailApp.Correos.CorreoRegistro;
import com.Quda.Backend.TiendaApp.Entidad.Bill;
import com.Quda.Backend.TiendaApp.Entidad.BillsProduct;
import com.Quda.Backend.TiendaApp.Entidad.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.List;

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
    public void correoCompra (List<BillsProduct> detalles, BigDecimal total, User usuario, Bill factura )
            throws MessagingException, UnsupportedEncodingException
    {
        correoCompra.correoNotificacionCompra(detalles,total,usuario,factura);
    }


}
