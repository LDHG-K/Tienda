package com.Quda.Backend.TiendaApp.Repositorio;

import com.Quda.Backend.TiendaApp.Entidad.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface JpaProveedor extends JpaRepository<Supplier,Integer> {

    @Query(value = "SELECT supplier_id FROM supplier WHERE supplier_name = :supplier",nativeQuery = true)
    Integer darIdPorProveedorNombre(@Param("supplier") String supplier);

}
