package com.Quda.Backend.Repositorio;

import com.Quda.Backend.Entidades.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaUsuario extends JpaRepository<User,String> {
}
