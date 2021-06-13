package com.Quda.Backend.Servicio;

import com.Quda.Backend.Entidades.Bill;
import com.Quda.Backend.Entidades.User;
import com.Quda.Backend.Repositorio.JpaFactura;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLOutput;
import java.util.Date;
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
            System.out.println(e.getMessage());
        }
        //Complementos
        factura.setBillDate(new Date());
        factura.setPersonId(user.getPersonId());
        factura.setStateId(11);

        try {
            return Optional.of(jpaFactura.save(factura));
        }catch (NullPointerException e){
            System.out.println(e.getMessage());
        }
        return Optional.of(jpaFactura.save(factura));
    }

    public Optional<Bill> buscarFactura(Integer idFactura){
        return jpaFactura.findById(idFactura);
    }




}
