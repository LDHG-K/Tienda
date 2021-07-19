package com.Quda.Backend.TiendaApp.Repositorio;

import com.Quda.Backend.TiendaApp.Entidad.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaCiudad extends JpaRepository<City,Integer> { }
