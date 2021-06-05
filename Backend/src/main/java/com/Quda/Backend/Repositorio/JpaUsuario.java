package com.Quda.Backend.Repositorio;

import com.Quda.Backend.Entidades.Product;
import com.Quda.Backend.Entidades.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JpaUsuario extends JpaRepository<User,String> {

    @Modifying
    @Query(value = "Update Users Set user_nick_name = :id_user Where user_nick_name = :id_user_old", nativeQuery = true)
    Void cambiarIdUsuario(@Param("id_user") String id_user,@Param("id_user_old") String id_user_old);

}
