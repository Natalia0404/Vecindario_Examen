package com.example.examen.model;

import jakarta.persistence.*;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDateTime;

@Entity
@Table(name = "comentarios")
public class Comentario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "com_id")
    private Integer comId;

    @Column(name = "com_contenido", nullable = false, columnDefinition = "TEXT")
    private String comContenido;

    @Column(name = "com_fecha_publicacion", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime comFechaPublicacion;

    // Relación con Usuario
    @ManyToOne
    @JoinColumn(name = "usu_id")
    private Usuario usuario;

    // Relación con Aviso
    @ManyToOne
    @JoinColumn(name = "avi_id")
    private Aviso aviso;

    // -------------------------
    // Constructores
    // -------------------------
    public Comentario() {
    }

    public Comentario(Integer comId, String comContenido, LocalDateTime comFechaPublicacion, Usuario usuario, Aviso aviso) {
        this.comId = comId;
        this.comContenido = comContenido;
        this.comFechaPublicacion = comFechaPublicacion;
        this.usuario = usuario;
        this.aviso = aviso;
    }

    // -------------------------
    // Getters y Setters
    // -------------------------
    public Integer getComId() {
        return comId;
    }

    public void setComId(Integer comId) {
        this.comId = comId;
    }

    public String getComContenido() {
        return comContenido;
    }

    public void setComContenido(String comContenido) {
        this.comContenido = comContenido;
    }

    public LocalDateTime getComFechaPublicacion() {
        return comFechaPublicacion;
    }

    public void setComFechaPublicacion(LocalDateTime comFechaPublicacion) {
        this.comFechaPublicacion = comFechaPublicacion;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Aviso getAviso() {
        return aviso;
    }

    public void setAviso(Aviso aviso) {
        this.aviso = aviso;
    }
}
