package com.Quda.Backend.Repositorio;

import com.Quda.Backend.Entidades.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JpaFactura extends JpaRepository<Bill, Integer> {

    @Query(value = "SELECT * FROM bills WHERE fk_persons_id = :person", nativeQuery = true)
    List<Bill> buscarFacturaPorUsuario(@Param("person") Integer person);

}
