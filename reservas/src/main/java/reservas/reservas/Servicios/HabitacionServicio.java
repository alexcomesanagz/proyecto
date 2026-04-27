package reservas.reservas.Servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import reservas.reservas.DTO.HabitacionesDTO.actualizarHabitacionDTO;
import reservas.reservas.DTO.HabitacionesDTO.crearHabitacionDTO;
import reservas.reservas.Entidades.Habitacion;
import reservas.reservas.Entidades.Hotel;
import reservas.reservas.Repositorios.HabitacionRepo;
import reservas.reservas.Repositorios.HotelRepo;
import usuarios.usuarios.DTO.UsuarioNombrePassDTO;
import usuarios.usuarios.Entidades.Usuario;

@Service
public class HabitacionServicio {

    @Autowired
    private HabitacionRepo habitacionRepo;
    @Autowired
    private HotelRepo hotelRepo;

    public boolean comprobarUsuario(String usuario, String contrasena){
        RestTemplate restTemplate = new RestTemplate();
        String urlServicio = "http://localhost:8502/usuarios/validar";
        UsuarioNombrePassDTO usuarioDTO = new UsuarioNombrePassDTO(usuario, contrasena);

        ResponseEntity<Boolean> response = restTemplate.postForEntity(urlServicio, usuarioDTO, Boolean.class);
        return Boolean.TRUE.equals(response.getBody());
    }

    public void crearHabitacion(crearHabitacionDTO dto) {
        if(!comprobarUsuario(dto.getUsuario(), dto.getContrasena())){
            throw new RuntimeException("Usuario no válido");
        }

        Habitacion habitacion = new Habitacion();
        habitacion.setNumero_habitacion(dto.getNumero_habitacion());
        habitacion.setTipo(dto.getTipo());
        habitacion.setPrecio(dto.getPrecio());
        habitacion.setDisponible(true); //ponemos por defecto que este disponible sin que nos pase nada el user

        //buscar el hotel
        Hotel hotel = hotelRepo.findById(dto.getHotel_id())
                        .orElseThrow(() -> new RuntimeException("Hotel no encontrado"));
        //asignar relación
        habitacion.setHotel(hotel);

        habitacionRepo.save(habitacion);
    }

    public void actualizarHabitacion(actualizarHabitacionDTO dto) {
        if(!comprobarUsuario(dto.getUsuario(), dto.getContrasena())){
            throw new RuntimeException("Usuario no válido");
        }

        Habitacion habitacion = habitacionRepo.findById((long) dto.getHabitacion_id())
                .orElseThrow(() -> new RuntimeException("Habitación no encontrada"));

        habitacion.setHabitacion_id(dto.getHabitacion_id());
        habitacion.setNumero_habitacion(dto.getNumero_habitacion());
        habitacion.setTipo(dto.getTipo());
        habitacion.setPrecio(dto.getPrecio());
        habitacion.setDisponible(dto.isDisponible());

        //buscar el hotel
        Hotel hotel = hotelRepo.findById(dto.getHotel_id())
                .orElseThrow(() -> new RuntimeException("Hotel no encontrado"));
        //asignar relación
        habitacion.setHotel(hotel);

        habitacionRepo.save(habitacion);
    }

    public void eliminarHabitacion(Long id) {
        Habitacion habitacion = habitacionRepo
                .findById(id).orElseThrow(() -> new RuntimeException("Habitación no encontrada"));

        habitacionRepo.delete(habitacion);
    }
}
