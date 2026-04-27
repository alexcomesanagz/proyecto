package reservas.reservas.Servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import reservas.reservas.Controladores.HotelControlador;
import reservas.reservas.DTO.HotelesDTO.crearHotelDTO;
import reservas.reservas.Entidades.Habitacion;
import reservas.reservas.Entidades.Hotel;
import reservas.reservas.Repositorios.HabitacionRepo;
import reservas.reservas.Repositorios.HotelRepo;
import usuarios.usuarios.DTO.UsuarioNombrePassDTO;

@Service
public class HotelServicio {

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

    public void crearHotel(crearHotelDTO dto) {
        if(!comprobarUsuario(dto.getUsuario(), dto.getContrasena())){
            throw new RuntimeException("Usuario no válido");
        }

        Hotel hotel = new Hotel();
        hotel.setNombre(dto.getNombre());

//        //buscar el hotel
//        Hotel hotel = hotelRepo.findById(dto.getHotel_id())
//                .orElseThrow(() -> new RuntimeException("Hotel no encontrado"));
//        //asignar relación
//        habitacion.setHotel(hotel);
//
//        habitacionRepo.save(habitacion);
    }
}
