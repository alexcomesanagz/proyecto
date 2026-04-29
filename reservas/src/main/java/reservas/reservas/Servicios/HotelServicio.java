package reservas.reservas.Servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import reservas.reservas.DTO.HotelesDTO.actualizarHotelDTO;
import reservas.reservas.DTO.HotelesDTO.crearHotelDTO;
import reservas.reservas.Entidades.Hotel;
import reservas.reservas.Repositorios.HabitacionRepo;
import reservas.reservas.Repositorios.HotelRepo;
import reservas.reservas.DTO.ReservasUsuarioNombrePassDTO;
import usuarios.usuarios.DTO.UsuarioNombrePassDTO;

@Service
public class HotelServicio {

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
        hotel.setDireccion(dto.getDireccion());

        hotelRepo.save(hotel);
    }

    public void actualizarHotel(actualizarHotelDTO dto) {
        if(!comprobarUsuario(dto.getUsuario(), dto.getContrasena())){
            throw new RuntimeException("Usuario no válido");
        }

        Hotel hotel = hotelRepo.findById((long) dto.getHotel_id())
                .orElseThrow(() -> new RuntimeException("Hotel no encontrado"));

        hotel.setNombre(dto.getNombre());
        hotel.setDireccion(dto.getDireccion());

        hotelRepo.save(hotel);
    }

    public void eliminarHotel(ReservasUsuarioNombrePassDTO dto, Long id) {
        if(!comprobarUsuario(dto.getUsuario(), dto.getContrasena())){
            throw new RuntimeException("Usuario no válido");
        }

        Hotel hotel = hotelRepo
                .findById(id).orElseThrow(() -> new RuntimeException("Hotel no encontrado"));

        hotelRepo.delete(hotel);
    }

    public int obtenerIdApartirNombre(ReservasUsuarioNombrePassDTO dto, String nombre) {
        if(!comprobarUsuario(dto.getUsuario(), dto.getContrasena())){
            throw new RuntimeException("Usuario no válido");
        }

        Hotel hotel = hotelRepo.findByNombre(nombre)
                .orElseThrow(() -> new RuntimeException("Hotel no encontrado."));

        return hotel.getHotel_id();
    }

    public String obtenerNombreAPartirId(ReservasUsuarioNombrePassDTO dto, Long id) {
        if(!comprobarUsuario(dto.getUsuario(), dto.getContrasena())){
            throw new RuntimeException("Usuario no válido");
        }

        Hotel hotel = hotelRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Hotel no encontrado."));

        return hotel.getNombre();
    }
}
