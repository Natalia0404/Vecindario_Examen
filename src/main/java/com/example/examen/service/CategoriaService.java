package com.example.examen.service;

import com.example.examen.model.Categoria;
import com.example.examen.repository.CategoriaRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class CategoriaService{

  private final CategoriaRepository categoriaRepository;

        public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
        }

        public List<Categoria> listarCategoria() {
        return categoriaRepository.findAll(Sort.by("catId").ascending());
        }

        public Optional<Categoria> buscarCategoriaPorId(Integer id) {
        return categoriaRepository.findById(id);
        }

        public Categoria guardarCategoria(Categoria categoria) {
        return categoriaRepository.save(categoria);
        }

        public void eliminarCategoria(Integer id) {
        categoriaRepository.deleteById(id);
        }
}

