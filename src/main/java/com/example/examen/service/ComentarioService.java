package com.example.examen.service;

import com.example.examen.model.Aviso;
import com.example.examen.model.Comentario;
import com.example.examen.model.Usuario;
import com.example.examen.repository.AvisoRepository;
import com.example.examen.repository.ComentarioRepository;
import com.example.examen.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ComentarioService {

    private final ComentarioRepository comentarioRepository;
    private final AvisoRepository avisoRepository;
    private final UsuarioRepository usuarioRepository;
    private final NotificacionService notificacionService;


    public ComentarioService(ComentarioRepository comentarioRepository,
                             AvisoRepository avisoRepository,
                             UsuarioRepository usuarioRepository,
                             NotificacionService notificacionService) {
        this.comentarioRepository = comentarioRepository;
        this.avisoRepository = avisoRepository;
        this.usuarioRepository = usuarioRepository;
        this.notificacionService = notificacionService;
    }

    public List<Comentario> listarComentarios() {
        return comentarioRepository.findAll();
    }

    public Optional<Comentario> buscarComentarioPorId(Integer id) {
        return comentarioRepository.findById(id);
    }

    /**
     * Guarda un comentario y, si corresponde,
     * crea una notificación para el dueño del aviso.
     */
    @Transactional
    public Comentario guardarComentario(Comentario comentario) {
        // Guardar comentario
        Comentario guardado = comentarioRepository.save(comentario);

        // Recargar el aviso desde BD para obtener el dueño
        Integer avisoId = guardado.getAviso().getAviId();
        Aviso avisoDB = avisoRepository.findById(avisoId)
                .orElseThrow(() -> new RuntimeException("Aviso no encontrado: " + avisoId));

        Usuario duenio = avisoDB.getUsuario();
        Usuario autorComentario = guardado.getUsuario();

        // Crear notificación solo si el autor es distinto al dueño
        if (duenio != null && autorComentario != null &&
                !duenio.getUsuId().equals(autorComentario.getUsuId())) {
            String mensaje = "Nuevo comentario en tu aviso: \"" + avisoDB.getAviTitulo() + "\"";
            notificacionService.crear(mensaje, duenio, avisoDB);
        }

        return guardado;
    }

    public void eliminarComentario(Integer id) {
        comentarioRepository.deleteById(id);
    }
}
