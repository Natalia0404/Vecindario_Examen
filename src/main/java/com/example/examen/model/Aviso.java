package com.example.examen.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "avisos")
public class Aviso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "avi_id")
    private Integer aviId;

    @Column(name = "avi_titulo", nullable = false, length = 100)
    private String aviTitulo;

    @Column(name = "avi_descripcion", nullable = false, columnDefinition = "TEXT")
    private String aviDescripcion;

    @Column(name = "avi_fecha_publicacion", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime aviFechaPublicacion = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    @Column(name = "avi_categoria", nullable = false)
    private Categoria aviCategoria;

    @Enumerated(EnumType.STRING)
    @Column(name = "avi_estado", nullable = false)
    private Estado aviEstado = Estado.PUBLICADO;

    // Relación con Usuario
    @ManyToOne
    @JoinColumn(name = "usu_id")
    private Usuario usuario;

    // Relación con Comentarios
    @OneToMany(mappedBy = "aviso", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comentario> comentarios;

    // -------------------------
    // ENUMS
    // -------------------------
    public enum Categoria {
        AYUDA, ALERTAS, COMPRAS, REUNIONES, MASCOTAS, EVENTOS
    }

    public enum Estado {
        PUBLICADO, ATENDIDO
    }

    // -------------------------
    // Constructores
    // -------------------------
    public Aviso() {
    }

    public Aviso(Integer aviId, String aviTitulo, String aviDescripcion, LocalDateTime aviFechaPublicacion,
                 Categoria aviCategoria, Estado aviEstado, Usuario usuario) {
        this.aviId = aviId;
        this.aviTitulo = aviTitulo;
        this.aviDescripcion = aviDescripcion;
        this.aviFechaPublicacion = aviFechaPublicacion;
        this.aviCategoria = aviCategoria;
        this.aviEstado = aviEstado;
        this.usuario = usuario;
    }

    // -------------------------
    // Getters y Setters
    // -------------------------
    public Integer getAviId() {
        return aviId;
    }

    public void setAviId(Integer aviId) {
        this.aviId = aviId;
    }

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

    public LocalDateTime getAviFechaPublicacion() {
        return aviFechaPublicacion;
    }

    public void setAviFechaPublicacion(LocalDateTime aviFechaPublicacion) {
        this.aviFechaPublicacion = aviFechaPublicacion;
    }

    public Categoria getAviCategoria() {
        return aviCategoria;
    }

    public void setAviCategoria(Categoria aviCategoria) {
        this.aviCategoria = aviCategoria;
    }

    public Estado getAviEstado() {
        return aviEstado;
    }

    public void setAviEstado(Estado aviEstado) {
        this.aviEstado = aviEstado;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Comentario> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<Comentario> comentarios) {
        this.comentarios = comentarios;
    }
}
