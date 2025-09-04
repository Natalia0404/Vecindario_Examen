package com.example.examen.controller;

import com.example.examen.model.Aviso;
import com.example.examen.model.Comentario;
import com.example.examen.model.Usuario;
import com.example.examen.repository.AvisoRepository;
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
    private final AvisoRepository avisoRepository; // <-- inyecta el repo

    public ComentarioController(ComentarioService comentarioService,
                                NotificacionService notificacionService,
                                AvisoRepository avisoRepository) {
        this.comentarioService = comentarioService;
        this.notificacionService = notificacionService;
        this.avisoRepository = avisoRepository;
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

        // Recarga el aviso desde BD para tener el dueño garantizado
        Integer avisoId = guardado.getAviso().getAviId();
        Aviso avisoDB = avisoRepository.findById(avisoId)
                .orElseThrow(() -> new RuntimeException("Aviso no encontrado: " + avisoId));

        Usuario duenio = avisoDB.getUsuario();
        Usuario autorComentario = guardado.getUsuario();

        if (duenio != null && autorComentario != null && !duenio.getUsuId().equals(autorComentario.getUsuId())) {
            String mensaje = "Nuevo comentario en tu aviso: \"" + avisoDB.getAviTitulo() + "\"";
            notificacionService.crear(mensaje, duenio, avisoDB);
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
