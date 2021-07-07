package com.Quda.Backend.TiendaApp.Entidad;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "role_permissions")
public class RolePermissions {

    @EmbeddedId
    private RolePermissionsPK rolePermissionsPK;

    @ManyToOne
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JoinColumn(name="id_role", updatable = false,insertable = false)
    private Role roleidfk;

    //bi-directional many-to-one association to Product
    @ManyToOne
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JoinColumn(name="id_permission", updatable = false,insertable = false)
    private Permissions permissionsidfk;

}
