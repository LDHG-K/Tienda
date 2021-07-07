package com.Quda.Backend.TiendaApp.Entidad;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Embeddable
@Data
public class RolePermissionsPK implements Serializable {

    @Column(name = "id_role", insertable=false, updatable=false)
    private Long roleId;

    @Column(name = "id_permission", insertable=false, updatable=false)
    private Long permissionId;

}
