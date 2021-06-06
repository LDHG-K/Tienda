package com.Quda.Backend.Repositorio;

import com.Quda.Backend.Entidades.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCategoria extends JpaRepository<Category, Integer> {
}
