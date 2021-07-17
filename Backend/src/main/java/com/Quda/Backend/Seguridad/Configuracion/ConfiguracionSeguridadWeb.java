package com.Quda.Backend.Seguridad.Configuracion;

import com.Quda.Backend.LoginApp.Service.UserPrincipalDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@AllArgsConstructor
@EnableWebSecurity
public class ConfiguracionSeguridadWeb extends WebSecurityConfigurerAdapter {

    private final UserPrincipalDetailsService service;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
       http
               .csrf().disable()
               .authorizeRequests()
               .antMatchers("/Usuarios/Registro/**").permitAll()
               .antMatchers("/Productos/**").permitAll()
               .anyRequest()
               .authenticated().and()
               .httpBasic();
                //.formLogin();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(bCryptPasswordEncoder);
        provider.setUserDetailsService(service);
        return provider;
    }
}
