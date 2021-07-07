package com.Quda.Backend.LoginApp.Service;

import com.Quda.Backend.LoginApp.UserModel.UserPrincipal;
import com.Quda.Backend.TiendaApp.Entidad.User;
import com.Quda.Backend.TiendaApp.Repositorio.JpaDetalleRol;
import com.Quda.Backend.TiendaApp.Repositorio.JpaUsuario;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserPrincipalDetailsService implements UserDetailsService {

    private final JpaUsuario jpaUsuario;
    private final JpaDetalleRol jpaDetalleRol;
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        User user = jpaUsuario.findById(s).orElseThrow(()->
                new UsernameNotFoundException("Usuario no existe"));

        UserPrincipal userPrincipal = new UserPrincipal(user, jpaDetalleRol);
        return userPrincipal;

     }
    }
