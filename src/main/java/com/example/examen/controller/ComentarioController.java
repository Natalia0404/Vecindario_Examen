package com.example.examen.controller;

import com.example.examen.model.Comentario;
import com.example.examen.service.ComentarioService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/comentarios")
public class ComentarioController {

    private final ComentarioService comentarioService;

    public ComentarioController(ComentarioService comentarioService) {
        this.comentarioService = comentarioService;
    }

    @GetMapping
    public List<Comentario> listarComentarios() {
        return comentarioService.listarComentarios();
    }

    @GetMapping("/{id}")
    public Optional<Comentario> buscarComentario(@PathVariable Integer id) {
        return comentarioService.buscarComentarioPorId(id);
    }

    @PostMapping
    public Comentario guardarComentario(@RequestBody Comentario comentario) {
        return comentarioService.guardarComentario(comentario);
    }

    @DeleteMapping("/{id}")
    public void eliminarComentario(@PathVariable Integer id) {
        comentarioService.eliminarComentario(id);
    }

    @PutMapping("/{id}")
    public Comentario actualizarComentario(@PathVariable Integer id, @RequestBody Comentario comentario) {
        return comentarioService.buscarComentarioPorId(id)
                .map(c -> {
                    c.setComContenido(comentario.getComContenido());
                    c.setUsuario(comentario.getUsuario());
                    c.setAviso(comentario.getAviso());
                    return comentarioService.guardarComentario(c);
                })
                .orElseThrow(() -> new RuntimeException("Comentario no encontrado con id " + id));
    }

}
