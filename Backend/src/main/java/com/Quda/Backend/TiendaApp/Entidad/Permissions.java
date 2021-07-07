package com.Quda.Backend.TiendaApp.Entidad;


import javax.persistence.*;

@Entity
@Table(name = "permissions")
public class Permissions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "permission_id")
    private Long permissionId;

    @Column (name = "permission_name")
    private String permissionName;

}
