package com.Quda.Backend.Servicio;

import com.Quda.Backend.Entidades.Person;
import com.Quda.Backend.Entidades.User;
import com.Quda.Backend.Repositorio.JpaPersona;
import com.Quda.Backend.Repositorio.JpaUsuario;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ServicioUsuario {


    private final JpaUsuario jpaUsuario;
    private final JpaPersona jpaPersona;

    public Optional<User> buscarUsuario(String id){
        return jpaUsuario.findById(id);
    }

    public Optional<User> eliminarUsuario(String id){
        User usuarioBuscado = validarUsuario(id).get();
        usuarioBuscado.setStateId(2);
        return Optional.ofNullable( jpaUsuario.save(usuarioBuscado));
    }

    @Transactional
    public Optional<User> crearUsuario(User usuario){
        usuario.getPerson().setPersonCreationDate(new Date());
        Person person = jpaPersona.save(usuario.getPerson());

        if (!jpaUsuario.findById(usuario.getUserNickName()).isEmpty()){
            throw new RuntimeException("Usuario "+ usuario.getUserNickName() + " Ya existe");
        }

        usuario.setStateId(1);
        usuario.setPersonId(person.getPersonId());
        return Optional.ofNullable(jpaUsuario.save(usuario));
    }

    public List<User> listarUsuarios(){
        return jpaUsuario.findAll();
    }

    public Optional<User> editarUsuario(User usuario){
        return Optional.ofNullable(jpaUsuario.save(usuario));
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




}
