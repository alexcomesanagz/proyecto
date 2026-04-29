package reservas.reservas.Servicios;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import reservas.reservas.DTO.HotelesDTO.crearHotelDTO;
import reservas.reservas.DTO.ReservasDTO.crearReservaDTO;
import reservas.reservas.Entidades.Habitacion;
import reservas.reservas.Entidades.Hotel;
import reservas.reservas.Entidades.Reserva;
import reservas.reservas.Enums.EstadoReserva;
import reservas.reservas.Repositorios.HabitacionRepo;
import reservas.reservas.Repositorios.ReservasRepo;
import usuarios.usuarios.DTO.UsuarioNombrePassDTO;

import java.time.LocalDate;

@Service
public class ReservaServicio {

    @Autowired
    private ReservasRepo reservasRepo;

    @Autowired
    private HabitacionRepo habitacionRepo;

//    @Autowired
//    private UsuarioRepo usuarioRepo;

    public boolean comprobarUsuario(String usuario, String contrasena){
        RestTemplate restTemplate = new RestTemplate();
        String urlServicio = "http://localhost:8502/usuarios/validar";
        UsuarioNombrePassDTO usuarioDTO = new UsuarioNombrePassDTO(usuario, contrasena);

        ResponseEntity<Boolean> response = restTemplate.postForEntity(urlServicio, usuarioDTO, Boolean.class);
        return Boolean.TRUE.equals(response.getBody());
    }

    public int idUsuario(String usuario, String contrasena){
        try {
            RestTemplate restTemplate = new RestTemplate();
            String urlServicio = "http://localhost:8502/usuarios/info/nombre/" + usuario;
            UsuarioNombrePassDTO usuarioDTO = new UsuarioNombrePassDTO(usuario, contrasena);

            ResponseEntity<Integer> response = restTemplate.postForEntity(urlServicio, usuarioDTO, Integer.class);
            return response.getBody();

        }catch(Exception e){
            throw new RuntimeException("No se pudo obtener el id del usuario");
        }
    }

    public void crearReserva(crearReservaDTO dto) {
        if(!comprobarUsuario(dto.getUsuario(), dto.getContrasena())){
            throw new RuntimeException("Usuario no válido");
        }

        int id = idUsuario(dto.getUsuario(), dto.getContrasena());

        Reserva reserva = new Reserva();
        reserva.setFecha_inicio(LocalDate.parse(dto.getFecha_inicio()));
        reserva.setFecha_fin(LocalDate.parse(dto.getFecha_fin()));
        reserva.setUsuario(id);
        reserva.setEstado(EstadoReserva.valueOf("Confirmada"));

        //buscar la habitacion
        Habitacion habitacion = habitacionRepo.findById(dto.getHabitacion_id())
                .orElseThrow(() -> new RuntimeException("Habitación no encontrada"));
        //asignar relación
        reserva.setHabitacion(habitacion);

        reservasRepo.save(reserva);
    }

}
