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
    public void enviarMensaje(String correo, String asunto, String mensaje, String token) throws MessagingException, UnsupportedEncodingException, FileNotFoundException {
        enviarMensajeConHtml(correo,asunto,mensaje,token);
    }

    public void enviarMensajeSimple(String correo, String asunto, String mensaje) {

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom("quda.maquillaje@gmail.com");
        msg.setTo(correo);
        msg.setSubject(asunto);
        msg.setText(mensaje);
        javaMailSender.send(msg);

    }

    public void enviarMensajeConHtml(String correo, String asunto, String nombre, String token) throws MessagingException, UnsupportedEncodingException {

        MimeMessage mensajeEditado = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mensajeEditado);

        helper.setFrom("quda.maquillaje@gmail.com","Equipo de ventas");
        helper.setTo(correo);
        helper.setSubject(asunto);
        helper.setText("<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "<title></title>\n" +
                "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\n" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
                "<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\" />\n" +
                "<style type=\"text/css\">\n" +
                "    /* FONTS */\n" +
                "    @media screen {\n" +
                "        @font-face {\n" +
                "          font-family: 'Lato';\n" +
                "          font-style: normal;\n" +
                "          font-weight: 400;\n" +
                "          src: local('Lato Regular'), local('Lato-Regular'), url(https://fonts.gstatic.com/s/lato/v11/qIIYRU-oROkIk8vfvxw6QvesZW2xOQ-xsNqO47m55DA.woff) format('woff');\n" +
                "        }\n" +
                "        \n" +
                "        @font-face {\n" +
                "          font-family: 'Lato';\n" +
                "          font-style: normal;\n" +
                "          font-weight: 700;\n" +
                "          src: local('Lato Bold'), local('Lato-Bold'), url(https://fonts.gstatic.com/s/lato/v11/qdgUG4U09HnJwhYI-uK18wLUuEpTyoUstqEm5AMlJo4.woff) format('woff');\n" +
                "        }\n" +
                "        \n" +
                "        @font-face {\n" +
                "          font-family: 'Lato';\n" +
                "          font-style: italic;\n" +
                "          font-weight: 400;\n" +
                "          src: local('Lato Italic'), local('Lato-Italic'), url(https://fonts.gstatic.com/s/lato/v11/RYyZNoeFgb0l7W3Vu1aSWOvvDin1pK8aKteLpeZ5c0A.woff) format('woff');\n" +
                "        }\n" +
                "        \n" +
                "        @font-face {\n" +
                "          font-family: 'Lato';\n" +
                "          font-style: italic;\n" +
                "          font-weight: 700;\n" +
                "          src: local('Lato Bold Italic'), local('Lato-BoldItalic'), url(https://fonts.gstatic.com/s/lato/v11/HkF_qI1x_noxlxhrhMQYELO3LdcAZYWl9Si6vvxL-qU.woff) format('woff');\n" +
                "        }\n" +
                "    }\n" +
                "    \n" +
                "    /* CLIENT-SPECIFIC STYLES */\n" +
                "    body, table, td, a { -webkit-text-size-adjust: 100%; -ms-text-size-adjust: 100%; }\n" +
                "    table, td { mso-table-lspace: 0pt; mso-table-rspace: 0pt; }\n" +
                "    img { -ms-interpolation-mode: bicubic; }\n" +
                "\n" +
                "    /* RESET STYLES */\n" +
                "    img { border: 0; height: auto; line-height: 100%; outline: none; text-decoration: none; }\n" +
                "    table { border-collapse: collapse !important; }\n" +
                "    body { height: 100% !important; margin: 0 !important; padding: 0 !important; width: 100% !important; }\n" +
                "\n" +
                "    /* iOS BLUE LINKS */\n" +
                "    a[x-apple-data-detectors] {\n" +
                "        color: inherit !important;\n" +
                "        text-decoration: none !important;\n" +
                "        font-size: inherit !important;\n" +
                "        font-family: inherit !important;\n" +
                "        font-weight: inherit !important;\n" +
                "        line-height: inherit !important;\n" +
                "    }\n" +
                "    \n" +
                "    /* MOBILE STYLES */\n" +
                "    @media screen and (max-width:600px){\n" +
                "        h1 {\n" +
                "            font-size: 32px !important;\n" +
                "            line-height: 32px !important;\n" +
                "        }\n" +
                "    }\n" +
                "\n" +
                "    /* ANDROID CENTER FIX */\n" +
                "    div[style*=\"margin: 16px 0;\"] { margin: 0 !important; }\n" +
                "</style>\n" +
                "</head>\n" +
                "<body style=\"background-color: #f4f4f4; margin: 0 !important; padding: 0 !important;\">\n" +
                "\n" +
                "<!-- HIDDEN PREHEADER TEXT -->\n" +
                "<div style=\"display: none; font-size: 1px; color: #fefefe; line-height: 1px; font-family: 'Lato', Helvetica, Arial, sans-serif; max-height: 0px; max-width: 0px; opacity: 0; overflow: hidden;\">\n" +
                "      Este es un correo para confirmar tus datos de registro en Q'uda.\n" +
                "</div>\n" +
                "\n" +
                "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n" +
                "    <!-- LOGO -->\n" +
                "    <tr>\n" +
                "        <td bgcolor=\"#EFA885\" align=\"center\">\n" +
                "            <!--[if (gte mso 9)|(IE)]>\n" +
                "            <table align=\"center\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"600\">\n" +
                "            <tr>\n" +
                "            <td align=\"center\" valign=\"top\" width=\"600\">\n" +
                "            <![endif]-->\n" +
                "            <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\" >\n" +
                "                <tr>\n" +
                "                    <!--\n" +
                "                    <td align=\"center\" valign=\"top\" style=\"padding: 40px 10px 40px 10px;\">\n" +
                "                    <svg width=\"200\" height=\"200\" style=\"display: block;\" \n" +
                "                    id=\"Capa_1\" data-name=\"Capa 1\" xmlns=\"http://www.w3.org/2000/svg\" viewBox=\"0 0 938.4 638.73\"><defs><style>.cls-1{fill:#f8d49b;stroke:#f8d49b;stroke-miterlimit:10;stroke-width:4px;}.cls-2{isolation:isolate;}.cls-3{fill:#fff;}</style></defs><title>LogoSF</title><g id=\"Fuentes\"><rect class=\"cls-1\" x=\"2\" y=\"2\" width=\"300.19\" height=\"634.73\"/><g class=\"cls-2\"><path class=\"cls-3\" d=\"M482.08,277.54q22.76,0,39.67,9.92a93.64,93.64,0,0,1,28.59,26A139.74,139.74,0,0,1,569,349.3a246.32,246.32,0,0,1,10.5,40,313.53,313.53,0,0,1,4.38,38.21q.87,18.09.29,29.76a508.36,508.36,0,0,1-13.13,87.5,553.31,553.31,0,0,1-25.38,79A464.59,464.59,0,0,1,511.25,692,441,441,0,0,1,471,747.75c1.94,1.94,4,4.09,6.13,6.42s4.17,4.47,6.12,6.42A211.41,211.41,0,0,0,516.5,788a156.13,156.13,0,0,0,32.09,16.62q17.5,6.71,33.25,2,.58,8.18-5,12.55a43.46,43.46,0,0,1-12,6.71,76.88,76.88,0,0,1-18.09,2.91,81.1,81.1,0,0,1-28.29-5.54A191.55,191.55,0,0,1,490,809.88a240.76,240.76,0,0,1-26-17.21q-12.25-9.33-20.42-16.33Q417.33,802,392,814.84t-47,12.84q-35.6,0-50.76-15.46T280.82,774q.57-11.09,7-23.33a80,80,0,0,1,17.79-22.46,92.48,92.48,0,0,1,27.13-16.63q15.75-6.41,36.17-6.41a160.31,160.31,0,0,1,35.29,3.5,121,121,0,0,1,30.63,11.66,483,483,0,0,0,35.59-59.21,619.93,619.93,0,0,0,30-68.55,647.42,647.42,0,0,0,21.88-71.76A421.54,421.54,0,0,0,533.42,452q2.33-42-2.33-69.13t-13.42-43.17q-8.75-16-19.25-22.46t-19.25-6.42q-19.85,0-44.05,19.55t-46.38,54.54q-22.17,35-38.5,82.85T329.82,572.15q-.58,24.51,4.67,43.76a99.73,99.73,0,0,0,6.12,16,54.29,54.29,0,0,0,9.63,14,41.71,41.71,0,0,0,14,9.63q8.16,3.49,19.25,2.91-1.17,9.93-7.58,15.17a42.36,42.36,0,0,1-13.42,7.59,55.24,55.24,0,0,1-18.67,2.91q-8.74,0-21.58-7.29t-24.8-23q-12-15.75-19.54-40.55t-4.67-59.8a362.51,362.51,0,0,1,8.46-52.79,402.21,402.21,0,0,1,19-60.09,437.72,437.72,0,0,1,29.46-59.8,267.62,267.62,0,0,1,40-52.21,191.36,191.36,0,0,1,50.76-37Q448.84,277.54,482.08,277.54ZM346.16,787.42q15.15,0,31.79-10.21t33-27.71a52.72,52.72,0,0,0-29.75-9.33A59.87,59.87,0,0,0,361,743.38a51,51,0,0,0-15.46,8.46,46.33,46.33,0,0,0-10.21,11.37,22.68,22.68,0,0,0-3.79,12,10.13,10.13,0,0,0,2.92,8.75C336.82,786.26,340.7,787.42,346.16,787.42Z\" transform=\"translate(-118 -228)\"/></g><g class=\"cls-2\"><path class=\"cls-3\" d=\"M678.55,508.91q-7.77,14.64-7.77,43.61,0,43.62,16.43,69.6,9.56,15.23,22.85,15.23t21.36-14Q741,606,741,575.52q0-38.24-10.75-56.45-4.79-8.67-5.52-12.55c-.5-2.59.14-4.48,1.94-5.68s4.78-1.79,9-1.79q18.21,0,23.89,37.64a123,123,0,0,1,1.8,20.91A146.8,146.8,0,0,1,750.53,612q-15.22,37.35-48.09,37.34-17.33,0-29.57-12.84-23-24.5-23-73.78,0-21.51,3-32.86T657.94,515a34.4,34.4,0,0,1,5.07-6.42,7.46,7.46,0,0,1,6.73-2.39A41.29,41.29,0,0,1,678.55,508.91Z\" transform=\"translate(-118 -228)\"/><path class=\"cls-3\" d=\"M903.17,409.74q-11.35,102.76-11.35,146.51a479.11,479.11,0,0,0,8.06,87.37q-6,6-10.45,6a20.15,20.15,0,0,1-7.47-1.2,211.64,211.64,0,0,1-7.47-48.09q-7.17,11.36-10.6,16.13a182.9,182.9,0,0,1-11.8,14Q836.85,648.41,816,648.4q-15.84,0-22.85-14.78t-7-37.94a183.16,183.16,0,0,1,5.22-44.21A89.72,89.72,0,0,1,809.38,515q12.82-15.37,31.66-15.38A46.22,46.22,0,0,1,862,504.43q9.56,4.79,13.14,12.84.6-8.06,1.19-24.79t1.35-29.72q.74-13,2.54-26.43,3.29-28.68,14.63-28.68A20,20,0,0,1,903.17,409.74ZM868.37,528a23.5,23.5,0,0,0-10-9.71q-6.27-3.14-16.58-3.14t-19.26,11.65q-17.62,23-17.63,66.61a90.42,90.42,0,0,0,4.49,27.93q4.47,13.89,13.14,13.89,12.54,0,24.49-16.43a127.36,127.36,0,0,0,18.52-37.78q6.57-21.36,6.57-38.39A29.08,29.08,0,0,0,868.37,528Z\" transform=\"translate(-118 -228)\"/><path class=\"cls-3\" d=\"M1026.53,563l-1.5,34.36q0,26,6,34.94,3.59,6,9.86,6a20.79,20.79,0,0,0,5.07-.9q6.29-1.79,10.46,1.79-2.1,10.47-20.91,10.46-12.85,0-17-10.76a80.11,80.11,0,0,1-4.78-20.16q-1.5-12.09-2.39-16.58-19.41,37-37.63,45.11a52,52,0,0,1-21.51,4.18q-12.25,0-19-11.8t-6.72-34.5a166,166,0,0,1,7.47-47.64q7.47-24.94,21.81-42.12t31.36-17.17q24.48,0,32.56,20.31Q1026.53,535.79,1026.53,563Zm-73.18-9.7A287.69,287.69,0,0,0,947.07,584,182.75,182.75,0,0,0,945,609.72q0,11.21,4.63,19.27t12.1,8.06q16.13,0,31.06-30.47t14.94-56.75q0-17-9-28.08a19.43,19.43,0,0,0-15.68-7.17q-9.72,0-17.63,11.21T953.35,553.27Z\" transform=\"translate(-118 -228)\"/></g><path class=\"cls-3\" d=\"M588.21,296.82a186,186,0,0,1,18.45,103.51c-.68,6.45,6,12,12,12,7.06,0,11.31-5.53,12-12,4.21-39.74-3.73-80-21.72-115.62-2.93-5.79-11.07-7.44-16.42-4.31-5.88,3.44-7.24,10.61-4.31,16.42Z\" transform=\"translate(-118 -228)\"/></g>\n" +
                "                    </svg>\n" +
                "                    </td>\n" +
                "                -->\n" +
                "                    <td align=\"center\" valign=\"top\" style=\"padding: 40px 10px 40px 10px;\">\n" +
                "                        <a href=\"#\" target=\"_blank\">\n" +
                "                            <img alt=\"Logo\" src=\"https://i.ibb.co/LZFR0Yh/Logo.jpg\"\n" +
                "                           style=\"display: block; width: 200px; height : 200px; font-family: 'Lato', Helvetica, Arial, sans-serif; color: #ffffff; font-size: 18px;\" border=\"0\">\n" +
                "                        </a>\n" +
                "                    </td>\n" +
                "                    \n" +
                "                </tr>\n" +
                "            </table>\n" +
                "            <!--[if (gte mso 9)|(IE)]>\n" +
                "            </td>\n" +
                "            </tr>\n" +
                "            </table>\n" +
                "            <![endif]-->\n" +
                "        </td>\n" +
                "    </tr>\n" +
                "    <!-- HERO -->\n" +
                "    <tr>\n" +
                "        <td bgcolor=\"#EFA885\" align=\"center\" style=\"padding: 0px 10px 0px 10px;\">\n" +
                "            <!--[if (gte mso 9)|(IE)]>\n" +
                "            <table align=\"center\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"600\">\n" +
                "            <tr>\n" +
                "            <td align=\"center\" valign=\"top\" width=\"600\">\n" +
                "            <![endif]-->\n" +
                "            <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\" >\n" +
                "                <tr>\n" +
                "                    <td bgcolor=\"#ffffff\" align=\"center\" valign=\"top\" style=\"padding: 40px 20px 20px 20px; border-radius: 4px 4px 0px 0px; color: #111111; font-family: 'Lato', Helvetica, Arial, sans-serif; font-size: 48px; font-weight: 400; letter-spacing: 4px; line-height: 48px;\">\n" +
                "                      <h1 style=\"font-size: 48px; font-weight: 400; margin: 0;\">¡HOLA "+nombre+"!</h1>\n" +
                "                    </td>\n" +
                "                </tr>\n" +
                "            </table>\n" +
                "            <!--[if (gte mso 9)|(IE)]>\n" +
                "            </td>\n" +
                "            </tr>\n" +
                "            </table>\n" +
                "            <![endif]-->\n" +
                "        </td>\n" +
                "    </tr>\n" +
                "    <!-- COPY BLOCK -->\n" +
                "    <tr>\n" +
                "        <td bgcolor=\"#f4f4f4\" align=\"center\" style=\"padding: 0px 10px 0px 10px;\">\n" +
                "            <!--[if (gte mso 9)|(IE)]>\n" +
                "            <table align=\"center\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"600\">\n" +
                "            <tr>\n" +
                "            <td align=\"center\" valign=\"top\" width=\"600\">\n" +
                "            <![endif]-->\n" +
                "            <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\" >\n" +
                "              <!-- COPY -->\n" +
                "              <tr>\n" +
                "                <td bgcolor=\"#ffffff\" align=\"left\" style=\"padding: 20px 30px 40px 30px; color: #666666; font-family: 'Lato', Helvetica, Arial, sans-serif; font-size: 18px; font-weight: 400; line-height: 25px;\" >\n" +
                "                  <p style=\"margin: 0;\">Estamos anciosos de tenerte en nuestro equipo solo falta que hagas click en el boton de abajo para completar tu registro</p>\n" +
                "                </td>\n" +
                "              </tr>\n" +
                "              <!-- BULLETPROOF BUTTON -->\n" +
                "              <tr>\n" +
                "                <td bgcolor=\"#ffffff\" align=\"left\">\n" +
                "                  <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n" +
                "                    <tr>\n" +
                "                      <td bgcolor=\"#ffffff\" align=\"center\" style=\"padding: 20px 30px 60px 30px;\">\n" +
                "                        <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n" +
                "                          <tr>\n" +
                "                              <td align=\"center\" style=\"border-radius: 3px;\" bgcolor=\"#E9967A\"><a href=\"http://localhost:8080/Usuarios/Registro/"+token+" target=\"_blank\" style=\"font-size: 20px; font-family: Helvetica, Arial, sans-serif; color: #000000; text-decoration: none; color: #ffffff; text-decoration: none; padding: 15px 25px; border-radius: 0px; border: 0px solid #FFFFFF; display: inline-block;\">Confirmar cuenta</a></td>\n" +
                "                          </tr>\n" +
                "                        </table>\n" +
                "                      </td>\n" +
                "                    </tr>\n" +
                "                  </table>\n" +
                "                </td>\n" +
                "              </tr>\n" +
                "              <!-- COPY -->\n" +
                "              <tr>\n" +
                "                <td bgcolor=\"#ffffff\" align=\"left\" style=\"padding: 0px 30px 0px 30px; color: #666666; font-family: 'Lato', Helvetica, Arial, sans-serif; font-size: 18px; font-weight: 400; line-height: 25px;\" >\n" +
                "                  <p style=\"margin: 0;\">Si no funciona puedes copiar y pegar el siguiente link en tu navegador:</p>\n" +
                "                </td>\n" +
                "              </tr>\n" +
                "              <!-- COPY -->\n" +
                "                <tr>\n" +
                "                  <td bgcolor=\"#ffffff\" align=\"left\" style=\"padding: 20px 30px 20px 30px; color: #666666; font-family: 'Lato', Helvetica, Arial, sans-serif; font-size: 18px; font-weight: 400; line-height: 25px;\" >\n" +
                "                    <p style=\"margin: 0;\"><a href=\"http://localhost:8080/Usuarios/Registro/"+token+"\" target=\"_blank\" style=\"color: #E9967A;\">http://localhost:8080/Usuarios/Registro/24bd73ea-de07-475b-8878-f61182e0008c</a></p>\n" +
                "                  </td>\n" +
                "                </tr>\n" +
                "              <!-- COPY -->\n" +
                "              <tr>\n" +
                "                <td bgcolor=\"#ffffff\" align=\"left\" style=\"padding: 0px 30px 20px 30px; color: #666666; font-family: 'Lato', Helvetica, Arial, sans-serif; font-size: 18px; font-weight: 400; line-height: 25px;\" >\n" +
                "                  <p style=\"margin: 0;\">Si tienes alguna duda puedes responder a este correo y te ayudaremos con mucho gusto.</p>\n" +
                "                </td>\n" +
                "              </tr>\n" +
                "              <!-- COPY -->\n" +
                "              <tr>\n" +
                "                <td bgcolor=\"#ffffff\" align=\"left\" style=\"padding: 0px 30px 40px 30px; border-radius: 0px 0px 4px 4px; color: #666666; font-family: 'Lato', Helvetica, Arial, sans-serif; font-size: 18px; font-weight: 400; line-height: 25px;\" >\n" +
                "                  <p style=\"margin: 0;\">Un fuerte abrazo,<br>Equipo Quda</p>\n" +
                "                </td>\n" +
                "              </tr>\n" +
                "            </table>\n" +
                "            <!--[if (gte mso 9)|(IE)]>\n" +
                "            </td>\n" +
                "            </tr>\n" +
                "            </table>\n" +
                "            <![endif]-->\n" +
                "        </td>\n" +
                "    </tr>\n" +
                "    <!-- SUPPORT CALLOUT -->\n" +
                "    <tr>\n" +
                "        <td bgcolor=\"#f4f4f4\" align=\"center\" style=\"padding: 30px 10px 0px 10px;\">\n" +
                "            <!--[if (gte mso 9)|(IE)]>\n" +
                "            <table align=\"center\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"600\">\n" +
                "            <tr>\n" +
                "            <td align=\"center\" valign=\"top\" width=\"600\">\n" +
                "            <![endif]-->\n" +
                "            <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\" >\n" +
                "                <!-- HEADLINE -->\n" +
                "                <tr>\n" +
                "                  <td bgcolor=\"#FFF0D1\" align=\"center\" style=\"padding: 30px 30px 30px 30px; border-radius: 4px 4px 4px 4px; color: #666666; font-family: 'Lato', Helvetica, Arial, sans-serif; font-size: 18px; font-weight: 400; line-height: 25px;\" >\n" +
                "                    <h2 style=\"font-size: 20px; font-weight: 400; color: #111111; margin: 0;\">¿Necesitas atencion personalizada?</h2>\n" +
                "                    <p style=\"margin: 0;\"><a href=\"https://wa.me/573202423015\" target=\"_blank\" style=\"color: #9B4503;\">Estamos en WhatsApp;</a></p>\n" +
                "                  </td>\n" +
                "                </tr>\n" +
                "            </table>\n" +
                "            <!--[if (gte mso 9)|(IE)]>\n" +
                "            </td>\n" +
                "            </tr>\n" +
                "            </table>\n" +
                "            <![endif]-->\n" +
                "        </td>\n" +
                "    </tr>\n" +
                "    <!-- FOOTER -->\n" +
                "    <tr>\n" +
                "        <td bgcolor=\"#f4f4f4\" align=\"center\" style=\"padding: 0px 10px 0px 10px;\">\n" +
                "            <!--[if (gte mso 9)|(IE)]>\n" +
                "            <table align=\"center\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"600\">\n" +
                "            <tr>\n" +
                "            <td align=\"center\" valign=\"top\" width=\"600\">\n" +
                "            <![endif]-->\n" +
                "            <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\" >\n" +
                "              <!-- NAVIGATION -->\n" +
                "              <br>\n" +
                "              <br>\n" +
                "              <!-- PERMISSION REMINDER -->\n" +
                "              <tr>\n" +
                "                <td bgcolor=\"#f4f4f4\" align=\"left\" style=\"padding: 0px 30px 30px 30px; color: #666666; font-family: 'Lato', Helvetica, Arial, sans-serif; font-size: 14px; font-weight: 400; line-height: 18px;\" >\n" +
                "                  <p style=\"margin: 0;\">Recibiste este mail para confirmar tu cuenta en Q'uda, <a href=\"#\" target=\"_blank\" style=\"color: #111111; font-weight: 700;\">Miralos en tu navegador</a>.</p>\n" +
                "                </td>\n" +
                "              </tr>\n" +
                "              <!-- UNSUBSCRIBE -->\n" +
                "              <tr>\n" +
                "                <td bgcolor=\"#f4f4f4\" align=\"left\" style=\"padding: 0px 30px 30px 30px; color: #666666; font-family: 'Lato', Helvetica, Arial, sans-serif; font-size: 14px; font-weight: 400; line-height: 18px;\" >\n" +
                "                  <p style=\"margin: 0;\">Si recibiste este Email por accidente <a href=\"#\" target=\"_blank\" style=\"color: #111111; font-weight: 700;\">haz click aqui</a>.</p>\n" +
                "                </td>\n" +
                "              </tr>\n" +
                "              <!-- ADDRESS -->\n" +
                "              <tr>\n" +
                "                <td bgcolor=\"#f4f4f4\" align=\"left\" style=\"padding: 0px 30px 30px 30px; color: #666666; font-family: 'Lato', Helvetica, Arial, sans-serif; font-size: 14px; font-weight: 400; line-height: 18px;\" >\n" +
                "                  <p style=\"margin: 0;\">@Copyright Quda Maquillaje Colombia - Ibagué - Tolima - 730004</p>\n" +
                "                </td>\n" +
                "              </tr>\n" +
                "            </table>\n" +
                "            <!--[if (gte mso 9)|(IE)]>\n" +
                "            </td>\n" +
                "            </tr>\n" +
                "            </table>\n" +
                "            <![endif]-->\n" +
                "        </td>\n" +
                "    </tr>\n" +
                "</table>\n" +
                "    \n" +
                "</body>\n" +
                "</html>",true);


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
