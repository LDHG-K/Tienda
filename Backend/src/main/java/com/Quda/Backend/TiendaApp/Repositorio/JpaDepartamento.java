package com.Quda.Backend.TiendaApp.Repositorio;

import com.Quda.Backend.TiendaApp.Entidad.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaDepartamento extends JpaRepository<Department, Integer> {
}
