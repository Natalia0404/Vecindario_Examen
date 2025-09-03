package com.example.examen.repository;

import com.example.examen.model.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificacionRepository extends JpaRepository<Notificacion, Integer> {
    List<Notificacion> findByUsuario_UsuIdOrderByNotFechaDesc(Integer usuId);
    List<Notificacion> findByAviso_AviIdOrderByNotFechaDesc(Integer aviId);
}