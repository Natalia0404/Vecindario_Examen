package com.example.examen.controller;

import com.example.examen.model.Aviso;
import com.example.examen.model.Comentario;
import com.example.examen.model.Usuario;
import com.example.examen.service.ComentarioService;
import com.example.examen.service.NotificacionService;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/comentarios")
public class ComentarioController {

    private final ComentarioService comentarioService;
    private final NotificacionService notificacionService;

    public ComentarioController(ComentarioService comentarioService,
                                NotificacionService notificacionService) {
        this.comentarioService = comentarioService;
        this.notificacionService = notificacionService;
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
    public Comentario guardarComentario(@Valid @RequestBody Comentario comentario) {
        Comentario guardado = comentarioService.guardarComentario(comentario);

        // Notificar al dueño del aviso cuando alguien comenta
        // 1. Obtenemos el aviso al que pertenece el comentario guardado
        Aviso aviso = guardado.getAviso();
        // 2. Identificamos al dueño del aviso (usuario que creó el aviso)
        Usuario duenio = aviso.getUsuario();
        // 3. Identificamos al autor del comentario recién guardado
        Usuario autorComentario = guardado.getUsuario();

        // 4. Si el dueño del aviso existe y es diferente del autor del comentario,
        //    entonces corresponde enviarle una notificación.
        if (duenio != null && autorComentario != null && !duenio.getUsuId().equals(autorComentario.getUsuId())) {
            String mensaje = "Nuevo comentario en tu aviso: \"" + aviso.getAviTitulo() + "\"";
            notificacionService.crear(mensaje, duenio, aviso);
        }

        return guardado;
    }

    @PutMapping("/{id}")
    public Comentario actualizarComentario(@PathVariable Integer id, @Valid @RequestBody Comentario comentario) {
        return comentarioService.buscarComentarioPorId(id)
                .map(c -> {
                    c.setComContenido(comentario.getComContenido());
                    c.setUsuario(comentario.getUsuario());
                    c.setAviso(comentario.getAviso());
                    return comentarioService.guardarComentario(c);
                })
                .orElseThrow(() -> new RuntimeException("Comentario no encontrado con id " + id));
    }

    @DeleteMapping("/{id}")
    public void eliminarComentario(@PathVariable Integer id) {
        comentarioService.eliminarComentario(id);
    }
}
