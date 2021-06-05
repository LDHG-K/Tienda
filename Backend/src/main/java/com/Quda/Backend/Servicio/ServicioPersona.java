package com.Quda.Backend.Servicio;

import com.Quda.Backend.Entidades.Person;
import com.Quda.Backend.Entidades.User;
import com.Quda.Backend.Repositorio.JpaPersona;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ServicioPersona {

    private final JpaPersona jpaPersona;
    private final ServicioUsuario servicioUsuario;

    public Optional<Person> buscarPersona(Integer id){
        Optional<Person> buscado = jpaPersona.findById(id);
        if (buscado.isEmpty()){throw new RuntimeException("No encontrado");}
        return buscado;
    }
    public void eliminarPersona(Integer id){

        try{jpaPersona.delete(buscarPersona(id).get());}
        catch (RuntimeException e){throw new RuntimeException("Persona no existe");}

    }

    public List<Person> enlistarPersonas(){
        return jpaPersona.findAll();
    }

    public Person editarPersona(Person personaEditada, String id){

        Optional<User> usuario = servicioUsuario.buscarUsuario(id);
        Integer idPersona = usuario.get().getPersonId();
        personaEditada.setPersonId(idPersona);
        usuario.get().setPerson(personaEditada);

        return jpaPersona.save(personaEditada);
    }


}
