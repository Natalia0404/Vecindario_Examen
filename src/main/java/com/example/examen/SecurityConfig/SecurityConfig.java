package com.example.examen.SecurityConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    public SecurityConfig(JwtAuthFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 1. Desactivamos la protección CSRF, que no es necesaria para APIs REST sin estado.
                .csrf(csrf -> csrf.disable())

                // 2. Definimos las reglas de autorización para las peticiones HTTP.
                .authorizeHttpRequests(auth -> auth
                        // 2a. Estas son las "Puertas Abiertas": cualquiera puede acceder a estas rutas.
                        .requestMatchers("/api/auth/**", "/api/usuarios/registro").permitAll()

                        // 2b. Esta es la regla "Solo para miembros": cualquier otra petición requiere autenticación.
                        .anyRequest().authenticated()
                )

                // 3. Le decimos a Spring que no cree ni gestione sesiones. Cada petición es independiente.
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // 4. Le damos la instrucción más importante: "Usa nuestro bouncer personalizado (JwtAuthFilter)
                //    antes de que el bouncer por defecto de Spring haga su trabajo".
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}