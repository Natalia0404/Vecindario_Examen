package com.example.examen.service;

import com.example.examen.model.Aviso;
import com.example.examen.repository.AvisoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AvisoService {

    private final AvisoRepository avisoRepository;

    public AvisoService(AvisoRepository avisoRepository) {
        this.avisoRepository = avisoRepository;
    }

    public List<Aviso> listarAvisos() {
        return avisoRepository.findAll();
    }

    public Optional<Aviso> buscarAvisoPorId(Integer id) {
        return avisoRepository.findById(id);
    }

    public Aviso guardarAviso(Aviso aviso) {
        return avisoRepository.save(aviso);
    }

    public void eliminarAviso(Integer id) {
        avisoRepository.deleteById(id);
    }
}

