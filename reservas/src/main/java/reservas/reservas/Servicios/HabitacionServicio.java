package reservas.reservas.Servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import reservas.reservas.DTO.HabitacionesDTO.crearHabitacionDTO;
import reservas.reservas.Entidades.Habitacion;
import reservas.reservas.Entidades.Hotel;
import reservas.reservas.Repositorios.HabitacionRepo;
import reservas.reservas.Repositorios.HotelRepo;
import usuarios.usuarios.DTO.UsuarioNombrePassDTO;

@Service
public class HabitacionServicio {

    @Autowired
    private HabitacionRepo habitacionRepo;
    @Autowired
    private HotelRepo hotelRepo;

    public boolean comprobarUsuario(String usuario, String contrasena){
        RestTemplate restTemplate = new RestTemplate();
        String urlServicio = "http://localhost:8502/validar";
        UsuarioNombrePassDTO usuarioDTO = new UsuarioNombrePassDTO(usuario, contrasena);

        ResponseEntity<Boolean> responseEntity1 = restTemplate.postForEntity(urlServicio, usuarioDTO, Boolean.class);
        return (Boolean) Boolean.TRUE.equals(responseEntity1.getBody());
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
}
