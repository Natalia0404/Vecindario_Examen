package com.example.examen.controller;

import com.example.examen.model.Aviso;
import com.example.examen.service.AvisoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/avisos")
public class AvisoController {

    private final AvisoService avisoService;

    public AvisoController(AvisoService avisoService) {
        this.avisoService = avisoService;
    }

    @GetMapping
    public List<Aviso> listarAvisos() {
        return avisoService.listarAvisos();
    }

    @GetMapping("/{id}")
    public Optional<Aviso> buscarAviso(@PathVariable Integer id) {
        return avisoService.buscarAvisoPorId(id);
    }

    @PostMapping
    public Aviso guardarAviso(@RequestBody Aviso aviso) {
        return avisoService.guardarAviso(aviso);
    }

    @DeleteMapping("/{id}")
    public void eliminarAviso(@PathVariable Integer id) {
        avisoService.eliminarAviso(id);
    }

    @PutMapping("/{id}")
    public Aviso actualizarAviso(@PathVariable Integer id, @RequestBody Aviso aviso) {
        return avisoService.buscarAvisoPorId(id)
                .map(a -> {
                    a.setAviTitulo(aviso.getAviTitulo());
                    a.setAviDescripcion(aviso.getAviDescripcion());
                    a.setAviCategoria(aviso.getAviCategoria());
                    a.setAviEstado(aviso.getAviEstado());
                    a.setUsuario(aviso.getUsuario());
                    return avisoService.guardarAviso(a);
                })
                .orElseThrow(() -> new RuntimeException("Aviso no encontrado con id " + id));
    }
}
