package com.Quda.Backend.Servicio;
import com.Quda.Backend.Entidades.Bill;
import com.Quda.Backend.Entidades.Product;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ServicioCompras {

       private final ServicioProducto servicioProducto;
       private final ServicioFactura servicioFactura;
       private final ServicioDetalleFactura servicioDetalleFactura;

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
                                   throw new RuntimeException("Error al crear los detalles");
                            }


                     }
              }
              catch (RuntimeException e){
                     throw e;
              }
       }



}
