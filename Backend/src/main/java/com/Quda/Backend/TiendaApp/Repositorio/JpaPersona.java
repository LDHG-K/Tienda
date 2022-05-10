package com.Quda.Backend.TiendaApp.Repositorio;


import com.Quda.Backend.TiendaApp.Entidad.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JpaPersona extends JpaRepository<Person,Integer> {

    @Query(value = "SELECT person_id, person_name, person_lastname, person_email, person_cellphone, person_birthdate, person_creation_date, person_identification, fk_names_identification_id, fk_city_id" +
            " FROM persons, users WHERE users.fk_person_id = persons.person_id AND users.fk_state_id = :state", nativeQuery = true)
    List<Person> buscarPersonasPorEstado(@Param("state") Integer state);

}
