package com.example.examen.controller;

import com.example.examen.dto.ComentarioCreateDTO;
import com.example.examen.dto.ComentarioUpdateDTO;
import com.example.examen.model.Aviso;
import com.example.examen.model.Comentario;
import com.example.examen.model.Usuario;
import com.example.examen.repository.AvisoRepository;
import com.example.examen.repository.UsuarioRepository;
import com.example.examen.service.ComentarioService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/comentarios")
public class ComentarioController {

    private final ComentarioService comentarioService;
    private final UsuarioRepository usuarioRepository;
    private final AvisoRepository avisoRepository;

    public ComentarioController(ComentarioService comentarioService,
                                UsuarioRepository usuarioRepository,
                                AvisoRepository avisoRepository) {
        this.comentarioService = comentarioService;
        this.usuarioRepository = usuarioRepository;
        this.avisoRepository = avisoRepository;
    }

    // CREATE: crea comentario y (en el service) genera notificación al dueño del aviso
    @PostMapping
    public ResponseEntity<Comentario> crear(@Valid @RequestBody ComentarioCreateDTO dto) {
        Usuario autor = usuarioRepository.findById(dto.getUsuId())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no existe: " + dto.getUsuId()));
        Aviso aviso = avisoRepository.findById(dto.getAviId())
                .orElseThrow(() -> new IllegalArgumentException("Aviso no existe: " + dto.getAviId()));

        Comentario c = new Comentario();
        c.setUsuario(autor);
        c.setAviso(aviso);
        c.setComContenido(dto.getComContenido());

        Comentario guardado = comentarioService.guardarComentario(c); // aquí adentro se crea la notificación
        return ResponseEntity.created(URI.create("/api/comentarios/" + guardado.getComId()))
                .body(guardado);
    }

    // READ: listar todos
    @GetMapping
    public ResponseEntity<List<Comentario>> listar() {
        return ResponseEntity.ok(comentarioService.listarComentarios());
    }

    // READ: por id
    @GetMapping("/{id}")
    public ResponseEntity<Comentario> porId(@PathVariable Integer id) {
        return comentarioService.buscarComentarioPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // READ: listar por Aviso
    @GetMapping("/aviso/{aviId}")
    public ResponseEntity<List<Comentario>> porAviso(@PathVariable Integer aviId) {
        // Si tienes un método repo para esto, úsalo; si no, filtra luego.
        List<Comentario> todos = comentarioService.listarComentarios();
        List<Comentario> filtrados = todos.stream()
                .filter(c -> c.getAviso() != null && c.getAviso().getAviId().equals(aviId))
                .toList();
        return ResponseEntity.ok(filtrados);
    }

    // READ: listar por Usuario
    @GetMapping("/usuario/{usuId}")
    public ResponseEntity<List<Comentario>> porUsuario(@PathVariable Integer usuId) {
        List<Comentario> todos = comentarioService.listarComentarios();
        List<Comentario> filtrados = todos.stream()
                .filter(c -> c.getUsuario() != null && c.getUsuario().getUsuId().equals(usuId))
                .toList();
        return ResponseEntity.ok(filtrados);
    }

    // UPDATE (PUT): solo actualiza el contenido del comentario
    @PutMapping("/{id}")
    public ResponseEntity<Comentario> actualizar(@PathVariable Integer id,
                                                 @Valid @RequestBody ComentarioUpdateDTO dto) {
        Comentario existente = comentarioService.buscarComentarioPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Comentario no encontrado: " + id));

        existente.setComContenido(dto.getComContenido());
        Comentario actualizado = comentarioService.guardarComentario(existente);
        return ResponseEntity.ok(actualizado);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        comentarioService.eliminarComentario(id);
        return ResponseEntity.noContent().build();
    }
}