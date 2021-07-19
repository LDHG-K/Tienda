package com.Quda.Backend.TiendaApp.Servicio;
import com.Quda.Backend.MailApp.Controladores.MailController;
import com.Quda.Backend.TiendaApp.Entidad.Bill;
import com.Quda.Backend.TiendaApp.Entidad.BillsProduct;
import com.Quda.Backend.TiendaApp.Entidad.Product;
import com.Quda.Backend.TiendaApp.Entidad.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ServicioCompras {

       private final ServicioProducto servicioProducto;
       private final ServicioFactura servicioFactura;
       private final ServicioDetalleFactura servicioDetalleFactura;
       private final MailController mailController;
       private final ServicioUsuario servicioUsuario;
       //CREA UNA FACTURA CON LA LISTA DE PRODUCTOS, EL CUERPO DE LA FACTURA Y EL USUARIO
       @Transactional
       public void registrarCompra(HashMap<Integer,Integer> productos, String user, Bill facturaCrear){

              try {
                     if (servicioProducto.existeStock(productos)){

                            Optional<Bill> factura = servicioFactura.crearFactura(user,facturaCrear);
                            try {
                                   servicioProducto.restarExistencias(productos);
                            }
                            catch (RuntimeException e){
                                   throw new RuntimeException("Error al restar productos ");
                            }

                            try {
                                   servicioDetalleFactura.crearDetalles(factura.get().getBillId(),productos);
                            }
                            catch (RuntimeException e){
                                   System.out.println(e.getMessage());
                                   throw new RuntimeException("Error al crear los detalles");
                            }

                            try{
                                   enviarCorreo(productos,user,facturaCrear);
                            }
                            catch (RuntimeException e){
                                   throw new RuntimeException("Error al enviar el correo");
                            } catch (MessagingException e) {
                                   e.printStackTrace();
                            } catch (UnsupportedEncodingException e) {
                                   e.printStackTrace();
                            }
                     }
              }
              catch (RuntimeException e){
                     throw e;
              }
       }

       private void enviarCorreo (HashMap<Integer,Integer> productos, String usuarioid, Bill factura) throws MessagingException, UnsupportedEncodingException {

              List<BillsProduct> detalles = servicioDetalleFactura.buscarDetallesDeUnaFactura(factura.getBillId());
              BigDecimal total = servicioFactura.buscarFactura(factura.getBillId()).get().getBillTotal();
              User usuario = servicioUsuario.buscarUsuario(usuarioid).get();

              mailController.correoCompra(detalles,total,usuario,factura);
       }


}
