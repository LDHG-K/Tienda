package com.Quda.Backend.TiendaApp.Repositorio;

import com.Quda.Backend.TiendaApp.Entidad.BillsProduct;
import com.Quda.Backend.TiendaApp.Entidad.RolePermissions;
import com.Quda.Backend.TiendaApp.Entidad.RolePermissionsPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface JpaDetalleRol extends JpaRepository<RolePermissions, RolePermissionsPK> {

    @Query(value = "SELECT * FROM role_permissions WHERE id_role = :rolid", nativeQuery = true)
    List<RolePermissions> buscarRolPorPermisos(@Param("rolid") Long rolid);

}
