package com.Quda.Backend.Repositorio;

import com.Quda.Backend.Entidades.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JpaProducto extends JpaRepository<Product,Integer> {

    @Query(value = "SELECT * FROM products WHERE fk_category_id = :category_id AND product_stock > 0", nativeQuery = true)
    List<Product> listarPorCategoria(@Param("category_id") Integer category_id);

    @Query(value = "SELECT * FROM products WHERE fk_supplier_id = :proveedor AND product_stock > 0", nativeQuery = true)
    List<Product> listarPorProveedor(@Param("proveedor")Integer proveedor);

    @Query(value = "SELECT * FROM products WHERE fk_typestype_id = :objetivo AND product_stock > 0", nativeQuery = true)
    List<Product> listarPorObjetivo(@Param("objetivo") Integer objetivo);

    @Query(value = "SELECT * FROM products WHERE product_stock > 0",nativeQuery = true)
    List<Product> listarDisponibles();
}
