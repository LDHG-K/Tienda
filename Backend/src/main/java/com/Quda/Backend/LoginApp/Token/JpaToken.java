package com.Quda.Backend.LoginApp.Token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface JpaToken extends JpaRepository<TokenConfirmacion,Long> {

    @Query(value = "SELECT * FROM confirmation_token WHERE token_name = :token", nativeQuery = true)
    TokenConfirmacion buscarToken(@Param("token") String token);

}
