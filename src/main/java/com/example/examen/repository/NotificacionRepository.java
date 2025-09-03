package com.example.examen.repository;

import com.example.examen.model.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repositorio para la entidad Notificacion.
 * Extiende de JpaRepository, lo cual provee métodos CRUD básicos
 * (guardar, eliminar, buscar por id, listar todos, etc.) sin necesidad de implementarlos.
 */
public interface NotificacionRepository extends JpaRepository<Notificacion, Integer> {

    /**
     * Busca todas las notificaciones de un usuario específico, ordenadas
     * de la más reciente a la más antigua según el campo notFecha.
     *
     * @param usuId ID del usuario
     * @return lista de notificaciones asociadas al usuario
     */
    List<Notificacion> findByUsuario_UsuIdOrderByNotFechaDesc(Integer usuId);

    /**
     * Busca todas las notificaciones asociadas a un aviso específico,
     * ordenadas de la más reciente a la más antigua según el campo notFecha.
     *
     * @param aviId ID del aviso
     * @return lista de notificaciones asociadas al aviso
     */
    List<Notificacion> findByAviso_AviIdOrderByNotFechaDesc(Integer aviId);
}