package com.Quda.Backend.MailApp.Correos;

import com.Quda.Backend.TiendaApp.Entidad.*;
import com.Quda.Backend.TiendaApp.Repositorio.JpaCiudad;
import com.Quda.Backend.TiendaApp.Repositorio.JpaDepartamento;
import com.Quda.Backend.TiendaApp.Servicio.ServicioProducto;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class CorreoCompra {

    private final JavaMailSender javaMailSender;
    private final ServicioProducto servicioProducto;
    private final JpaCiudad jpaCiudad;
    private final JpaDepartamento jpaDepartamento;

    public void correoNotificacionCompra(List<BillsProduct> detalles, BigDecimal total, User usuario, Bill factura ) throws MessagingException, UnsupportedEncodingException {



        MimeMessage mensajeEditado = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mensajeEditado);

        helper.setFrom("quda.maquillaje@gmail.com", "Equipo de ventas");
        helper.setTo(usuario.getPerson().getPersonEmail());
        helper.setSubject("Notificacion de compra en Quda");

        //Mensaje parametrizado
        helper.setText(generarMensajeNotificacion(detalles,total,usuario,factura), true);
        javaMailSender.send(mensajeEditado);
    }


    // Crea un correo para ser enviado al generar la factura
    // Correo =====================================================================

    private String generarMensajeNotificacion(List<BillsProduct> detalles, BigDecimal total, User usuario, Bill factura ){

        String detallesHtml = generarHtmlDetalles(detalles);
        String envioHtml = generarHtmlEnvio(factura);
        String totalHtml = generarHtmlTotal(total);

        String nombre = usuario.getPerson().getPersonName();
        Integer idFactura = factura.getBillId();
        String direccion = factura.getBillSendAddres();

        City ciudad = jpaCiudad.getById(factura.getCityId());
        Department departamento = jpaDepartamento.getById(ciudad.getDepartmentId());

        String ciudadDestino = ciudad.getCityName() + "-" + departamento.getDepartmentName();
        String fechaLlegada = generarFechaDeLlegada();

        String mensaje = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "<title></title>\n" +
                "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\n" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
                "<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\" />\n" +
                "<style type=\"text/css\">\n" +
                "/* CLIENT-SPECIFIC STYLES */\n" +
                "body, table, td, a { -webkit-text-size-adjust: 100%; -ms-text-size-adjust: 100%; }\n" +
                "table, td { mso-table-lspace: 0pt; mso-table-rspace: 0pt; }\n" +
                "img { -ms-interpolation-mode: bicubic; }\n" +
                "\n" +
                "/* RESET STYLES */\n" +
                "img { border: 0; height: auto; line-height: 100%; outline: none; text-decoration: none; }\n" +
                "table { border-collapse: collapse !important; }\n" +
                "body { height: 100% !important; margin: 0 !important; padding: 0 !important; width: 100% !important; }\n" +
                "\n" +
                "/* iOS BLUE LINKS */\n" +
                "a[x-apple-data-detectors] {\n" +
                "    color: inherit !important;\n" +
                "    text-decoration: none !important;\n" +
                "    font-size: inherit !important;\n" +
                "    font-family: inherit !important;\n" +
                "    font-weight: inherit !important;\n" +
                "    line-height: inherit !important;\n" +
                "}\n" +
                "\n" +
                "/* MEDIA QUERIES */\n" +
                "@media screen and (max-width: 480px) {\n" +
                "    .mobile-hide {\n" +
                "        display: none !important;\n" +
                "    }\n" +
                "    .mobile-center {\n" +
                "        text-align: center !important;\n" +
                "    }\n" +
                "}\n" +
                "\n" +
                "/* ANDROID CENTER FIX */\n" +
                "div[style*=\"margin: 16px 0;\"] { margin: 0 !important; }\n" +
                "</style>\n" +
                "</head>\n" +
                "<body style=\"margin: 0 !important; padding: 0 !important; background-color: #eeeeee;\" bgcolor=\"#eeeeee\">\n" +
                "\n" +
                "<!-- HIDDEN PREHEADER TEXT -->\n" +
                "<div style=\"display: none; font-size: 1px; color: #fefefe; line-height: 1px; font-family: Open Sans, Helvetica, Arial, sans-serif; max-height: 0px; max-width: 0px; opacity: 0; overflow: hidden;\">\n" +
                "¡Hola "+nombre+" aqui tienes el recibo de tu compra en Q'uda! \n" +
                "</div>\n" +
                "\n" +
                "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n" +
                "    <tr>\n" +
                "        <td align=\"center\" style=\"background-color: #eeeeee;\" bgcolor=\"#eeeeee\">\n" +
                "        <!--[if (gte mso 9)|(IE)]>\n" +
                "        <table align=\"center\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"600\">\n" +
                "        <tr>\n" +
                "        <td align=\"center\" valign=\"top\" width=\"600\">\n" +
                "        <![endif]-->\n" +
                "        <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width:600px;\">\n" +
                "            <tr>\n" +
                "                <td align=\"center\" valign=\"top\" style=\"font-size:0; padding: 0px; \" bgcolor=\"#EFA885\">\n" +
                "                <!--[if (gte mso 9)|(IE)]>\n" +
                "                <table align=\"center\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"600\">\n" +
                "                <tr>\n" +
                "                <td align=\"left\" valign=\"top\" width=\"300\">\n" +
                "                <![endif]-->\n" +
                "                <div style=\"display:inline-block; max-width:50%; min-width:100px; vertical-align:top; width:100%;\">\n" +
                "                    <table align=\"CENTER\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width:100px;\">\n" +
                "                        <tr>\n" +
                "                            <td align=\"left\" valign=\"top\" style=\"font-family: Open Sans, Helvetica, Arial, sans-serif; font-size: 36px; font-weight: 800; line-height: 48px;\" class=\"mobile-center\">\n" +
                "                                <!--\n" +
                "                                <svg width=\"300\" height=\"100\"  \n" +
                "                                id=\"Capa_1\" data-name=\"Capa 1\" xmlns=\"http://www.w3.org/2000/svg\" viewBox=\"0 0 938.4 638.73\"><defs><style>.cls-1{fill:#f8d49b;stroke:#f8d49b;stroke-miterlimit:10;stroke-width:4px;}.cls-2{isolation:isolate;}.cls-3{fill:#fff;}</style></defs><title>LogoSF</title><g id=\"Fuentes\"><rect class=\"cls-1\" x=\"2\" y=\"2\" width=\"300.19\" height=\"634.73\"/><g class=\"cls-2\"><path class=\"cls-3\" d=\"M482.08,277.54q22.76,0,39.67,9.92a93.64,93.64,0,0,1,28.59,26A139.74,139.74,0,0,1,569,349.3a246.32,246.32,0,0,1,10.5,40,313.53,313.53,0,0,1,4.38,38.21q.87,18.09.29,29.76a508.36,508.36,0,0,1-13.13,87.5,553.31,553.31,0,0,1-25.38,79A464.59,464.59,0,0,1,511.25,692,441,441,0,0,1,471,747.75c1.94,1.94,4,4.09,6.13,6.42s4.17,4.47,6.12,6.42A211.41,211.41,0,0,0,516.5,788a156.13,156.13,0,0,0,32.09,16.62q17.5,6.71,33.25,2,.58,8.18-5,12.55a43.46,43.46,0,0,1-12,6.71,76.88,76.88,0,0,1-18.09,2.91,81.1,81.1,0,0,1-28.29-5.54A191.55,191.55,0,0,1,490,809.88a240.76,240.76,0,0,1-26-17.21q-12.25-9.33-20.42-16.33Q417.33,802,392,814.84t-47,12.84q-35.6,0-50.76-15.46T280.82,774q.57-11.09,7-23.33a80,80,0,0,1,17.79-22.46,92.48,92.48,0,0,1,27.13-16.63q15.75-6.41,36.17-6.41a160.31,160.31,0,0,1,35.29,3.5,121,121,0,0,1,30.63,11.66,483,483,0,0,0,35.59-59.21,619.93,619.93,0,0,0,30-68.55,647.42,647.42,0,0,0,21.88-71.76A421.54,421.54,0,0,0,533.42,452q2.33-42-2.33-69.13t-13.42-43.17q-8.75-16-19.25-22.46t-19.25-6.42q-19.85,0-44.05,19.55t-46.38,54.54q-22.17,35-38.5,82.85T329.82,572.15q-.58,24.51,4.67,43.76a99.73,99.73,0,0,0,6.12,16,54.29,54.29,0,0,0,9.63,14,41.71,41.71,0,0,0,14,9.63q8.16,3.49,19.25,2.91-1.17,9.93-7.58,15.17a42.36,42.36,0,0,1-13.42,7.59,55.24,55.24,0,0,1-18.67,2.91q-8.74,0-21.58-7.29t-24.8-23q-12-15.75-19.54-40.55t-4.67-59.8a362.51,362.51,0,0,1,8.46-52.79,402.21,402.21,0,0,1,19-60.09,437.72,437.72,0,0,1,29.46-59.8,267.62,267.62,0,0,1,40-52.21,191.36,191.36,0,0,1,50.76-37Q448.84,277.54,482.08,277.54ZM346.16,787.42q15.15,0,31.79-10.21t33-27.71a52.72,52.72,0,0,0-29.75-9.33A59.87,59.87,0,0,0,361,743.38a51,51,0,0,0-15.46,8.46,46.33,46.33,0,0,0-10.21,11.37,22.68,22.68,0,0,0-3.79,12,10.13,10.13,0,0,0,2.92,8.75C336.82,786.26,340.7,787.42,346.16,787.42Z\" transform=\"translate(-118 -228)\"/></g><g class=\"cls-2\"><path class=\"cls-3\" d=\"M678.55,508.91q-7.77,14.64-7.77,43.61,0,43.62,16.43,69.6,9.56,15.23,22.85,15.23t21.36-14Q741,606,741,575.52q0-38.24-10.75-56.45-4.79-8.67-5.52-12.55c-.5-2.59.14-4.48,1.94-5.68s4.78-1.79,9-1.79q18.21,0,23.89,37.64a123,123,0,0,1,1.8,20.91A146.8,146.8,0,0,1,750.53,612q-15.22,37.35-48.09,37.34-17.33,0-29.57-12.84-23-24.5-23-73.78,0-21.51,3-32.86T657.94,515a34.4,34.4,0,0,1,5.07-6.42,7.46,7.46,0,0,1,6.73-2.39A41.29,41.29,0,0,1,678.55,508.91Z\" transform=\"translate(-118 -228)\"/><path class=\"cls-3\" d=\"M903.17,409.74q-11.35,102.76-11.35,146.51a479.11,479.11,0,0,0,8.06,87.37q-6,6-10.45,6a20.15,20.15,0,0,1-7.47-1.2,211.64,211.64,0,0,1-7.47-48.09q-7.17,11.36-10.6,16.13a182.9,182.9,0,0,1-11.8,14Q836.85,648.41,816,648.4q-15.84,0-22.85-14.78t-7-37.94a183.16,183.16,0,0,1,5.22-44.21A89.72,89.72,0,0,1,809.38,515q12.82-15.37,31.66-15.38A46.22,46.22,0,0,1,862,504.43q9.56,4.79,13.14,12.84.6-8.06,1.19-24.79t1.35-29.72q.74-13,2.54-26.43,3.29-28.68,14.63-28.68A20,20,0,0,1,903.17,409.74ZM868.37,528a23.5,23.5,0,0,0-10-9.71q-6.27-3.14-16.58-3.14t-19.26,11.65q-17.62,23-17.63,66.61a90.42,90.42,0,0,0,4.49,27.93q4.47,13.89,13.14,13.89,12.54,0,24.49-16.43a127.36,127.36,0,0,0,18.52-37.78q6.57-21.36,6.57-38.39A29.08,29.08,0,0,0,868.37,528Z\" transform=\"translate(-118 -228)\"/><path class=\"cls-3\" d=\"M1026.53,563l-1.5,34.36q0,26,6,34.94,3.59,6,9.86,6a20.79,20.79,0,0,0,5.07-.9q6.29-1.79,10.46,1.79-2.1,10.47-20.91,10.46-12.85,0-17-10.76a80.11,80.11,0,0,1-4.78-20.16q-1.5-12.09-2.39-16.58-19.41,37-37.63,45.11a52,52,0,0,1-21.51,4.18q-12.25,0-19-11.8t-6.72-34.5a166,166,0,0,1,7.47-47.64q7.47-24.94,21.81-42.12t31.36-17.17q24.48,0,32.56,20.31Q1026.53,535.79,1026.53,563Zm-73.18-9.7A287.69,287.69,0,0,0,947.07,584,182.75,182.75,0,0,0,945,609.72q0,11.21,4.63,19.27t12.1,8.06q16.13,0,31.06-30.47t14.94-56.75q0-17-9-28.08a19.43,19.43,0,0,0-15.68-7.17q-9.72,0-17.63,11.21T953.35,553.27Z\" transform=\"translate(-118 -228)\"/></g><path class=\"cls-3\" d=\"M588.21,296.82a186,186,0,0,1,18.45,103.51c-.68,6.45,6,12,12,12,7.06,0,11.31-5.53,12-12,4.21-39.74-3.73-80-21.72-115.62-2.93-5.79-11.07-7.44-16.42-4.31-5.88,3.44-7.24,10.61-4.31,16.42Z\" transform=\"translate(-118 -228)\"/></g>\n" +
                "                                </svg>\n" +
                "                                -->\n" +
                "                                    <a href=\"#\" target=\"_blank\">\n" +
                "                                        <img alt=\"\" src=\"https://i.ibb.co/LZFR0Yh/Logo.jpg\"\n" +
                "                                       style=\"display: block; width: 200px; height : 200px; font-family: 'Lato', Helvetica, Arial, sans-serif; color: #ffffff; font-size: 18px;\" border=\"0\">\n" +
                "                                    </a>\n" +
                "                            </td>\n" +
                "                        </tr>\n" +
                "                    </table>\n" +
                "                </div>\n" +
                "                <!--[if (gte mso 9)|(IE)]>\n" +
                "                </td>\n" +
                "                <td align=\"right\" width=\"300\">\n" +
                "                <![endif]-->\n" +
                "            \n" +
                "                <!--[if (gte mso 9)|(IE)]>\n" +
                "                </td>\n" +
                "                </tr>\n" +
                "                </table>\n" +
                "                <![endif]-->\n" +
                "                </td>\n" +
                "            </tr>\n" +
                "            <tr>\n" +
                "                <td align=\"center\" style=\"padding: 35px 35px 20px 35px; background-color: #ffffff;\" bgcolor=\"#ffffff\">\n" +
                "                <!--[if (gte mso 9)|(IE)]>\n" +
                "                <table align=\"center\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"600\">\n" +
                "                <tr>\n" +
                "                <td align=\"center\" valign=\"top\" width=\"600\">\n" +
                "                <![endif]-->\n" +
                "                <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width:600px;\">\n" +
                "                    <tr>\n" +
                "                        <td align=\"center\" style=\"font-family: Open Sans, Helvetica, Arial, sans-serif; font-size: 16px; font-weight: 400; line-height: 24px; padding-top: 25px;\">\n" +
                "                           \n" +
                "                           <!-- <img src=\"hero-image-receipt.png\" width=\"125\" height=\"120\" style=\"display: block; border: 0px;\" /><br>\n" +
                "                           --> \n" +
                "                            <h2 style=\"font-size: 30px; font-weight: 800; line-height: 36px; color: #333333; margin: 0;\">\n" +
                "                                ¡Gracias por tu compra!\n" +
                "                            </h2>\n" +
                "                        </td>\n" +
                "                    </tr>\n" +
                "                    <tr>\n" +
                "                        <td align=\"left\" style=\"font-family: Open Sans, Helvetica, Arial, sans-serif; font-size: 16px; font-weight: 400; line-height: 24px; padding-top: 10px;\">\n" +
                "                            <p style=\"font-size: 16px; font-weight: 400; line-height: 24px; color: #777777;\">\n" +
                "                                Hola "+nombre+", nos aseguraremos que tu paquete llegue sano y salvo, danos tiempo para prepararlo mientras puedes revisar lo que pediste.\n" +
                "                            </p>\n" +
                "                        </td>\n" +
                "                    </tr>\n" +
                "                    <tr>\n" +
                "                        <td align=\"left\" style=\"padding-top: 20px;\">\n" +
                "                            <table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                <tr>\n" +
                "                                    <td width=\"75%\" align=\"left\" bgcolor=\"#E9967A\" style=\"font-family: Open Sans, Helvetica, Arial, sans-serif; font-size: 16px; font-weight: 800; line-height: 24px; padding: 10px;\">\n" +
                "                                        Orden #\n" +
                "                                    </td>\n" +
                "                                    <td width=\"25%\" align=\"left\" bgcolor=\"#E9967A\" style=\"font-family: Open Sans, Helvetica, Arial, sans-serif; font-size: 16px; font-weight: 800; line-height: 24px; padding: 10px;\">\n" +
                "                                        "+idFactura+"\n" +
                "                                    </td>\n" +
                "                                </tr>\n" +
                detallesHtml+
                envioHtml+
                "                            </table>\n" +
                "                        </td>\n" +
                "                    </tr>\n" +
                "                    <tr>\n" +
                "                        <td align=\"left\" style=\"padding-top: 20px;\">\n" +
                "                            <table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"100%\">\n" +
                "                                <tr>\n" +
                totalHtml+

                "                                </tr>\n" +
                "                            </table>\n" +
                "                        </td>\n" +
                "                    </tr>\n" +
                "                </table>\n" +
                "                <!--[if (gte mso 9)|(IE)]>\n" +
                "                </td>\n" +
                "                </tr>\n" +
                "                </table>\n" +
                "                <![endif]-->\n" +
                "                </td>\n" +
                "            </tr>\n" +
                "             <tr>\n" +
                "                <td align=\"center\" height=\"100%\" valign=\"top\" width=\"100%\" style=\"padding: 0 35px 35px 35px; background-color: #ffffff;\" bgcolor=\"#ffffff\">\n" +
                "                <!--[if (gte mso 9)|(IE)]>\n" +
                "                <table align=\"center\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"600\">\n" +
                "                <tr>\n" +
                "                <td align=\"center\" valign=\"top\" width=\"600\">\n" +
                "                <![endif]-->\n" +
                "                <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width:660px;\">\n" +
                "                    <tr>\n" +
                "                        <td align=\"center\" valign=\"top\" style=\"font-size:0;\">\n" +
                "                            <!--[if (gte mso 9)|(IE)]>\n" +
                "                            <table align=\"center\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"600\">\n" +
                "                            <tr>\n" +
                "                            <td align=\"left\" valign=\"top\" width=\"300\">\n" +
                "                            <![endif]-->\n" +
                "                            <div style=\"display:inline-block; max-width:50%; min-width:240px; vertical-align:top; width:100%;\">\n" +
                "\n" +
                "                                <table align=\"left\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width:300px;\">\n" +
                "                                    <tr>\n" +
                "                                        <td align=\"left\" valign=\"top\" style=\"font-family: Open Sans, Helvetica, Arial, sans-serif; font-size: 16px; font-weight: 400; line-height: 24px;\">\n" +
                "                                            <p style=\"font-weight: 800;\">Direccion de envio</p>\n" +
                "                                            <p>"+direccion+"<br>"+ciudadDestino+"</p>\n" +
                "\n" +
                "                                        </td>\n" +
                "                                    </tr>\n" +
                "                                </table>\n" +
                "                            </div>\n" +
                "                            <!--[if (gte mso 9)|(IE)]>\n" +
                "                            </td>\n" +
                "                            <td align=\"left\" valign=\"top\" width=\"300\">\n" +
                "                            <![endif]-->\n" +
                "                            <div style=\"display:inline-block; max-width:50%; min-width:240px; vertical-align:top; width:100%;\">\n" +
                "                                <table align=\"left\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width:300px;\">\n" +
                "                                    <tr>\n" +
                "                                        <td align=\"left\" valign=\"top\" style=\"font-family: Open Sans, Helvetica, Arial, sans-serif; font-size: 16px; font-weight: 400; line-height: 24px;\">\n" +
                "                                            <p style=\"font-weight: 800;\">Fecha estimada de envio</p>\n" +
                "                                            <p>"+fechaLlegada+"</p>\n" +
                "                                        </td>\n" +
                "                                    </tr>\n" +
                "                                </table>\n" +
                "                            </div>\n" +
                "                            <!--[if (gte mso 9)|(IE)]>\n" +
                "                            </td>\n" +
                "                            </tr>\n" +
                "                            </table>\n" +
                "                            <![endif]-->\n" +
                "                        </td>\n" +
                "                    </tr>\n" +
                "                </table>\n" +
                "                <!--[if (gte mso 9)|(IE)]>\n" +
                "                </td>\n" +
                "                </tr>\n" +
                "                </table>\n" +
                "                <![endif]-->\n" +
                "                </td>\n" +
                "            </tr>\n" +
                "            <tr>\n" +
                "                <td align=\"center\" style=\" padding: 35px; background-color: #EFA885;\" bgcolor=\"#1b9ba3\">\n" +
                "                <!--[if (gte mso 9)|(IE)]>\n" +
                "                <table align=\"center\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"600\">\n" +
                "                <tr>\n" +
                "                <td align=\"center\" valign=\"top\" width=\"600\">\n" +
                "                <![endif]-->\n" +
                "                <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width:600px;\">\n" +
                "                    <tr>\n" +
                "                        <td align=\"center\" style=\"font-family: Open Sans, Helvetica, Arial, sans-serif; font-size: 16px; font-weight: 400; line-height: 24px; padding-top: 25px;\">\n" +
                "                            <h2 style=\"font-size: 24px; font-weight: 800; line-height: 30px; color: #ffffff; margin: 0;\">\n" +
                "                                ¿Algo salio mal?\n" +
                "                            </h2>\n" +
                "                        </td>\n" +
                "                    </tr>\n" +
                "                    <tr>\n" +
                "                        <td align=\"center\" style=\"padding: 25px 0 15px 0;\">\n" +
                "                            <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n" +
                "                                <tr>\n" +
                "                                    <td align=\"center\" style=\"border-radius: 5px;\" bgcolor=\"#66b3b7\">\n" +
                "                                      <a href=\"https://wa.me/573202423015\" target=\"_blank\" style=\"font-size: 18px; font-family: Open Sans, Helvetica, Arial, sans-serif; color: #ffffff; text-decoration: none; border-radius: 5px; background-color: #E9967A; padding: 15px 30px; display: block;\">Comunicate ya con nosotros</a>\n" +
                "                                    </td>\n" +
                "                                </tr>\n" +
                "                            </table>\n" +
                "                        </td>\n" +
                "                    </tr>\n" +
                "                </table>\n" +
                "                <!--[if (gte mso 9)|(IE)]>\n" +
                "                </td>\n" +
                "                </tr>\n" +
                "                </table>\n" +
                "                <![endif]-->\n" +
                "                </td>\n" +
                "            </tr>\n" +
                "            <tr>\n" +
                "                <td align=\"center\" style=\"padding: 35px; background-color: #ffffff;\" bgcolor=\"#ffffff\">\n" +
                "                <!--[if (gte mso 9)|(IE)]>\n" +
                "                <table align=\"center\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"600\">\n" +
                "                <tr>\n" +
                "                <td align=\"center\" valign=\"top\" width=\"600\">\n" +
                "                <![endif]-->\n" +
                "                <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width:600px;\">\n" +
                "                   <!-- \n" +
                "                    <tr>\n" +
                "                        <td align=\"center\">\n" +
                "                            <img src=\"logo-footer.png\" width=\"37\" height=\"37\" style=\"display: block; border: 0px;\"/>\n" +
                "                        </td>\n" +
                "                    </tr>\n" +
                "                 -->\n" +
                "                    <tr>\n" +
                "                        <td align=\"center\" style=\"font-family: Open Sans, Helvetica, Arial, sans-serif; font-size: 14px; font-weight: 400; line-height: 24px; padding: 5px 0 10px 0;\">\n" +
                "                            <p style=\"font-size: 14px; font-weight: 800; line-height: 18px; color: #333333;\">\n" +
                "                                Colombia<br>\n" +
                "                                Ibague Tolima <br>\n" +
                "                                730004\n" +
                "                            </p>\n" +
                "                        </td>\n" +
                "                    </tr>\n" +
                "                    <tr>\n" +
                "                        <td align=\"left\" style=\"font-family: Open Sans, Helvetica, Arial, sans-serif; font-size: 14px; font-weight: 400; line-height: 24px;\">\n" +
                "                            <p style=\"font-size: 14px; font-weight: 400; line-height: 20px; color: #777777;\">\n" +
                "                                Si crees que no debiste recibir este mensaje <a href=\"#\" target=\"_blank\" style=\"color: #777777;\">haz click aqui</a>.\n" +
                "                            </p>\n" +
                "                        </td>\n" +
                "                    </tr>\n" +
                "                </table>\n" +
                "                <!--[if (gte mso 9)|(IE)]>\n" +
                "                </td>\n" +
                "                </tr>\n" +
                "                </table>\n" +
                "                <![endif]-->\n" +
                "                </td>\n" +
                "            </tr>\n" +
                "        </table>\n" +
                "        <!--[if (gte mso 9)|(IE)]>\n" +
                "        </td>\n" +
                "        </tr>\n" +
                "        </table>\n" +
                "        <![endif]-->\n" +
                "        </td>\n" +
                "    </tr>\n" +
                "</table>\n" +
                "    \n" +
                "</body>\n" +
                "</html>";

        return  mensaje;
    }

    private String generarHtmlDetalles(List<BillsProduct> detalles){

        String respuesta = "";
        for (BillsProduct detalle : detalles)
        {
            Optional<Product> producto = servicioProducto.buscarProducto(detalle.getId().getFkProductSerial());
            DecimalFormat formato = new DecimalFormat("#,###.00");
            String valorFormateado = formato.format(detalle.getTotal());


            respuesta+="<tr>\n" +
                    "           <td width=\"75%\" align=\"left\" style=\"font-family: Open Sans, Helvetica, Arial, sans-serif; font-size: 16px; font-weight: 400; line-height: 24px; padding: 15px 10px 5px 10px;\">\n" +
                    "                "+producto.get().getProductName()+" x"+detalle.getUnits()+"\n" +
                    "           </td>\n" +
                    "           <td width=\"25%\" align=\"left\" style=\"font-family: Open Sans, Helvetica, Arial, sans-serif; font-size: 16px; font-weight: 400; line-height: 24px; padding: 15px 10px 5px 10px;\">\n" +
                    "                $"+valorFormateado+"\n" +
                    "           </td>\n" +
                    "   </tr>\n"
            ;
        }

        return respuesta;
    }

    private String generarHtmlEnvio(Bill factura){
        String respuesta = "";
            String valorFormateado="";
            if (factura.getBillSendCost().compareTo(BigDecimal.ZERO)==0){
                valorFormateado="Gratis :)";
            }else{
                DecimalFormat formato = new DecimalFormat("#,###.00");
                valorFormateado = formato.format(factura.getBillSendCost());
            }
            respuesta+="<tr>\n" +
                    "           <td width=\"75%\" align=\"left\" style=\"font-family: Open Sans, Helvetica, Arial, sans-serif; font-size: 16px; font-weight: 400; line-height: 24px; padding: 15px 10px 5px 10px;\">\n" +
                    "                "+"Envio"+"\n" +
                    "           </td>\n" +
                    "           <td width=\"25%\" align=\"left\" style=\"font-family: Open Sans, Helvetica, Arial, sans-serif; font-size: 16px; font-weight: 400; line-height: 24px; padding: 15px 10px 5px 10px;\">\n" +
                    "                $"+valorFormateado+"\n" +
                    "           </td>\n" +
                    "   </tr>\n"
            ;
        return respuesta;
    }

    private String generarHtmlTotal(BigDecimal total){

        DecimalFormat formato = new DecimalFormat("#,###.00");
        String valorFormateado = formato.format(total);

        return " <td width=\"75%\" align=\"left\" style=\"font-family: Open Sans, Helvetica, Arial, sans-serif; font-size: 16px; font-weight: 800; line-height: 24px; padding: 10px; border-top: 3px solid #E9967A; border-bottom: 3px solid #E9967A;\">\n" +
                "                                        TOTAL\n" +
                "                                    </td>\n" +
                "                                    <td width=\"25%\" align=\"left\" style=\"font-family: Open Sans, Helvetica, Arial, sans-serif; font-size: 16px; font-weight: 800; line-height: 24px; padding: 10px; border-top: 3px solid #E9967A; border-bottom: 3px solid #E9967A;\">\n" +
                "                                        $"+valorFormateado+"\n" +
                "                                    </td>";
    }

    private String generarFechaDeLlegada(){

        LocalDateTime fecha = LocalDateTime.now();
        LocalDate hoy = LocalDate.now();

        if(hoy.getDayOfWeek().equals(DayOfWeek.SATURDAY)) fecha = fecha.plusDays(6);
        else if (hoy.getDayOfWeek().equals(DayOfWeek.SUNDAY)) fecha = fecha.plusDays(5);
        else{ fecha = fecha.plusDays(4); }
        DateTimeFormatter isoFecha = DateTimeFormatter.ISO_LOCAL_DATE;
        return fecha.format(isoFecha);
    }


}
