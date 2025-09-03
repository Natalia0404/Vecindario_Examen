package com.example.examen.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usu_id")
    private Integer usuId;

    @Column(name = "usu_nombre", nullable = false, length = 150)
    private String usuNombre;

    @Column(name = "usu_correo", nullable = false, unique = true, length = 100)
    private String usuCorreo;

    @Column(name = "usu_password", nullable = false, length = 255)
    private String usuPassword;

    @Column(name = "usu_direccion", length = 200)
    private String usuDireccion;

    @Column(name = "usu_activo", nullable = false)
    private Boolean usuActivo = true;

    // Relación con Avisos
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Aviso> avisos;

    // Relación con Comentarios
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comentario> comentarios;

    // -------------------------
    // Constructores
    // -------------------------
    public Usuario() {
    }

    public Usuario(Integer usuId, String usuNombre, String usuCorreo, String usuPassword, String usuDireccion, Boolean usuActivo) {
        this.usuId = usuId;
        this.usuNombre = usuNombre;
        this.usuCorreo = usuCorreo;
        this.usuPassword = usuPassword;
        this.usuDireccion = usuDireccion;
        this.usuActivo = usuActivo;
    }

    // -------------------------
    // Getters y Setters
    // -------------------------
    public Integer getUsuId() {
        return usuId;
    }

    public void setUsuId(Integer usuId) {
        this.usuId = usuId;
    }

    public String getUsuNombre() {
        return usuNombre;
    }

    public void setUsuNombre(String usuNombre) {
        this.usuNombre = usuNombre;
    }

    public String getUsuCorreo() {
        return usuCorreo;
    }

    public void setUsuCorreo(String usuCorreo) {
        this.usuCorreo = usuCorreo;
    }

    public String getUsuPassword() {
        return usuPassword;
    }

    public void setUsuPassword(String usuPassword) {
        this.usuPassword = usuPassword;
    }

    public String getUsuDireccion() {
        return usuDireccion;
    }

    public void setUsuDireccion(String usuDireccion) {
        this.usuDireccion = usuDireccion;
    }

    public Boolean getUsuActivo() {
        return usuActivo;
    }

    public void setUsuActivo(Boolean usuActivo) {
        this.usuActivo = usuActivo;
    }

    public List<Aviso> getAvisos() {
        return avisos;
    }

    public void setAvisos(List<Aviso> avisos) {
        this.avisos = avisos;
    }

    public List<Comentario> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<Comentario> comentarios) {
        this.comentarios = comentarios;
    }
}
