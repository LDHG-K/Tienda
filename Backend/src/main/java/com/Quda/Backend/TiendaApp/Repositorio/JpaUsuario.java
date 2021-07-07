package com.Quda.Backend.TiendaApp.Repositorio;

import com.Quda.Backend.TiendaApp.Entidad.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface JpaUsuario extends JpaRepository<User,String> {

    @Modifying
    @Query(value = "Update Users Set user_nick_name = :id_user Where user_nick_name = :id_user_old", nativeQuery = true)
    Void cambiarIdUsuario(@Param("id_user") String id_user,@Param("id_user_old") String id_user_old);

}
