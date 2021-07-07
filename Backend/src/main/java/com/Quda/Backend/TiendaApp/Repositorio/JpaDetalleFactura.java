package com.Quda.Backend.TiendaApp.Repositorio;

import com.Quda.Backend.TiendaApp.Entidad.BillsProduct;
import com.Quda.Backend.TiendaApp.Entidad.BillsProductPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;


public interface JpaDetalleFactura extends JpaRepository<BillsProduct, BillsProductPK> {

   @Query(value = "SELECT * FROM bills_products WHERE fk_bills_id = :billId", nativeQuery = true)
    List<BillsProduct> buscarPorIdfactura(@Param("billId") Integer billId);

}