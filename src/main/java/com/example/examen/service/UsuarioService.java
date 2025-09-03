package com.example.examen.service;

import com.example.examen.model.Usuario;
import com.example.examen.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    // Inyección de dependencias por constructor (tu estilo preferido)
    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Usuario> getAllUsuarios() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> getUsuarioById(Integer id) {
        return usuarioRepository.findById(id);
    }

    public Usuario createUsuario(Usuario usuario) {
        // Encriptamos la contraseña ANTES de guardarla en la base de datos
        String passwordEncriptado = passwordEncoder.encode(usuario.getUsuPassword());
        usuario.setUsuPassword(passwordEncriptado);
        return usuarioRepository.save(usuario);
    }

    public Usuario updateUsuario(Integer id, Usuario usuarioDetails) {
        return usuarioRepository.findById(id)
                .map(usuarioExistente -> {
                    usuarioExistente.setUsuNombre(usuarioDetails.getUsuNombre());
                    usuarioExistente.setUsuCorreo(usuarioDetails.getUsuCorreo());
                    usuarioExistente.setUsuDireccion(usuarioDetails.getUsuDireccion());
                    // Nota: No actualizamos la contraseña aquí. Eso debería ser un endpoint separado.
                    return usuarioRepository.save(usuarioExistente);
                })
                .orElse(null); // O lanzar una excepción si se prefiere
    }

    public boolean deleteUsuario(Integer id) {
        return usuarioRepository.findById(id).map(usuario -> {
            usuario.setUsuActivo(false);
            usuarioRepository.save(usuario);
            return true;
        }).orElse(false);
    }
}
