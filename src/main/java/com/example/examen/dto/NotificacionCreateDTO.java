// src/main/java/com/example/examen/dto/NotificacionCreateDTO.java
package com.example.examen.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class NotificacionCreateDTO {
    @NotBlank
    private String mensaje;

    @NotNull
    private Integer usuId;

    @NotNull
    private Integer aviId;

    public String getMensaje() {
        return mensaje;
    }

    public Integer getUsuId() {
        return usuId;
    }

    public Integer getAviId() {
        return aviId;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public void setUsuId(Integer usuId) {
        this.usuId = usuId;
    }

    public void setAviId(Integer aviId) {
        this.aviId = aviId;
    }
}
