package com.example.examen.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tokens")
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tok_id")
    private Integer tokId;

    @Column(name = "tok_valor", nullable = false, length = 500)
    private String tokValor;

    @Column(name = "tok_fecha_creacion")
    private LocalDateTime tokFechaCreacion = LocalDateTime.now();

    @Column(name = "tok_fecha_expira")
    private LocalDateTime tokFechaExpira;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usu_id", nullable = false)
    private Usuario usuario;

    // --- Getters y Setters ---

    public Integer getTokId() {
        return tokId;
    }

    public void setTokId(Integer tokId) {
        this.tokId = tokId;
    }

    public String getTokValor() {
        return tokValor;
    }

    public void setTokValor(String tokValor) {
        this.tokValor = tokValor;
    }

    public LocalDateTime getTokFechaCreacion() {
        return tokFechaCreacion;
    }

    public void setTokFechaCreacion(LocalDateTime tokFechaCreacion) {
        this.tokFechaCreacion = tokFechaCreacion;
    }

    public LocalDateTime getTokFechaExpira() {
        return tokFechaExpira;
    }

    public void setTokFechaExpira(LocalDateTime tokFechaExpira) {
        this.tokFechaExpira = tokFechaExpira;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
