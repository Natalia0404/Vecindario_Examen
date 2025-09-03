package com.example.examen.service;

import com.example.examen.model.Aviso;
import com.example.examen.model.Notificacion;
import com.example.examen.model.Usuario;
import com.example.examen.repository.NotificacionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service // Indica que esta clase es un "servicio" de Spring (capa de lógica de negocio)
public class NotificacionService {

    // Inyección del repositorio para acceder a la base de datos
    private final NotificacionRepository notificacionRepository;

    // Constructor que inyecta el repositorio
    public NotificacionService(NotificacionRepository notificacionRepository) {
        this.notificacionRepository = notificacionRepository;
    }

    /**
     * Crear y guardar una notificación en la base de datos.
     * Además, simula el envío mostrando un log en consola.
     */
    public Notificacion crear(String mensaje, Usuario usuario, Aviso aviso) {
        // Crear el objeto Notificacion
        Notificacion n = new Notificacion(mensaje, usuario, aviso);

        // Guardar en la base de datos
        Notificacion guardada = notificacionRepository.save(n);

        // Simulación de envío (solo un print en consola)
        System.out.println("📢 Notificación simulada -> Usuario: " +
                usuario.getUsuCorreo() + " | Mensaje: " + mensaje);

        return guardada;
    }

    /**
     * Listar todas las notificaciones existentes.
     */
    public List<Notificacion> listar() {
        return notificacionRepository.findAll();
    }

    /**
     * Buscar una notificación por su ID.
     */
    public Optional<Notificacion> buscarPorId(Integer id) {
        return notificacionRepository.findById(id);
    }

    /**
     * Listar notificaciones de un usuario específico,
     * ordenadas por fecha (de más reciente a más antigua).
     */
    public List<Notificacion> listarPorUsuario(Integer usuId) {
        return notificacionRepository.findByUsuario_UsuIdOrderByNotFechaDesc(usuId);
    }

    /**
     * Listar notificaciones asociadas a un aviso específico,
     * ordenadas por fecha (de más reciente a más antigua).
     */
    public List<Notificacion> listarPorAviso(Integer aviId) {
        return notificacionRepository.findByAviso_AviIdOrderByNotFechaDesc(aviId);
    }

    /**
     * Marcar una notificación como "leída".
     */
    public Notificacion marcarLeida(Integer id) {
        // Buscar la notificación, lanzar excepción si no existe
        Notificacion n = notificacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notificación no encontrada: " + id));

        // Cambiar el estado a "leída"
        n.setNotLeida(true);

        // Guardar los cambios
        return notificacionRepository.save(n);
    }

    /**
     * Eliminar una notificación por su ID.
     */
    public void eliminar(Integer id) {
        notificacionRepository.deleteById(id);
    }
}
