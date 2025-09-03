package com.example.examen.SecurityConfig;

import com.example.examen.repository.TokenRepository; // <-- 1. IMPORTACIÓN NUEVA
import com.example.examen.repository.UsuarioRepository;
import com.example.examen.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UsuarioRepository usuarioRepository;
    private final TokenRepository tokenRepository; // <-- 2. DEPENDENCIA NUEVA

    // 3. CONSTRUCTOR ACTUALIZADO
    public JwtAuthFilter(JwtService jwtService, UsuarioRepository usuarioRepository, TokenRepository tokenRepository) {
        this.jwtService = jwtService;
        this.usuarioRepository = usuarioRepository;
        this.tokenRepository = tokenRepository;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        jwt = authHeader.substring(7);
        userEmail = jwtService.extractUsername(jwt);

        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            usuarioRepository.findByUsuCorreo(userEmail)
                    .ifPresent(usuario -> {
                        UserDetails userDetails = new User(usuario.getUsuCorreo(), usuario.getUsuPassword(), Collections.emptyList());

                        // 4. LÓGICA DE VALIDACIÓN ACTUALIZADA
                        // Se comprueba si el token existe en nuestra base de datos
                        boolean isTokenValidInDb = tokenRepository.findByTokValor(jwt)
                                .map(t -> true) // Si el token se encuentra, es válido
                                .orElse(false); // Si no se encuentra, no es válido (ej. fue invalidado en logout)

                        // Ahora se comprueban AMBAS cosas: que el token sea criptográficamente válido
                        // Y que todavía exista en nuestra base de datos.
                        if (jwtService.isTokenValid(jwt, userDetails) && isTokenValidInDb) {
                            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities()
                            );
                            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                            SecurityContextHolder.getContext().setAuthentication(authToken);
                        }
                    });
        }
        filterChain.doFilter(request, response);
    }
}

