package comentarios.comentarios.Controladores;

import comentarios.comentarios.DTO.CrearComentarioDTO;
import comentarios.comentarios.Servicios.ComentariosServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/comentarios")

public class ComentarioControlador {

    private final ComentariosServicio comentariosServicio;

    @Autowired
    public ComentarioControlador(ComentariosServicio comentariosServicio) {
        this.comentariosServicio = comentariosServicio;
    }

    @MutationMapping
    public ResponseEntity<CrearComentarioDTO> crearUsuario(@Argument CrearComentarioDTO dto) {
        try {
            CrearComentarioDTO comentario = comentariosServicio.crearComentario(dto);
            return ResponseEntity.ok().body(comentario);
        } catch (Exception e) {
            return ResponseEntity.ok().body(null);
        }
    }
}
