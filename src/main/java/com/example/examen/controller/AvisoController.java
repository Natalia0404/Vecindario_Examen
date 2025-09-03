package com.example.examen.controller;

import com.example.examen.model.Aviso;
import com.example.examen.model.Usuario;
import com.example.examen.service.AvisoService;
import com.example.examen.service.NotificacionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/avisos")
public class AvisoController {

    private final AvisoService avisoService;
    private final NotificacionService notificacionService;

    public AvisoController(AvisoService avisoService, NotificacionService notificacionService) {
        this.avisoService = avisoService;
        this.notificacionService = notificacionService;
    }

    @GetMapping
    public List<Aviso> listarAvisos() {
        return avisoService.listarAvisos();
    }

    @GetMapping("/{id}")
    public Optional<Aviso> buscarAviso(@PathVariable Integer id) {
        return avisoService.buscarAvisoPorId(id);
    }

    // (a) Publicar un aviso
    @PostMapping
    public Aviso guardarAviso(@RequestBody Aviso aviso) {
        Aviso guardado = avisoService.guardarAviso(aviso);

        // Notificar al autor que su aviso fue publicado
        Usuario autor = guardado.getUsuario();
        if (autor != null) {
            String mensaje = "Tu aviso \"" + guardado.getAviTitulo() + "\" fue publicado correctamente.";
            notificacionService.crear(mensaje, autor, guardado);
        }

        return guardado;
    }

    @DeleteMapping("/{id}")
    public void eliminarAviso(@PathVariable Integer id) {
        avisoService.eliminarAviso(id);
    }

    // (b) Marcar un aviso como "atendido"
    @PutMapping("/{id}")
    public Aviso actualizarAviso(@PathVariable Integer id, @RequestBody Aviso aviso) {
        return avisoService.buscarAvisoPorId(id)
                .map(a -> {
                    a.setAviTitulo(aviso.getAviTitulo());
                    a.setAviDescripcion(aviso.getAviDescripcion());
                    a.setAviCategoria(aviso.getAviCategoria());

                    boolean estadoCambio = aviso.getAviEstado() != null &&
                            !aviso.getAviEstado().equals(a.getAviEstado());

                    a.setAviEstado(aviso.getAviEstado());
                    a.setUsuario(aviso.getUsuario());

                    Aviso actualizado = avisoService.guardarAviso(a);

                    /*// Si el estado cambió a ATENDIDO, enviamos notificación
                    if (estadoCambio && "ATENDIDO".equalsIgnoreCase(aviso.getAviEstado())) {
                        Usuario autor = actualizado.getUsuario();
                        if (autor != null) {
                            String mensaje = "Tu aviso \"" + actualizado.getAviTitulo() + "\" fue marcado como ATENDIDO.";
                            notificacionService.crear(mensaje, autor, actualizado);
                        }
                    }*/

                    return actualizado;
                })
                .orElseThrow(() -> new RuntimeException("Aviso no encontrado con id " + id));
    }
}
