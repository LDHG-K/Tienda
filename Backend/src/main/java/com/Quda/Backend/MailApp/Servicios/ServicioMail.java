package com.Quda.Backend.MailApp.Servicios;

import lombok.AllArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

@Service
@AllArgsConstructor
public class ServicioMail {

    private JavaMailSender javaMailSender;
    //private PdfServicio pdfServicio;
    //https://www.youtube.com/watch?v=njPaIwdx4yw

    @Transactional
    public void enviarMensaje(String correo, String asunto, String mensaje) throws MessagingException, UnsupportedEncodingException, FileNotFoundException {
        enviarMensajeConArchivoAdjuntoPDFEncriptado(correo,asunto,mensaje);
    }

    public void enviarMensajeSimple(String correo, String asunto, String mensaje) {

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom("quda.maquillaje@gmail.com");
        msg.setTo(correo);
        msg.setSubject(asunto);
        msg.setText(mensaje);
        javaMailSender.send(msg);

    }

    public void enviarMensajeConHtml(String correo, String asunto, String mensaje) throws MessagingException, UnsupportedEncodingException {

        MimeMessage mensajeEditado = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mensajeEditado);

        helper.setFrom("quda.maquillaje@gmail.com","Equipo de ventas");
        helper.setTo(correo);
        helper.setSubject(asunto);
        helper.setText("<H2>"+mensaje+"</H2>",true);


        javaMailSender.send(mensajeEditado);


    }

    public void enviarMensajeHtmlEImagen(String correo, String asunto, String mensaje) throws MessagingException, UnsupportedEncodingException {

        MimeMessage mensajeEditado = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mensajeEditado,true);

        helper.setFrom("quda.maquillaje@gmail.com","Equipo de ventas");
        helper.setTo(correo);
        helper.setSubject(asunto);
        helper.setText("<H2>"+mensaje+"</H2>" +"<hr><img src='cid:logoImage'/>",true);

        ClassPathResource resource = new ClassPathResource("/static/noname.png");
        helper.addInline("logoImage",resource);

        javaMailSender.send(mensajeEditado);
    }

    public void enviarMensajeConArchivoAdjunto(String correo, String asunto, String mensaje) throws MessagingException, UnsupportedEncodingException {
        MimeMessage mensajeEditado = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mensajeEditado,true);

        helper.setFrom("quda.maquillaje@gmail.com","Equipo de ventas");
        helper.setTo(correo);
        helper.setSubject(asunto);
        helper.setText("<H2>"+mensaje+"</H2>",true);

        helper.addAttachment("Test.pdf",
                new ClassPathResource("static/"+asunto+".pdf"));

        javaMailSender.send(mensajeEditado);
        System.out.println("Enviado");
    }

    public void enviarMensajeConArchivoAdjuntoPDF(String correo, String asunto, String mensaje) throws MessagingException, UnsupportedEncodingException, FileNotFoundException {

        MimeMessage mensajeEditado = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mensajeEditado,true);

        helper.setFrom("quda.maquillaje@gmail.com","Equipo de ventas");
        helper.setTo(correo);
        helper.setSubject(asunto);
        helper.setText("<H2>"+mensaje+"</H2>",true);

        //pdfServicio.exportarPdf(asunto,"30000",false);

        helper.addAttachment("JeissonTest.pdf",new ClassPathResource("static/"+asunto+".pdf"));

        javaMailSender.send(mensajeEditado);

    }

    //https://knpcode.com/java-programs/password-protected-pdf-using-openpdf-java/
    public void enviarMensajeConArchivoAdjuntoPDFEncriptado(String correo, String asunto, String mensaje)
            throws MessagingException, UnsupportedEncodingException, FileNotFoundException {

        MimeMessage mensajeEditado = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mensajeEditado,true);

        helper.setFrom("quda.maquillaje@gmail.com","Equipo de ventas");
        helper.setTo(correo);
        helper.setSubject(asunto);
        helper.setText("<H2>"+mensaje+"</H2>",true);

        //pdfServicio.exportarPdf(asunto,"30000",true);
        helper.addAttachment(asunto+".pdf",new ClassPathResource("static/"+asunto+".pdf"));
        javaMailSender.send(mensajeEditado);
    }




}
