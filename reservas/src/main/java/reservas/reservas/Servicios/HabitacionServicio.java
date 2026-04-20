package reservas.reservas.Servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import reservas.reservas.DTO.HabitacionesDTO.crearHabitacionDTO;
import reservas.reservas.Entidades.Habitacion;
import reservas.reservas.Repositorios.HabitacionRepo;
import usuarios.usuarios.DTO.UsuarioNombrePassDTO;
import usuarios.usuarios.Entidades.Usuario;
import usuarios.usuarios.Repositorios.UsuarioRepo;

@Service
public class HabitacionServicio {

    @Autowired
    private HabitacionRepo habitacionRepo;

    public boolean comprobarUsuario(String usuario, String contrasena){
        RestTemplate restTemplate = new RestTemplate();
        String urlServicio = "http://localhost:8502/validar";
        UsuarioNombrePassDTO usuarioDTO = new UsuarioNombrePassDTO(usuario, contrasena);

        ResponseEntity<Boolean> responseEntity1 = restTemplate.postForEntity(urlServicio, usuarioDTO, Boolean.class);
        return (Boolean) Boolean.TRUE.equals(responseEntity1.getBody());
    }

    public void crearHabitacion(crearHabitacionDTO dto) {
        if(comprobarUsuario(dto.getUsuario(), dto.getContrasena())){
            throw new RuntimeException("Usuario no válido");
        }

        Habitacion habitacion = new Habitacion();
        habitacion.setNumero_habitacion(dto.getNumero_habitacion());
        habitacion.setTipo(dto.getTipo());
        habitacion.setPrecio(dto.getPrecio());
        habitacion.setHotel_id(dto.getHotel_id());

        habitacionRepo.save(habitacion);
    }
}
