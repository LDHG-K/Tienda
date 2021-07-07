package com.Quda.Backend.TiendaApp.Servicio;

import com.Quda.Backend.TiendaApp.Entidad.Bill;
import com.Quda.Backend.TiendaApp.Entidad.User;
import com.Quda.Backend.TiendaApp.Repositorio.JpaFactura;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ServicioFactura {

    private final JpaFactura jpaFactura;
    private final ServicioUsuario servicioUsuario;
    // CRUD ====================================================================
    @Transactional
    public Optional<Bill> crearFactura (String usuario, Bill factura ){
        User user = null;
        //Validacion
        try {
            user = servicioUsuario.validarUsuario(usuario).get();
        }
        catch (RuntimeException e){
            throw e;
        }
        //Complementos
        factura.setBillDate(new Date());
        factura.setPersonId(user.getPersonId());
        factura.setStateId(11);

        try {
            return Optional.of(jpaFactura.save(factura));
        }catch (NullPointerException e){
            throw e;
        }
    }

    public Optional<Bill> buscarFactura(Integer idFactura){
        Optional<Bill> factura = jpaFactura.findById(idFactura);
        if (factura.isEmpty()){
            throw new RuntimeException("No existe la factura con el id "+idFactura);
        }
        return factura;
    }


    public List<Bill> buscarFacturasDelUsuario(String id) {
        List<Bill> facturas = null;
        User usuario = null;
        try {
            usuario = servicioUsuario.buscarUsuario(id).get();
            facturas = jpaFactura.buscarFacturaPorUsuario(usuario.getPersonId());
        }
        catch (RuntimeException e){
            throw e;
        }

        return facturas;
    }
}
