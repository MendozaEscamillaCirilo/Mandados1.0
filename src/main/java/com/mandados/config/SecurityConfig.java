package com.mandados.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;	
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

@Configuration	
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    protected void configure(HttpSecurity http) throws Exception {			
        http.authorizeRequests()
        .antMatchers("/home*").authenticated()
        .antMatchers("/getproductosabc").authenticated()
        .antMatchers("/configurar").authenticated()
        .antMatchers("/eliminar*").authenticated()
        .antMatchers("/eliminarcategoria*").authenticated()
        .antMatchers("/eliminarorden*").authenticated()
        .antMatchers("/eliminarproducto*").authenticated()
        .antMatchers("/eliminarusuario*").authenticated()
        .antMatchers("/eliminarrepartidor*").authenticated()
        .antMatchers("/buscarproducto").permitAll()
        .antMatchers("/editarusuario*").authenticated()
        .antMatchers("/editarcontrasenia*").authenticated()
        .antMatchers("/editarfoto*").authenticated()
        .antMatchers("/usuarios*").hasAuthority("ROL_ADMIN")
        .antMatchers("/roles*").hasAuthority("ROL_ADMIN")
        .antMatchers("/listacomercio").hasAnyAuthority("ROL_ADMIN","ROL_CALLCENTER")
        .antMatchers("/registroauthority").hasAnyAuthority("ROL_ADMIN","ROL_CALLCENTER")
        .antMatchers("/registrorepartidor").hasAnyAuthority("ROL_ADMIN","ROL_CALLCENTER")
        .antMatchers("/registrousuario").hasAuthority("ROL_ADMIN")
        .antMatchers("/listarestaurante").hasAuthority("ROL_ADMIN")
        .antMatchers("/callcenter").hasAuthority("ROL_ADMIN")
        .antMatchers("/listatienda").hasAuthority("ROL_ADMIN")
        .antMatchers("/listacatalogo").hasAnyAuthority("ROL_ADMIN","ROL_COMERCIO")
        .antMatchers("/asignarcategoria").hasAuthority("ROL_COMERCIO")
        .antMatchers("/registroproducto").hasAuthority("ROL_COMERCIO")
        .antMatchers("/listaproducto").hasAnyAuthority("ROL_ADMIN","ROL_COMERCIO")
        .antMatchers("/listaorden").hasAnyAuthority("ROL_ADMIN","ROL_CALLCENTER","ROL_COMERCIO")
        .antMatchers("/listarcategoria").hasAnyAuthority("ROL_ADMIN","ROL_CALLCENTER","ROL_COMERCIO")
        .antMatchers("/registrocategoria").hasAnyAuthority("ROL_ADMIN","ROL_CALLCENTER","ROL_COMERCIO")
        .antMatchers("/listarepartidor").hasAnyAuthority("ROL_ADMIN","ROL_CALLCENTER")
        .antMatchers("/**", "/logout").permitAll()
        .antMatchers("/").permitAll()        
        .anyRequest().authenticated()
        .and()
        .formLogin().loginPage("/login")
        .permitAll()
        .defaultSuccessUrl("/home");
    }

    BCryptPasswordEncoder bCryptPasswordEncoder;
    //Crea el encriptador de contraseñas	
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
		bCryptPasswordEncoder = new BCryptPasswordEncoder(6);
    //El numero 4 representa que tan fuerte quieres la encriptacion.
    //Se puede en un rango entre 4 y 31. 
    //Si no pones un numero el programa utilizara uno aleatoriamente cada vez
    //que inicies la aplicacion, por lo cual tus contrasenas encriptadas no funcionaran bien
        return bCryptPasswordEncoder;
    }
	
    @Autowired
    UserDetailsServiceImpl userDetailsService;
	
    //Registra el service para usuarios y el encriptador de contrasena
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception { 
 
        // Setting Service to find User in the database.
        // And Setting PassswordEncoder
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());     
    }
}
