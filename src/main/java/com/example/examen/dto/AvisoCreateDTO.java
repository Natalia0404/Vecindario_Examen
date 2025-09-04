package com.example.examen.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class AvisoCreateDTO {

    @NotBlank
    @Size(max = 100)
    private String aviTitulo;

    @NotBlank
    private String aviDescripcion;

    // Opcional: "PUBLICADO" | "ATENDIDO"
    @Pattern(regexp = "PUBLICADO|ATENDIDO", message = "aviEstado debe ser PUBLICADO o ATENDIDO")
    private String aviEstado;

    @NotNull
    private Integer usuId;

    @NotNull
    private Integer catId;

    public String getAviTitulo() {
        return aviTitulo;
    }

    public void setAviTitulo(String aviTitulo) {
        this.aviTitulo = aviTitulo;
    }

    public String getAviDescripcion() {
        return aviDescripcion;
    }

    public void setAviDescripcion(String aviDescripcion) {
        this.aviDescripcion = aviDescripcion;
    }

    public String getAviEstado() {
        return aviEstado;
    }

    public void setAviEstado(String aviEstado) {
        this.aviEstado = aviEstado;
    }

    public Integer getUsuId() {
        return usuId;
    }

    public void setUsuId(Integer usuId) {
        this.usuId = usuId;
    }

    public Integer getCatId() {
        return catId;
    }

    public void setCatId(Integer catId) {
        this.catId = catId;
    }
}
