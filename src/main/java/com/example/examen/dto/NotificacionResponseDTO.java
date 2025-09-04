// src/main/java/com/example/examen/dto/NotificacionResponseDTO.java
package com.example.examen.dto;

import java.time.LocalDateTime;

public class NotificacionResponseDTO {
    private Integer id;
    private String mensaje;
    private LocalDateTime fecha;
    private Boolean leida;
    private Integer usuId;
    private Integer aviId;


    public NotificacionResponseDTO(Integer id, String mensaje, LocalDateTime fecha,
                                   Boolean leida, Integer usuId, Integer aviId) {
        this.id = id;
        this.mensaje = mensaje;
        this.fecha = fecha;
        this.leida = leida;
        this.usuId = usuId;
        this.aviId = aviId;
    }

    public Integer getId() {
        return id;
    }

    public String getMensaje() {
        return mensaje;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public Boolean getLeida() {
        return leida;
    }

    public Integer getUsuId() {
        return usuId;
    }

    public Integer getAviId() {
        return aviId;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public void setLeida(Boolean leida) {
        this.leida = leida;
    }

    public void setAviId(Integer aviId) {
        this.aviId = aviId;
    }

    public void setUsuId(Integer usuId) {
        this.usuId = usuId;
    }
}
