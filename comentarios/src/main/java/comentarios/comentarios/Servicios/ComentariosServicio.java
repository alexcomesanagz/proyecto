package comentarios.comentarios.Servicios;

import comentarios.comentarios.DTO.CrearComentarioDTO;
import comentarios.comentarios.DTO.UsuarioDTO;
import comentarios.comentarios.Entidades.Comentarios;
import comentarios.comentarios.Repositorios.ComentarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;

@Service
public class ComentariosServicio {

    @Autowired
    private ComentarioRepositorio comentarioRepo;

    public boolean comprobarUsuario(String usuario, String contrasena){
        RestTemplate restTemplate = new RestTemplate();
        String urlServicio = "http://localhost:8502/usuarios/validar";
        UsuarioDTO usuarioDTO = new UsuarioDTO(usuario, contrasena);

        ResponseEntity<Boolean> response = restTemplate.postForEntity(urlServicio, usuarioDTO, Boolean.class);
        return Boolean.TRUE.equals(response.getBody());
    }

    public int idUsuario(String usuario){
        try {
            RestTemplate restTemplate = new RestTemplate();
            String urlServicio = "http://localhost:8502/usuarios/info/nombre/" + usuario;

            ResponseEntity<String> response = restTemplate.getForEntity(urlServicio, String.class);
            if (response.getBody() == null || response.getBody().contains("No se ha podido")) {
                throw new RuntimeException("Usuario no encontrado en el sistema de usuarios");
            }

            return Integer.parseInt(response.getBody());

        }catch(Exception e){
            throw new RuntimeException("No se pudo obtener el id del usuario");
        }
    }

    public int idHotel(String nombreHotel){
        try {
            RestTemplate restTemplate = new RestTemplate();
            String urlServicio = "http://localhost:8501/reservas/hotel/id/" + nombreHotel;

            ResponseEntity<String> response = restTemplate.getForEntity(urlServicio, String.class);
            if (response.getBody() == null || response.getBody().contains("No se ha podido")) {
                throw new RuntimeException("Hotel no encontrado en el sistema de reservas");
            }

            return Integer.parseInt(response.getBody());

        }catch(Exception e){
            throw new RuntimeException("No se pudo obtener el id del hotel");
        }
    }


    public CrearComentarioDTO crearComentario(CrearComentarioDTO dto) {
        if(!comprobarUsuario(dto.getNombre(), dto.getContrasena())){
            throw new RuntimeException("Usuario o contraseña incorrectos");
        }

        int idHotel = idHotel(dto.getNombreHotel());
        int idUsuario = idUsuario(dto.getNombre());

        //Deberá comprobar frente al microservicio reservas (método checkReserva)
        //si la combinación (idUsuario - idHotel - idReserva) existe antes de poder crear el comentario.

        //Si el usuario ya hizo un comentario sobre esa combinación (idUsuario - idHotel - idReserva)
        //no se podrá realizar el comentario

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
}
