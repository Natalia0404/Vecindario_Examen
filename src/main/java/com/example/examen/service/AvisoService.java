package com.example.examen.service;

import com.example.examen.dto.AvisoCreateDTO;
import com.example.examen.dto.AvisoUpdateDTO;
import com.example.examen.model.Aviso;
import com.example.examen.model.Categoria;
import com.example.examen.model.Estado;
import com.example.examen.model.Usuario;
import com.example.examen.repository.AvisoRepository;
import com.example.examen.repository.CategoriaRepository;
import com.example.examen.repository.NotificacionRepository;
import com.example.examen.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.NoSuchElementException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class AvisoService {

    private final AvisoRepository avisoRepo;
    private final UsuarioRepository usuarioRepo;
    private final CategoriaRepository categoriaRepo;
    private final NotificacionService notificacionService;

    public AvisoService(AvisoRepository avisoRepo,
                        UsuarioRepository usuarioRepo,
                        CategoriaRepository categoriaRepo,
                        NotificacionService notificacionService) {
        this.avisoRepo = avisoRepo;
        this.usuarioRepo = usuarioRepo;
        this.categoriaRepo = categoriaRepo;
        this.notificacionService = notificacionService;
    }

    public List<Aviso> listar() {
        return avisoRepo.findAll();
    }

    public Aviso obtener(Integer id) {
        return avisoRepo.findById(id).orElse(null);
    }

    public Aviso crear(AvisoCreateDTO dto) {
        Usuario usuario = usuarioRepo.findById(dto.getUsuId())
                .orElseThrow(() -> new ResponseStatusException(BAD_REQUEST, "usu_id no existe"));
        Categoria categoria = categoriaRepo.findById(dto.getCatId())
                .orElseThrow(() -> new ResponseStatusException(BAD_REQUEST, "cat_id no existe"));

        Aviso a = new Aviso();
        a.setAviTitulo(dto.getAviTitulo());
        a.setAviDescripcion(dto.getAviDescripcion());
        if (dto.getAviEstado() != null) {
            try {
                a.setAviEstado(Estado.valueOf(dto.getAviEstado()));
            } catch (IllegalArgumentException ex) {
                throw new ResponseStatusException(BAD_REQUEST, "aviEstado inválido (PUBLICADO|ATENDIDO)");
            }
        }
        a.setUsuario(usuario);
        a.setCategoria(categoria);
        return avisoRepo.save(a);
    }

    public Aviso actualizar(Integer id, AvisoUpdateDTO dto) {
        Aviso a = avisoRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Aviso no encontrado"));

        if (dto.getAviTitulo() != null) a.setAviTitulo(dto.getAviTitulo());
        if (dto.getAviDescripcion() != null) a.setAviDescripcion(dto.getAviDescripcion());
        if (dto.getAviEstado() != null) {
            try {
                a.setAviEstado(Estado.valueOf(dto.getAviEstado()));
            } catch (IllegalArgumentException ex) {
                throw new ResponseStatusException(BAD_REQUEST, "aviEstado inválido (PUBLICADO|ATENDIDO)");
            }
        }
        if (dto.getUsuId() != null) {
            Usuario u = usuarioRepo.findById(dto.getUsuId())
                    .orElseThrow(() -> new ResponseStatusException(BAD_REQUEST, "usu_id no existe"));
            a.setUsuario(u);
        }
        if (dto.getCatId() != null) {
            Categoria c = categoriaRepo.findById(dto.getCatId())
                    .orElseThrow(() -> new ResponseStatusException(BAD_REQUEST, "cat_id no existe"));
            a.setCategoria(c);
        }
        return avisoRepo.save(a);
    }

    public void eliminar(Integer id) {
        if (!avisoRepo.existsById(id)) {
            throw new ResponseStatusException(NOT_FOUND, "Aviso no encontrado");
        }
        avisoRepo.deleteById(id);
    }

    public Aviso cambiarEstado(Integer id, Estado nuevoEstado) {
        Aviso aviso = avisoRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Aviso no encontrado"));

        aviso.setAviEstado(nuevoEstado);
        Aviso actualizado = avisoRepo.save(aviso);

        // Crear la notificación para el dueño del aviso
        String mensaje = "Tu aviso \"" + aviso.getAviTitulo() +
                "\" fue marcado como " + nuevoEstado.name();
        notificacionService.crear(mensaje, aviso.getUsuario(), aviso);

        return actualizado;
    }
}

