package com.example.examen.controller;

import com.example.examen.model.Usuario;
import com.example.examen.service.UsuarioService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public List<Usuario> listarUsuarios() {
        return usuarioService.listarUsuarios();
    }

    @GetMapping("/{id}")
    public Optional<Usuario> buscarUsuario(@PathVariable Integer id) {
        return usuarioService.buscarUsuarioPorId(id);
    }

    @PostMapping
    public Usuario guardarUsuario(@RequestBody Usuario usuario) {
        return usuarioService.guardarUsuario(usuario);
    }

    @DeleteMapping("/{id}")
    public void eliminarUsuario(@PathVariable Integer id) {
        usuarioService.eliminarUsuario(id);
    }

    @PutMapping("/{id}")
    public Usuario actualizarUsuario(@PathVariable Integer id, @RequestBody Usuario usuario) {
        return usuarioService.buscarUsuarioPorId(id)
                .map(u -> {
                    u.setUsuNombre(usuario.getUsuNombre());
                    u.setUsuCorreo(usuario.getUsuCorreo());
                    u.setUsuPassword(usuario.getUsuPassword());
                    u.setUsuDireccion(usuario.getUsuDireccion());
                    u.setUsuActivo(usuario.getUsuActivo());
                    return usuarioService.guardarUsuario(u);
                })
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id " + id));
    }
}
