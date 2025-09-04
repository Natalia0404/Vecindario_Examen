package com.example.examen.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.time.LocalDateTime;

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
    @Column(name = "avi_estado", nullable = false)
    private Estado aviEstado = Estado.PUBLICADO;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "usu_id", nullable = false)
    @JsonIgnoreProperties({"avisos", "hibernateLazyInitializer", "handler"})
    private Usuario usuario;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "cat_id", nullable = false)
    @JsonIgnoreProperties({"avisos", "hibernateLazyInitializer", "handler"})
    private Categoria categoria;

    // ====== Constructores ======
    public Aviso() {}

    public Aviso(Integer aviId, String aviTitulo, String aviDescripcion, LocalDateTime aviFechaPublicacion,
                 Estado aviEstado, Usuario usuario, Categoria categoria) {
        this.aviId = aviId;
        this.aviTitulo = aviTitulo;
        this.aviDescripcion = aviDescripcion;
        this.aviFechaPublicacion = aviFechaPublicacion;
        this.aviEstado = aviEstado;
        this.usuario = usuario;
        this.categoria = categoria;
    }

    // ====== Getters/Setters ======
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

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
}
