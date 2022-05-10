package com.Quda.Backend.TiendaApp.Servicio;

import com.Quda.Backend.LoginApp.RegisterToken.ServicioToken;
import com.Quda.Backend.LoginApp.RegisterToken.TokenConfirmacion;
import com.Quda.Backend.MailApp.Controladores.MailController;
import com.Quda.Backend.TiendaApp.Dominio.DTOS.Persona;
import com.Quda.Backend.TiendaApp.Dominio.DTOS.Usuario;
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
    private final MailController mailController;

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
        usuario.setLocked(false);
        usuario.setEnabled(false);
        Optional<User> user = Optional.ofNullable(jpaUsuario.save(usuario));

        String tokenID = UUID.randomUUID().toString();
        TokenConfirmacion confirmacion = TokenConfirmacion.builder()
                .token(tokenID)
                .created(LocalDateTime.now())
                .expired(LocalDateTime.now().plusMinutes(15))
                .userId(user.get().getUserNickName())
                .build();
        servicioToken.guardarToken(confirmacion);

        mailController.correoRegistro(person.getPersonEmail(),"Verificacion de cuenta",person.getPersonName(),tokenID);
        System.out.println(tokenID);

        return user;
    }

    public List<User> listarUsuarios(){
        return jpaUsuario.findAll();
    }

    //TODO -> Corregir la logica del metodo que actualiza el usuario, revisar cambio de usuario y contrasena
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
        user.get().setLocked(true);
        jpaUsuario.save(user.get());
    }

    public Usuario usuarioEntityToUsuarioDTO(User user){

        Usuario u = Usuario.builder()
                .userNickName(user.getUserNickName())
                .userPerson(personaEntityToPersonaDTO(user.getPerson()) )
                .userPassword(user.getUserPassword())
                .build();
        return u;
    }

    public User usuarioDTOToUsuarioEntiry(Usuario user){

        User u = User.builder()
                .userNickName(user.getUserNickName())
                .person(personaDTOToPersonaEntity(user.getUserPerson()) )
                .userPassword(user.getUserPassword())
                .build();
        return u;

    }

    public Persona personaEntityToPersonaDTO (Person person){

        Persona p = Persona.builder()
                .fkNamesIdentificationId(person.getFkNamesIdentificationId())
                .cityId(person.getCityId())
                .personBirthdate(person.getPersonBirthdate())
                .personCellphone(person.getPersonCellphone())
                .personEmail(person.getPersonEmail())
                .personIdentification(person.getPersonIdentification())
                .personLastname(person.getPersonLastname())
                .personName(person.getPersonName())
                .build();
        return p;
    }

    public Person personaDTOToPersonaEntity(Persona person){

        Person p = Person.builder()
                .fkNamesIdentificationId(person.getFkNamesIdentificationId())
                .cityId(person.getCityId())
                .personBirthdate(person.getPersonBirthdate())
                .personCellphone(person.getPersonCellphone())
                .personEmail(person.getPersonEmail())
                .personIdentification(person.getPersonIdentification())
                .personLastname(person.getPersonLastname())
                .personName(person.getPersonName())
                .build();

        return p;

    }

}
