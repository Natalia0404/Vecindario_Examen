package com.example.examen.controller;

import com.example.examen.dto.AvisoCreateDTO;
import com.example.examen.dto.AvisoUpdateDTO;
import com.example.examen.model.Aviso;
import com.example.examen.model.Estado;
import com.example.examen.service.AvisoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/avisos")
public class AvisoController {

    private final AvisoService service;

    public AvisoController(AvisoService service) {
        this.service = service;
    }

    @GetMapping
    public List<Aviso> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Aviso> obtener(@PathVariable Integer id) {
        Aviso a = service.obtener(id);
        return (a != null) ? ResponseEntity.ok(a) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Aviso> crear(@Valid @RequestBody AvisoCreateDTO dto) {
        Aviso creado = service.crear(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Aviso> actualizar(@PathVariable Integer id,
                                            @Valid @RequestBody AvisoUpdateDTO dto) {
        Aviso actualizado = service.actualizar(id, dto);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<Aviso> cambiarEstado(@PathVariable Integer id,
                                               @RequestParam("estado") String estado) {
        try {
            Estado nuevoEstado = Estado.valueOf(estado.toUpperCase());
            Aviso actualizado = service.cambiarEstado(id, nuevoEstado);
            return ResponseEntity.ok(actualizado);
        } catch (IllegalArgumentException e) {
            // Si alguien manda un estado inválido
            return ResponseEntity.badRequest().build();
        } catch (NoSuchElementException e) {
            // Si no existe el aviso
            return ResponseEntity.notFound().build();
        }
    }
}
