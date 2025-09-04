package com.example.examen.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notificaciones")
public class Notificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "not_id")
    private Integer notId;

    @Column(name = "not_mensaje", nullable = false, length = 255)
    private String notMensaje;

    @Column(name = "not_fecha", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime notFecha = LocalDateTime.now();

    @Column(name = "not_leida", nullable = false)
    private Boolean notLeida = false;

    @ManyToOne
    @JoinColumn(name = "usu_id", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "avi_id", nullable = false)
    private Aviso aviso;

    public Notificacion() { }

    public Notificacion(String notMensaje, Usuario usuario, Aviso aviso) {
        this.notMensaje = notMensaje;
        this.usuario = usuario;
        this.aviso = aviso;
    }

    public Integer getNotId() {
        return notId;
    }
    public void setNotId(Integer notId) {
        this.notId = notId;
    }

    public String getNotMensaje() {
        return notMensaje;
    }
    public void setNotMensaje(String notMensaje) {
        this.notMensaje = notMensaje;
    }

    public LocalDateTime getNotFecha() {
        return notFecha;
    }
    public void setNotFecha(LocalDateTime notFecha) {
        this.notFecha = notFecha;
    }

    public Boolean getNotLeida() {
        return notLeida;
    }
    public void setNotLeida(Boolean notLeida) {
        this.notLeida = notLeida;
    }

    public Usuario getUsuario() { return usuario;
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