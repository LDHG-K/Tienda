package com.Quda.Backend.TiendaApp.Servicio;

import com.Quda.Backend.TiendaApp.Dominio.DTOS.Persona;
import com.Quda.Backend.TiendaApp.Entidad.Person;
import com.Quda.Backend.TiendaApp.Entidad.User;
import com.Quda.Backend.TiendaApp.Repositorio.JpaPersona;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ServicioPersona {

    private final JpaPersona jpaPersona;
    private final ServicioUsuario servicioUsuario;

    //CRUD ==========================================================================

    public Optional<Person> buscarPersona(String id){
        Optional<User> buscado = servicioUsuario.buscarUsuario(id);
        if (buscado.isEmpty()){throw new RuntimeException("No encontrado");}
        return Optional.of(buscado.get().getPerson());
    }

    /* OPCIONAL BORRAR EN CASCADA
    public void eliminarPersona(Integer id){

        try{jpaPersona.delete(buscarPersona(id).get());}
        catch (RuntimeException e){throw new RuntimeException("Persona no existe");}

    }
    */


    public Optional<Person> editarPersona(Person personaEditada, String id){

        Optional<User> usuario = servicioUsuario.buscarUsuario(id);

        if (usuario.isEmpty()){
            throw  new RuntimeException("Usuario no existe");
        }

        Person personaAntigua = usuario.get().getPerson();

        personaEditada.setPersonId(personaAntigua.getPersonId());
        personaEditada.setPersonCreationDate(personaAntigua.getPersonCreationDate());


        System.out.println( personaEditada.toString());

        return Optional.ofNullable(jpaPersona.save(personaEditada));
    }



    //LISTAR =========================================================================

    public List<Persona> enlistarPersonas(Integer estado){

        if (estado<=0 || estado>9){
            throw new RuntimeException("Estado no Existe");
        }
        List<Person> p = jpaPersona.buscarPersonasPorEstado(estado);

        List<Persona> lp = new ArrayList<>();
        p.forEach(person -> {
            lp.add(servicioUsuario.personaEntityToPersonaDTO(person));
        });

        return lp;
    }



}
