package com.example.examen.model;
import jakarta.persistence.*;

@Entity
@Table (name = "categorias")
public class Categoria {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cat_id")
    private Integer catId;

    @Column(name = "cat_nombre", nullable = false, unique = true, length = 50)
    private String catNombre;

    public Categoria() {
    }

    public Categoria(Integer catId, String catNombre) {
        this.catId = catId;
        this.catNombre = catNombre;
    }

    public Integer getCatId() {
        return catId;
    }

    public void setCatId(Integer catId) {
        this.catId = catId;
    }

    public String getCatNombre() {
        return catNombre;
    }

    public void setCatNombre(String catNombre) {
        this.catNombre = catNombre;
    }
}


