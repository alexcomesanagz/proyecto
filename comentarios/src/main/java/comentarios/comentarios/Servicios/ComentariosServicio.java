package comentarios.comentarios.Servicios;

import comentarios.comentarios.DTO.*;
import comentarios.comentarios.Entidades.Comentarios;
import comentarios.comentarios.Repositorios.ComentarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.String.format;

@Service
public class ComentariosServicio {

    @Autowired
    private ComentarioRepositorio comentarioRepo;

    public boolean comprobarUsuario(String usuario, String contrasena) {
        RestTemplate restTemplate = new RestTemplate();
        String urlServicio = "http://localhost:8502/usuarios/validar";
        UsuarioDTO usuarioDTO = new UsuarioDTO(usuario, contrasena);

        ResponseEntity<Boolean> response = restTemplate.postForEntity(urlServicio, usuarioDTO, Boolean.class);
        return Boolean.TRUE.equals(response.getBody());
    }

    public int idUsuario(String usuario) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String urlServicio = "http://localhost:8502/usuarios/info/nombre/" + usuario;

            ResponseEntity<String> response = restTemplate.getForEntity(urlServicio, String.class);
            if (response.getBody() == null || response.getBody().contains("No se ha podido")) {
                throw new RuntimeException("Usuario no encontrado en el sistema de usuarios");
            }

            return Integer.parseInt(response.getBody());

        } catch (Exception e) {
            throw new RuntimeException("No se pudo obtener el id del usuario");
        }
    }

    public int idHotel(String nombreHotel) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String urlServicio = "http://localhost:8501/reservas/hotel/id/" + nombreHotel;

            ResponseEntity<String> response = restTemplate.getForEntity(urlServicio, String.class);
            if (response.getBody() == null || response.getBody().contains("No se ha podido")) {
                throw new RuntimeException("Hotel no encontrado en el sistema de reservas");
            }

            return Integer.parseInt(response.getBody());

        } catch (Exception e) {
            throw new RuntimeException("No se pudo obtener el id del hotel");
        }
    }

    public boolean reservaExist(int idUsuario, int idHotel, int idReserva) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String urlServicio = String.format(
                    "http://localhost:8501/reservas/check?idUsuario=%d&idHotel=%d&idReserva=%d",
                    idUsuario, idHotel, idReserva
            );
            ResponseEntity<Boolean> response = restTemplate.getForEntity(urlServicio, Boolean.class);
            return Boolean.TRUE.equals(response.getBody());
        } catch (Exception e) {
            return false;
        }
    }


    public CrearComentarioDTO crearComentario(CrearComentarioDTO dto) {
        if (!comprobarUsuario(dto.getNombre(), dto.getContrasena())) {
            throw new RuntimeException("Usuario o contraseña incorrectos");
        }

        int idHotel = idHotel(dto.getNombreHotel());
        int idUsuario = idUsuario(dto.getNombre());

        //Comprobar mediante checkReserva
        //si la combinación (idUsuario - idHotel - idReserva) existe antes de crear el comentario.
        boolean reservaValida = reservaExist(idUsuario, idHotel, dto.getId_reserva());
        if (!reservaValida) {
            throw new RuntimeException("La combinación de Usuario, Hotel y Reserva no es válida o no existe.");
        }

        //Si el usuario ya hizo un comentario sobre esa combinación (idUsuario - idHotel - idReserva)
        //no se podrá realizar el comentario
        boolean yaComento = comentarioRepo.existsByUsuarioIdAndHotelIdAndReservaId(idUsuario, idHotel, dto.getId_reserva());
        if (yaComento) {
            throw new RuntimeException("El usuario ya ha realizado un comentario para esta reserva.");
        }

        Comentarios comentario = new Comentarios();
        comentario.setUsuarioId(idUsuario);
        comentario.setHotelId(idHotel);
        comentario.setReservaId(dto.getId_reserva());
        comentario.setPuntuacion(dto.getPuntuacion());
        comentario.setComentario(dto.getComentario());
        String fechaCreacion = Instant.now().toString();
        comentario.setFechaCreacion(fechaCreacion);

        comentarioRepo.save(comentario);

        return dto;
    }

    public String eliminarComentarios() {
        try {
            if (comentarioRepo.count() == 0) {
                return "No hay comentarios para eliminar.";
            }

            comentarioRepo.deleteAll();
            return "Todos los comentarios han sido eliminados correctamente.";
        } catch (Exception e) {
            throw new RuntimeException("No se pudieron eliminar los comentarios de la base de datos.");
        }
    }

    public String eliminarComentarioDeUsuario(EliminarComentarioDTO dto) {
        if (!comprobarUsuario(dto.getNombre(), dto.getContrasena())) {
            throw new RuntimeException("Usuario o contraseña incorrectos.");
        }

        Optional<Comentarios> comentarioOpt = comentarioRepo.findById(dto.getId_comentario());
        if (comentarioOpt.isEmpty()) {
            throw new RuntimeException("El comentario con ID " + dto.getId_comentario() + " no existe.");
        }

        Comentarios comentario = comentarioOpt.get();
        int userId = idUsuario(dto.getNombre());

        //[Opcional] Verificar que el comentario pertenezca al usuario que lo borra
        if (comentario.getUsuarioId() != userId) {
            throw new RuntimeException("No tienes permisos para eliminar este comentario");
        }

        comentarioRepo.delete(comentario);

        return "El comentario se ha eliminado correctamente.";
    }

    public List<ComentarioHotelDTO> listarComentariosHotel(NombreHotelUsuarioDTO dto) {
        if (!comprobarUsuario(dto.getNombre(), dto.getContrasena())) {
            throw new RuntimeException("Usuario o contraseña incorrectos.");
        }

        return null;
    }

    /*
    public ResponseEntity<List<ComentarioHotelDTO>> listarComentariosUsuario(UsuarioDTO dto) {
        if(!comprobarUsuario(dto.getNombre(), dto.getContrasena())){
            throw new RuntimeException("Usuario o contraseña incorrectos.");
        }

        //conseguir la id reserva con solo la info de usuario
        Optional<Comentarios> comentarioOpt = comentarioRepo.buscarComentarioPorReservaId(dto.get());
        if (comentarioOpt.isEmpty()) {
            return List.of();
        }

        return Stream.of(comentarioOpt.get()).map(comentario -> new ComentarioHotelDTO(
                comentario.get().getHotel().getNombre(), // hacer funcion para conseguir nombreHotel
                comentario.getReservaId(),
                comentario.getPuntuacion(),
                comentario.getComentario()
        )).collect(Collectors.toList());
    }
    */
    /*
    public ResponseEntity<List<ComentarioHotelDTO>> mostrarComentarioUsuarioReserva(MostrarComentarioUsuarioReservaDTO dto) {

    }
    */
    /*
    public Float puntuacionMediaHotel(NombreHotelUsuarioDTO dto) {
    }
    */

}
