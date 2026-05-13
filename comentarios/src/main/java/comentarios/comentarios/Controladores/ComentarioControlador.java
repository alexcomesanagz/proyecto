package comentarios.comentarios.Controladores;

import comentarios.comentarios.DTO.*;
import comentarios.comentarios.Servicios.ComentariosServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ComentarioControlador {

    @Autowired
    private ComentariosServicio comentariosServicio;


    @MutationMapping
    public CrearComentarioDTO crearComentario(@Argument CrearComentarioDTO dto) {
        try {
            CrearComentarioDTO comentario = comentariosServicio.crearComentario(dto);
            return comentario;
        } catch (Exception e) {
            return null;
        }
    }

   /* @MutationMapping
    public String eliminarComentarios() {
        try {
            return comentariosServicio.eliminarComentarios();
        } catch (Exception e) {
            return "No se han podido eliminar los comentarios. ERROR:" + e.getMessage();
        }
    }

    @MutationMapping
    public String eliminarComentarioDeUsuario(@Argument EliminarComentarioDTO dto) {
        try {
            return comentariosServicio.eliminarComentarioDeUsuario(dto);
        } catch (Exception e) {
            return "No se han podido eliminar el usuario. ERROR:" + e.getMessage();
        }
    }

//    @QueryMapping
//    public List<ComentarioHotelDTO> listarComentariosHotel(@Argument NombreHotelUsuarioDTO dto) {
//           return comentariosServicio.listarComentariosHotel(dto);
//    }

    /*
    @QueryMapping
    public ResponseEntity<List<ComentarioHotelDTO>> listarComentariosUsuario(@Argument UsuarioDTO dto) {
        try {
            return comentariosServicio.listarComentariosUsuario(dto);
        } catch (Exception e) {
            throw new RuntimeException("Error al mostrar el comentario de la reserva: " + e.getMessage());
        }
    }
     */
    /*
    @QueryMapping
    public ResponseEntity<List<ComentarioHotelDTO>> mostrarComentarioUsuarioReserva(@Argument MostrarComentarioUsuarioReservaDTO dto) {
        return comentariosServicio.mostrarComentarioUsuarioReserva(dto);
    }
    */
    /*
    @QueryMapping
    public Float puntuacionMediaHotel(@Argument NombreHotelUsuarioDTO dto) {
        return comentariosServicio.puntuacionMediaHotel(dto);
    }
     */

}
