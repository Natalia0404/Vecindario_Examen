package com.example.examen.controller;

import com.example.examen.dto.NotificacionCreateDTO;
import com.example.examen.dto.NotificacionResponseDTO;
import com.example.examen.model.Aviso;
import com.example.examen.model.Notificacion;
import com.example.examen.model.Usuario;
import com.example.examen.repository.AvisoRepository;
import com.example.examen.repository.UsuarioRepository;
import com.example.examen.service.NotificacionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/notificaciones")
public class NotificacionController {

    private final NotificacionService notificacionService;
    private final UsuarioRepository usuarioRepository;
    private final AvisoRepository avisoRepository;

    public NotificacionController(NotificacionService notificacionService,
                                  UsuarioRepository usuarioRepository,
                                  AvisoRepository avisoRepository) {
        this.notificacionService = notificacionService;
        this.usuarioRepository = usuarioRepository;
        this.avisoRepository = avisoRepository;
    }

    // CREATE
    @PostMapping
    public ResponseEntity<NotificacionResponseDTO> crear(@Valid @RequestBody NotificacionCreateDTO dto) {
        Usuario usuario = usuarioRepository.findById(dto.getUsuId())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no existe: " + dto.getUsuId()));
        Aviso aviso = avisoRepository.findById(dto.getAviId())
                .orElseThrow(() -> new IllegalArgumentException("Aviso no existe: " + dto.getAviId()));

        Notificacion n = notificacionService.crear(dto.getMensaje(), usuario, aviso);

        NotificacionResponseDTO resp = new NotificacionResponseDTO(
                n.getNotId(), n.getNotMensaje(), n.getNotFecha(), n.getNotLeida(),
                n.getUsuario().getUsuId(), n.getAviso().getAviId()
        );
        return ResponseEntity.created(URI.create("/api/notificaciones/" + n.getNotId())).body(resp);
    }


    @GetMapping
    public List<NotificacionResponseDTO> listar() {
        return notificacionService.listar().stream()
                .map(n -> new NotificacionResponseDTO(
                        n.getNotId(), n.getNotMensaje(), n.getNotFecha(), n.getNotLeida(),
                        n.getUsuario().getUsuId(), n.getAviso().getAviId()))
                .toList();
    }


    @GetMapping("/{id}")
    public NotificacionResponseDTO porId(@PathVariable Integer id) {
        Notificacion n = notificacionService.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Notificación no encontrada: " + id));
        return new NotificacionResponseDTO(
                n.getNotId(), n.getNotMensaje(), n.getNotFecha(), n.getNotLeida(),
                n.getUsuario().getUsuId(), n.getAviso().getAviId());
    }

    // por usuario
    @GetMapping("/usuario/{usuId}")
    public List<NotificacionResponseDTO> porUsuario(@PathVariable Integer usuId) {
        return notificacionService.listarPorUsuario(usuId).stream()
                .map(n -> new NotificacionResponseDTO(
                        n.getNotId(), n.getNotMensaje(), n.getNotFecha(), n.getNotLeida(),
                        n.getUsuario().getUsuId(), n.getAviso().getAviId()))
                .toList();
    }

    // por aviso
    @GetMapping("/aviso/{aviId}")
    public List<NotificacionResponseDTO> porAviso(@PathVariable Integer aviId) {
        return notificacionService.listarPorAviso(aviId).stream()
                .map(n -> new NotificacionResponseDTO(
                        n.getNotId(), n.getNotMensaje(), n.getNotFecha(), n.getNotLeida(),
                        n.getUsuario().getUsuId(), n.getAviso().getAviId()))
                .toList();
    }

    /*// UPDATE parcial - marcar leída
    @PatchMapping("/{id}/leida")
    public NotificacionResponseDTO marcarLeida(@PathVariable Integer id) {
        Notificacion n = notificacionService.marcarLeida(id);
        return new NotificacionResponseDTO(
                n.getNotId(), n.getNotMensaje(), n.getNotFecha(), n.getNotLeida(),
                n.getUsuario().getUsuId(), n.getAviso().getAviId());
    }*/

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        notificacionService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}