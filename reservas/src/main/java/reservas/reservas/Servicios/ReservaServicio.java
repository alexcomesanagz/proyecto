package reservas.reservas.Servicios;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import reservas.reservas.DTO.HotelesDTO.crearHotelDTO;
import reservas.reservas.DTO.ReservasDTO.cambiarEstadoReservaDTO;
import reservas.reservas.DTO.ReservasDTO.crearReservaDTO;
import reservas.reservas.DTO.ReservasDTO.salidaListarReservasDTO;
import reservas.reservas.DTO.ReservasUsuarioNombrePassDTO;
import reservas.reservas.Entidades.Habitacion;
import reservas.reservas.Entidades.Hotel;
import reservas.reservas.Entidades.Reserva;
import reservas.reservas.Enums.EstadoReserva;
import reservas.reservas.Repositorios.HabitacionRepo;
import reservas.reservas.Repositorios.ReservasRepo;
import usuarios.usuarios.DTO.UsuarioNombrePassDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

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

    public void crearReserva(crearReservaDTO dto) {
        if(!comprobarUsuario(dto.getUsuario(), dto.getContrasena())){
            throw new RuntimeException("Usuario o contraseña incorrectos");
        }

        int id = idUsuario(dto.getUsuario());

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

    public void cambiarEstado(cambiarEstadoReservaDTO dto) {
        if(!comprobarUsuario(dto.getUsuario(), dto.getContrasena())){
            throw new RuntimeException("Usuario o contraseña incorrectos");
        }

        Reserva reserva = reservasRepo.findById((long) dto.getReserva_id())
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));

        reserva.setEstado(dto.getEstado());

        reservasRepo.save(reserva);
    }

    public List<salidaListarReservasDTO> listarReservasUsuario(ReservasUsuarioNombrePassDTO dto) {
        if (!comprobarUsuario(dto.getUsuario(), dto.getContrasena())) {
            throw new RuntimeException("Usuario o contraseña incorrectos");
        }

        int id = idUsuario(dto.getUsuario());
        List<Reserva> reservas = reservasRepo.findByUsuario(id);

        return reservas.stream().map(reserva -> new salidaListarReservasDTO(
                reserva.getFecha_inicio().toString(),
                reserva.getFecha_fin().toString(),
                reserva.getHabitacion().getHabitacion_id()
        )).collect(Collectors.toList());
    }

    public List<salidaListarReservasDTO> listarReservasSegunEstado(ReservasUsuarioNombrePassDTO dto, EstadoReserva estado) {
        if (!comprobarUsuario(dto.getUsuario(), dto.getContrasena())) {
            throw new RuntimeException("Usuario o contraseña incorrectos");
        }

        List<Reserva> reservas = reservasRepo.findByEstado(estado);

        return reservas.stream().map(reserva -> new salidaListarReservasDTO(
                reserva.getFecha_inicio().toString(),
                reserva.getFecha_fin().toString(),
                reserva.getHabitacion().getHabitacion_id()
        )).collect(Collectors.toList());
    }
}
