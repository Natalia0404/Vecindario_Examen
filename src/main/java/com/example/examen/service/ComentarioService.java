package com.example.examen.service;

import com.example.examen.model.Comentario;
import com.example.examen.repository.ComentarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ComentarioService {

    private final ComentarioRepository comentarioRepository;

    public ComentarioService(ComentarioRepository comentarioRepository) {
        this.comentarioRepository = comentarioRepository;
    }

    public List<Comentario> listarComentarios() {
        return comentarioRepository.findAll();
    }

    public Optional<Comentario> buscarComentarioPorId(Integer id) {
        return comentarioRepository.findById(id);
    }

    public Comentario guardarComentario(Comentario comentario) {
        return comentarioRepository.save(comentario);
    }

    public void eliminarComentario(Integer id) {
        comentarioRepository.deleteById(id);
    }
}
