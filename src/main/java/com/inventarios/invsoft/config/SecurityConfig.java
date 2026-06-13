package com.inventarios.invsoft.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // 1. Autorización de peticiones
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/css/**", "/js/**").permitAll() // Permite cargar estilos sin loguearse
                .anyRequest().authenticated() // CUALQUIER otra ruta (como /productos) requiere login
            )
            // 2. Configurar el formulario de Login nativo
            .formLogin(form -> form
                .defaultSuccessUrl("/productos", true) // Si el login es correcto, va al inventario
                .permitAll()
            )
            // 3. Configurar el Logout (Cierre de sesión)
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            );

        return http.build();
    }
}
