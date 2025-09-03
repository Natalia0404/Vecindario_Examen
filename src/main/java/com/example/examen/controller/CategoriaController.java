package com.example.examen.controller;

import com.example.examen.model.Categoria;
import com.example.examen.service.CategoriaService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping ("/categorias")

public class CategoriaController {


    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping
    public List<Categoria> listarCategoria() {
        return categoriaService.listarCategoria();
    }

    @GetMapping("/{id}")
    public Optional<Categoria> buscarCategoria(@PathVariable Integer id) {
        return categoriaService.buscarCategoriaPorId(id);
    }

    @PostMapping
    public Categoria guardarCategoria(@RequestBody Categoria categoria) {
        return categoriaService.guardarCategoria(categoria);
    }

    @PutMapping("/{id}")
    public Categoria actualizarCategoria(@PathVariable Integer id, @RequestBody Categoria categoria) {
        return categoriaService.buscarCategoriaPorId(id)
                .map(u -> {
                    u.setCatNombre(categoria.getCatNombre());
                    return categoriaService.guardarCategoria(u);
                })
                .orElseThrow(() -> new RuntimeException("Categoria no encontrada con id " + id));
    }

    @DeleteMapping("/{id}")
    public void eliminarCategoria(@PathVariable Integer id) {
        categoriaService.eliminarCategoria(id);
    }
}
