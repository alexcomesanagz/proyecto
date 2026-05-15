package comentarios.comentarios.Servicios;

import comentarios.comentarios.DTO.*;
import comentarios.comentarios.Entidades.Comentarios;
import comentarios.comentarios.Repositorios.ComentarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static java.lang.String.format;

@Service
public class ComentariosServicio {

    private ComentarioRepositorio comentarioRepo;
    private MongoTemplate mongoTemplate;

    public ComentariosServicio(ComentarioRepositorio comentarioRepo, MongoTemplate mongoTemplate) {
        this.comentarioRepo = comentarioRepo;
        this.mongoTemplate = mongoTemplate;
    }

    public boolean comprobarUsuario(String usuario, String contrasena) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String urlServicio = "http://localhost:8502/usuarios/validar";
            UsuarioDTO usuarioDTO = new UsuarioDTO(usuario, contrasena);

            ResponseEntity<Boolean> response = restTemplate.postForEntity(urlServicio, usuarioDTO, Boolean.class);

            // Devuelve TRUE si el usuario es correcto
            return Boolean.TRUE.equals(response.getBody());
        } catch (Exception e) {
            System.err.println("Error de conexión con Usuarios: " + e.getMessage());
            return false; // Si falla la comunicación, el usuario no es válido
        }
    }

    public int idUsuario(String usuario) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String urlServicio = "http://localhost:8502/usuarios/info/nombre/" + usuario;

            Integer id = restTemplate.getForObject(urlServicio, Integer.class);
            if (id == null ) throw new RuntimeException("Usuario no encontrado en el sistema de usuarios");

            return id;

        } catch (Exception e) {
            throw new RuntimeException("No se pudo obtener el id del usuario");
        }
    }

    public int idHotel(String nombreHotel, String usuario, String contrasena) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String urlServicio = "http://localhost:8501/reservas/hotel/id/{nombre}";

            UsuarioDTO authBody = new UsuarioDTO();
            authBody.setUsuario(usuario);
            authBody.setContrasena(contrasena);

            ResponseEntity<String> response = restTemplate.postForEntity(urlServicio, authBody, String.class, nombreHotel);
            String body = response.getBody();

            // LOG para depurar: esto te mostrará en la consola EXACTAMENTE qué responde Reservas
            System.out.println("Respuesta de Reservas: " + body);

            if (body == null || !body.contains("ID:")) {
                throw new RuntimeException("Respuesta inesperada: " + body);
            }

            // Respuesta esperada: "ID: 1 | Nombre: Hotel A"
            // Buscamos lo que hay entre "ID:" y el primer "|"
            int inicio = body.indexOf("ID:") + 3;
            int fin = body.indexOf("|");

            if (inicio < 3 || fin == -1) {
                throw new RuntimeException("Formato de respuesta del hotel inválido: " + body);
            }

            String idExtraido = body.substring(inicio, fin).trim();
            return Integer.parseInt(idExtraido);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al obtener ID del hotel: " + e.getMessage());
        }
    }

    public String obtenerNombreHotelPorId(int idHotel, String usuario, String contrasena) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String urlServicio = "http://localhost:8501/reservas/hotel/nombre/{id}";

            UsuarioDTO authBody = new UsuarioDTO();
            authBody.setUsuario(usuario);
            authBody.setContrasena(contrasena);

            ResponseEntity<String> response = restTemplate.postForEntity(urlServicio, authBody, String.class, idHotel);
            String body = response.getBody();

            if (body == null || !body.contains("Nombre:")) {
                return "Hotel Desconocido (ID: " + idHotel + ")";
            }

            // Respuesta esperada: "ID: 1 | Nombre: Hotel A"
            // Buscamos lo que hay entre "ID:" y el primer "|"
            int inicioNombre = body.indexOf("Nombre:") + 7;
            return body.substring(inicioNombre).trim();

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al obtener el nombre del hotel: " + e.getMessage());
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
        if (!comprobarUsuario(dto.getUsuario(), dto.getContrasena())) {
            throw new RuntimeException("Usuario o contraseña incorrectos");
        }

        int idHotel = idHotel(dto.getNombreHotel(), dto.getUsuario(), dto.getContrasena());
        int idUsuario = idUsuario(dto.getUsuario());

        //Comprobar mediante checkReserva
        //si la combinación (idUsuario - idHotel - idReserva) existe antes de crear el comentario.
        boolean reservaValida = reservaExist(idUsuario, idHotel, dto.getReserva_id());
        if (!reservaValida) {
            throw new RuntimeException("La combinación de Usuario, Hotel y Reserva no es válida o no existe.");
        }

        //Si el usuario ya hizo un comentario sobre esa combinación (idUsuario - idHotel - idReserva)
        //no se podrá realizar el comentario
        boolean yaComento = comentarioRepo.existsByUsuarioIdAndHotelIdAndReservaId(idUsuario, idHotel, dto.getReserva_id());
        if (yaComento) {
            throw new RuntimeException("El usuario ya ha realizado un comentario para esta reserva.");
        }

        Comentarios comentario = new Comentarios();
        comentario.setUsuarioId(idUsuario);
        comentario.setHotelId(idHotel);
        comentario.setReservaId(dto.getReserva_id());
        comentario.setPuntuacion(dto.getPuntuacion());
        comentario.setComentario(dto.getComentario());
        comentario.setFechaCreacion(Instant.now().toString());

        try {
            Comentarios resultado = comentarioRepo.save(comentario);
            System.out.println("Guardado con éxito en la DB: " + resultado.getId());
        } catch (Exception e) {
            System.out.println("ERROR AL GUARDAR: " + e.getMessage());
            throw new RuntimeException("Error técnico al persistir en MongoDB");
        }

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
        if (!comprobarUsuario(dto.getUsuario(), dto.getContrasena())) {
            throw new RuntimeException("Usuario o contraseña incorrectos.");
        }

        Optional<Comentarios> comentarioOpt = comentarioRepo.findById(dto.getId_comentario());
        if (comentarioOpt.isEmpty()) {
            throw new RuntimeException("El comentario con ID " + dto.getId_comentario() + " no existe.");
        }

        Comentarios comentario = comentarioOpt.get();
        int userId = idUsuario(dto.getUsuario());

        //[Opcional] Verificar que el comentario pertenezca al usuario que lo borra
        if (comentario.getUsuarioId() != userId) {
            throw new RuntimeException("No tienes permisos para eliminar este comentario");
        }

        comentarioRepo.delete(comentario);

        return "El comentario se ha eliminado correctamente.";
    }

    public List<ComentarioHotelDTO> listarComentariosHotel(NombreHotelUsuarioDTO dto) {
        try {
            if (!comprobarUsuario(dto.getUsuario(), dto.getContrasena())) {
                throw new RuntimeException("Usuario o contraseña incorrectos.");
            }

            int idHotel = idHotel(dto.getNombreHotel(), dto.getUsuario(), dto.getContrasena());

            List<Comentarios> listaEntidades = comentarioRepo.findByHotelId(idHotel);

            // Mapear de Entidad a DTO de salida
            // Convertimos cada objeto "Comentarios" en un "ComentarioHotelDTO"
            return listaEntidades.stream().map(c -> {
                ComentarioHotelDTO salida = new ComentarioHotelDTO();
                salida.setNombreHotel(dto.getNombreHotel());
                salida.setReserva_id(c.getReservaId());
                salida.setPuntuacion(c.getPuntuacion());
                salida.setComentario(c.getComentario());
                return salida;
            }).toList();
        }catch (Exception e){
            throw new RuntimeException("Error al listar comentarios.");
        }
    }


    public List<ComentarioHotelDTO> listarComentariosUsuario(UsuarioDTO dto) {
        try {
            if (!comprobarUsuario(dto.getUsuario(), dto.getContrasena())) {
                throw new RuntimeException("Usuario o contraseña incorrectos.");
            }

            int idUsuario = idUsuario(dto.getUsuario());

            List<Comentarios> listaEntidades = comentarioRepo.findByUsuarioId(idUsuario);

            if (listaEntidades.isEmpty()) {
                return List.of();
            }

            return listaEntidades.stream().map(c -> {
                ComentarioHotelDTO salida = new ComentarioHotelDTO();

                String nombreHotel = obtenerNombreHotelPorId(c.getHotelId(), dto.getUsuario(),dto.getContrasena());
                salida.setNombreHotel(nombreHotel);

                salida.setReserva_id(c.getReservaId());
                salida.setPuntuacion(c.getPuntuacion());
                salida.setComentario(c.getComentario());
                return salida;
            }).toList();

        }catch (Exception e){
            throw new RuntimeException("Error al listar comentarios del usuario: " + e.getMessage());
        }
    }

    //te paso todos los usuarios que han hecho comentarios en esa reservaId?
    //o te paso el comentario que hizo ese usuario con esa reserva id, me confunde q pidas una lista
    //no termino de comprender lo que pides en el enunciado
    public List<ComentarioHotelDTO> mostrarComentarioUsuarioReserva(MostrarComentarioUsuarioReservaDTO dto) {
        try {
            if (!comprobarUsuario(dto.getUsuario(), dto.getContrasena())) {
                throw new RuntimeException("Usuario o contraseña incorrectos.");
            }

            int idUsuarioAutenticado = idUsuario(dto.getUsuario());
            Optional<Comentarios> comentarioDelUsuario = comentarioRepo.findByReservaIdAndUsuarioId(dto.getId_reserva(), idUsuarioAutenticado);

            if (comentarioDelUsuario.isEmpty()) {
                System.out.println("El usuario " +dto.getUsuario()+ " no tiene comentarios para la reserva "+ dto.getId_reserva());
                return List.of();
            }

            Comentarios c = comentarioDelUsuario.get();
            ComentarioHotelDTO salida = new ComentarioHotelDTO();
            String nombreHotel = obtenerNombreHotelPorId(c.getHotelId(), dto.getUsuario(), dto.getContrasena());

            salida.setNombreHotel(nombreHotel);
            salida.setReserva_id(c.getReservaId());
            salida.setPuntuacion(c.getPuntuacion());
            salida.setComentario(c.getComentario());

            return List.of(salida);
        }catch (Exception e){
            throw new RuntimeException("Error interno al procesar la consulta.");
        }
    }

    public Float puntuacionMediaHotel(NombreHotelUsuarioDTO dto) {
        try {
            if (!comprobarUsuario(dto.getUsuario(), dto.getContrasena())) {
                throw new RuntimeException("Usuario o contraseña incorrectos.");
            }

            int idHotel = idHotel(dto.getNombreHotel(), dto.getUsuario(), dto.getContrasena());
            Double media = comentarioRepo.getMediaPuntuacionByHotelId(idHotel);

            //Mongo devuelve null si no hay resultados en el match
            return (media != null) ? media.floatValue() : 0.0f;

        }catch (Exception e){
            System.err.println("Error: " + e.getMessage());
            return 0.0f;
        }
    }

    public Float puntuacionesMediasUsuario(UsuarioDTO dto) {
        try {
            if (!comprobarUsuario(dto.getUsuario(), dto.getContrasena())) {
                throw new RuntimeException("Usuario o contraseña incorrectos.");
            }

            int idUsuario = idUsuario(dto.getUsuario());
            Double media = comentarioRepo.getMediaPuntuacionByUsuarioId(idUsuario);

            if (media == null) {
                return 0.0f;
            }

            return media.floatValue();

        } catch (Exception e) {
            System.err.println("Error al calcular media del usuario: " + e.getMessage());
            return 0.0f;
        }
    }
}
