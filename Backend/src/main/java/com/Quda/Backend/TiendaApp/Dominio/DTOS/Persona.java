package com.Quda.Backend.TiendaApp.Dominio.DTOS;


import lombok.Builder;
import lombok.Getter;
import java.util.Date;
@Builder
@Getter
public class Persona {

    /**
    *TIPO DE DOCUMENTO
     */
    private Integer fkNamesIdentificationId;

    /**
     * FECHA DE NACIMIENTO
     */
    private Date personBirthdate;

    /**
     * TELEFONO
     */
    private String personCellphone;

    /**
     * EMAIL
     */
    private String personEmail;

    /**
     * IDENTIFICACION
     */
    private String personIdentification;

    /**
     * APELLIDOS
     */
    private String personLastname;

    /**
     * NOMBRES
     */
    private String personName;

    /**
     * CIUDAD
     */
    private Integer cityId;

}
