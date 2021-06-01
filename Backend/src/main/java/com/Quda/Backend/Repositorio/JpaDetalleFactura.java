package com.Quda.Backend.Repositorio;

import com.Quda.Backend.Entidades.BillsProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaDetalleFactura extends JpaRepository<BillsProduct,Integer> {

}
