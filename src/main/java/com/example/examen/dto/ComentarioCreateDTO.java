package com.example.examen.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ComentarioCreateDTO {
    @NotNull private Integer usuId;
    @NotNull private Integer aviId;
    @NotBlank private String comContenido;

    public Integer getUsuId() {
        return usuId;
    }

    public Integer getAviId() {
        return aviId;
    }

    public String getComContenido() {
        return comContenido;
    }

    public void setUsuId(Integer usuId) {
        this.usuId = usuId;
    }

    public void setComContenido(String comContenido) {
        this.comContenido = comContenido;
    }

    public void setAviId(Integer aviId) {
        this.aviId = aviId;
    }
}