package com.example.examen.repository;

import com.example.examen.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository <Categoria, Integer> {
}
