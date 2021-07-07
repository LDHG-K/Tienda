package com.Quda.Backend.TiendaApp.Repositorio;

import com.Quda.Backend.TiendaApp.Entidad.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface JpaCategoria extends JpaRepository<Category, Integer> {

    @Query(value = "SELECT category_id FROM categories WHERE category_name = :category ",nativeQuery = true)
    Integer buscarIdPorCategoria(@Param("category") String category);

}
