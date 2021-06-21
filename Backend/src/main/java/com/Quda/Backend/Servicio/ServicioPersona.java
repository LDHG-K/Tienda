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


    public Person editarPersona(Person personaEditada, String id){

        Optional<User> usuario = servicioUsuario.buscarUsuario(id);

        Person personaAntigua = usuario.get().getPerson();

        personaEditada.setPersonId(personaAntigua.getPersonId());
        personaEditada.setPersonCreationDate(personaAntigua.getPersonCreationDate());
        personaEditada.setRoleId(personaAntigua.getRoleId());

        System.out.println( personaEditada.toString());


        return jpaPersona.save(personaEditada);
    }

    //LISTAR =========================================================================

    public List<Person> enlistarPersonas(Integer estado){
        if (estado<=0 || estado>9){
            throw new RuntimeException("Estado no Existe");
        }
        return jpaPersona.buscarPersonasPorEstado(estado);
    }

}
