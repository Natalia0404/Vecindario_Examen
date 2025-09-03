package com.example.examen.controller;

import com.example.examen.model.Aviso;
import com.example.examen.model.Notificacion;
import com.example.examen.model.Usuario;
import com.example.examen.repository.AvisoRepository;
import com.example.examen.repository.UsuarioRepository;
import com.example.examen.service.NotificacionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController // Indica que esta clase es un controlador REST
@RequestMapping("/notificaciones") // Prefijo común para todos los endpoints
public class NotificacionController {

    // Inyección de dependencias
    private final NotificacionService notificacionService;
    private final UsuarioRepository usuarioRepository;
    private final AvisoRepository avisoRepository;

    // Constructor con inyección de dependencias
    public NotificacionController(NotificacionService notificacionService,
                                  UsuarioRepository usuarioRepository,
                                  AvisoRepository avisoRepository) {
        this.notificacionService = notificacionService;
        this.usuarioRepository = usuarioRepository;
        this.avisoRepository = avisoRepository;
    }

    /**
     * Crear manualmente una notificación.
     * Endpoint de prueba que recibe un JSON con mensaje, usuId y opcionalmente aviId.
     *
     * Ejemplo de request:
     * {
     *   "mensaje": "Nueva alerta en tu vecindario",
     *   "usuId": 1,
     *   "aviId": 2
     * }
     */
    @PostMapping
    public Notificacion crear(@RequestBody Map<String, Object> body) {
        String mensaje = (String) body.get("mensaje");
        Integer usuId = (Integer) body.get("usuId");
        Integer aviId = body.get("aviId") != null ? (Integer) body.get("aviId") : null;

        // Buscar usuario (obligatorio)
        Usuario usuario = usuarioRepository.findById(usuId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + usuId));

        // Buscar aviso (obligatorio)
        Aviso aviso = avisoRepository.findById(aviId)
                .orElseThrow(() -> new RuntimeException("Aviso no encontrado: " + aviId));

        // Delegar la creación al servicio
        return notificacionService.crear(mensaje, usuario, aviso);
    }

    /**
     * Listar todas las notificaciones.
     */
    @GetMapping
    public List<Notificacion> listar() {
        return notificacionService.listar();
    }

    /**
     * Listar notificaciones de un usuario específico.
     * @param usuId ID del usuario
     */
    @GetMapping("/usuario/{usuId}")
    public List<Notificacion> listarPorUsuario(@PathVariable Integer usuId) {
        return notificacionService.listarPorUsuario(usuId);
    }

    /**
     * Listar notificaciones asociadas a un aviso específico.
     * @param aviId ID del aviso
     */
    @GetMapping("/aviso/{aviId}")
    public List<Notificacion> listarPorAviso(@PathVariable Integer aviId) {
        return notificacionService.listarPorAviso(aviId);
    }

    /**
     * Marcar una notificación como leída.
     * @param id ID de la notificación
     */
    @PutMapping("/{id}/leer")
    public Notificacion marcarLeida(@PathVariable Integer id) {
        return notificacionService.marcarLeida(id);
    }

    /**
     * Eliminar una notificación.
     * @param id ID de la notificación
     */
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        notificacionService.eliminar(id);
    }

    /**
     * Endpoint de prueba para simular el envío de notificaciones
     * a toda la comunidad por un aviso concreto.
     * @param aviId ID del aviso
     */
    @GetMapping("/aviso/{aviId}/simular-envio")
    public String simularEnvio(@PathVariable Integer aviId) {
        Aviso aviso = avisoRepository.findById(aviId)
                .orElseThrow(() -> new RuntimeException("Aviso no encontrado: " + aviId));

        // Simulación de broadcast en consola
        System.out.println("📣 Simulando envío de notificación a toda la comunidad por el aviso: " + aviso.getAviTitulo());

        return "Broadcast simulado para aviso " + aviId;
    }
}
