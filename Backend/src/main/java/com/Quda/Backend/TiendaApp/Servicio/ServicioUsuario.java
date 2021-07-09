package com.Quda.Backend.TiendaApp.Servicio;

import com.Quda.Backend.LoginApp.RegisterToken.ServicioToken;
import com.Quda.Backend.LoginApp.RegisterToken.TokenConfirmacion;
import com.Quda.Backend.MailApp.Servicios.ServicioMail;
import com.Quda.Backend.TiendaApp.Entidad.Person;
import com.Quda.Backend.TiendaApp.Entidad.User;
import com.Quda.Backend.TiendaApp.Repositorio.JpaPersona;
import com.Quda.Backend.TiendaApp.Repositorio.JpaUsuario;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ServicioUsuario {


    private final JpaUsuario jpaUsuario;
    private final JpaPersona jpaPersona;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ServicioToken servicioToken;
    private final ServicioMail servicioMail;

    public Optional<User> buscarUsuario(String id){
        return jpaUsuario.findById(id);
    }

    public Optional<User> eliminarUsuario(String id){
        User usuarioBuscado = validarUsuario(id).get();
        usuarioBuscado.setStateId(2);
        return Optional.ofNullable( jpaUsuario.save(usuarioBuscado));
    }

    @Transactional
    public Optional<User> crearUsuario(User usuario) throws MessagingException, UnsupportedEncodingException {
        usuario.getPerson().setPersonCreationDate(new Date());
        Person person = jpaPersona.save(usuario.getPerson());

        if (!jpaUsuario.findById(usuario.getUserNickName()).isEmpty()){
            throw new RuntimeException("Usuario "+ usuario.getUserNickName() + " Ya existe");
        }

        usuario.setUserPassword(bCryptPasswordEncoder.encode(usuario.getUserPassword()));

        usuario.setUserRole((Long.parseLong("1")));
        usuario.setStateId(1);
        usuario.setPersonId(person.getPersonId());
        Optional<User> user = Optional.ofNullable(jpaUsuario.save(usuario));

        String tokenID = UUID.randomUUID().toString();
        TokenConfirmacion confirmacion = TokenConfirmacion.builder()
                .token(tokenID)
                .created(LocalDateTime.now())
                .expired(LocalDateTime.now().plusMinutes(15))
                .userId(user.get().getUserNickName())
                .build();
        servicioToken.guardarToken(confirmacion);

        servicioMail.enviarMensajeConHtml(person.getPersonEmail(),"Verificacion de cuenta",person.getPersonName(),tokenID);

        return user;
    }

    public List<User> listarUsuarios(){
        return jpaUsuario.findAll();
    }

    @Transactional
    public Optional<User> editarUsuario(User usuario, String id)
    {
        try{
            jpaUsuario.findById(id);
            jpaUsuario.cambiarIdUsuario(usuario.getUserNickName(),id);
        }
        catch ( Exception e){
            throw new RuntimeException("No existe el usuario que se desea actualizar");
        }
        User temp = buscarUsuario(usuario.getUserNickName()).get();
        temp.setUserPassword(usuario.getUserPassword());
        return Optional.ofNullable(jpaUsuario.save(temp));
    }

    public Optional<User> validarUsuario(String id){
        Optional<User> user = buscarUsuario(id);
        if (user.isEmpty()){
            throw new RuntimeException("USUARIO NO EXISTE");
        }
        if (user.get().getStateId()==2){
            throw new RuntimeException("USUARIO ELIMINADO");
        }

        return user;
    }

    public void desbloquearUsuario(String token){

        TokenConfirmacion tokenValidado = servicioToken.validarToken(token);
        Optional<User> user = buscarUsuario(tokenValidado.getUserId());
        if (user.get().getEnabled()==true){
            throw new RuntimeException("Usuario ya ha sido validado");
        }
        user.get().setEnabled(true);
        jpaUsuario.save(user.get());
    }




}
