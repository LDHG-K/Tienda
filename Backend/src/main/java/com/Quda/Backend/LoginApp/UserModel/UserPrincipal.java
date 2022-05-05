package com.Quda.Backend.LoginApp.UserModel;

import com.Quda.Backend.TiendaApp.Entidad.RolePermissions;
import com.Quda.Backend.TiendaApp.Entidad.User;
import com.Quda.Backend.TiendaApp.Repositorio.JpaDetalleRol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


public class UserPrincipal implements UserDetails {

    public User user;
    private final JpaDetalleRol jpaDetalleRol ;

    public UserPrincipal(User user, JpaDetalleRol jpaDetalleRol){
        this.user=user;
        this.jpaDetalleRol = jpaDetalleRol;

    }




    public List<Long> listaDePermisos(){
        List<RolePermissions> permisosDeRol = jpaDetalleRol.buscarRolPorPermisos(user.getUserRole());
        List<Long> res = permisosDeRol.stream().map(rolePermissions -> rolePermissions.getRolePermissionsPK().getPermissionId()).collect(Collectors.toList());
        return res;
    }



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        List<GrantedAuthority> authorities = new ArrayList<>();

        //EXTRAE LA LISTA DE REFERENCIA DE LOS ROLES
        listaDePermisos().forEach(p ->
        {
            GrantedAuthority authority = new SimpleGrantedAuthority(p+"");
            authorities.add(authority);
        });

        GrantedAuthority rolAuthority = new SimpleGrantedAuthority("ROLE_"+user.getUserRole());
        authorities.add(rolAuthority);

        return authorities;
    }

    @Override
    public String getPassword() {
        return this.user.getUserPassword();
    }

    @Override
    public String getUsername() {
        return this.user.getUserNickName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.user.getLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.user.getEnabled();
    }
}
