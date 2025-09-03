package com.example.examen.service;

import com.example.examen.dto.AuthResponse;
import com.example.examen.dto.LoginRequest;
import com.example.examen.model.Token;
import com.example.examen.model.Usuario;
import com.example.examen.repository.TokenRepository;
import com.example.examen.repository.UsuarioRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final TokenRepository tokenRepository;

    public AuthService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder, JwtService jwtService, TokenRepository tokenRepository) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.tokenRepository = tokenRepository;
    }

    @Transactional
    public AuthResponse login(LoginRequest request) {
        // 1. Buscar al usuario por su correo.
        Usuario usuario = usuarioRepository.findByUsuCorreo(request.correo())
                .orElseThrow(() -> new BadCredentialsException("Usuario no encontrado o credenciales inválidas"));

        // 2. Comprobar si la contraseña proporcionada coincide con la encriptada.
        if (!passwordEncoder.matches(request.password(), usuario.getUsuPassword())) {
            throw new BadCredentialsException("Usuario no encontrado o credenciales inválidas");
        }

        // 3. Crear un UserDetails (la representación de Spring Security del usuario).
        UserDetails userDetails = new User(usuario.getUsuCorreo(), usuario.getUsuPassword(), Collections.emptyList());

        // 4. Generar el token JWT.
        String jwtToken = jwtService.generateToken(userDetails);

        // 5. Guardar el token en nuestra base de datos.
        guardarTokenUsuario(usuario, jwtToken);

        // 6. Devolver la respuesta con el token.
        return new AuthResponse(jwtToken, usuario.getUsuId());
    }

    @Transactional
    public void logout(String jwt) {
        // Buscar el token en la BD y eliminarlo si existe.
        tokenRepository.findByTokValor(jwt).ifPresent(tokenRepository::delete);
    }

    private void guardarTokenUsuario(Usuario usuario, String jwtToken) {
        Token token = new Token();
        token.setUsuario(usuario);
        token.setTokValor(jwtToken);

        // La fecha de expiración la extraemos del propio token
        // NOTA: Esta es una implementación simple. JwtService tendría que exponer un método para extraer la fecha.
        // Por ahora, para simplificar, podemos poner una fecha calculada.
        long expirationMillis = 86400000; // 24 horas (debería venir de application.properties)
        token.setTokFechaExpira(LocalDateTime.now().plusSeconds(expirationMillis / 1000));

        tokenRepository.save(token);
    }
}
