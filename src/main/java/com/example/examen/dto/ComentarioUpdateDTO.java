package com.example.examen.dto;

import jakarta.validation.constraints.NotBlank;

public class ComentarioUpdateDTO {
    @NotBlank private String comContenido;

    public String getComContenido() { return comContenido; }
    public void setComContenido(String comContenido) { this.comContenido = comContenido; }
}