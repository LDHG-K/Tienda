package com.Quda.Backend.TiendaApp.Dominio.DTOS;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.Column;
import javax.validation.constraints.Size;

@Builder
@Getter
public class Usuario {
    /**
     * NOMBRE USUARIO
     */
    private String userNickName;
    /**
     * CONTRASENIA
     */
    private String userPassword;
    /**
     * PERSONA DEL USUARIO
     */
    private Persona userPerson;
}
