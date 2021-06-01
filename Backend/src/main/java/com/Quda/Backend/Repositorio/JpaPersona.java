package com.Quda.Backend.Repositorio;

import com.Quda.Backend.Entidades.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaPersona extends JpaRepository<Person,Integer> {
}
