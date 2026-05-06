package reservas.reservas.Controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reservas.reservas.DTO.HabitacionesDTO.actualizarHabitacionDTO;
import reservas.reservas.DTO.HotelesDTO.crearHotelDTO;
import reservas.reservas.DTO.ReservasDTO.cambiarEstadoReservaDTO;
import reservas.reservas.DTO.ReservasDTO.crearReservaDTO;
import reservas.reservas.DTO.ReservasDTO.salidaListarReservasDTO;
import reservas.reservas.DTO.ReservasUsuarioNombrePassDTO;
import reservas.reservas.Entidades.Reserva;
import reservas.reservas.Enums.EstadoReserva;
import reservas.reservas.Servicios.HotelServicio;
import reservas.reservas.Servicios.ReservaServicio;

import java.util.List;

@RestController
@RequestMapping("/reservas")
public class ReservaControlador {

    private final ReservaServicio reservaServicio;

    @Autowired
    public ReservaControlador(ReservaServicio reservaServicio) {
        this.reservaServicio = reservaServicio;
    }

    @PostMapping
    public ResponseEntity<String> crearReserva(@RequestBody crearReservaDTO reserva) {
        try {
            reservaServicio.crearReserva(reserva);
            return ResponseEntity.ok().body("Reserva creada con éxito.");
        } catch (Exception e) {
            return ResponseEntity.ok().body("No se ha podido crear la reserva. ERROR: " + e);
        }
    }

    @PatchMapping
    public ResponseEntity<String> cambiarEstado(@RequestBody cambiarEstadoReservaDTO reserva) {
        try {
            reservaServicio.cambiarEstado(reserva);
            return ResponseEntity.ok().body("Estado actualizado con éxito.");
        } catch (Exception e) {
            return ResponseEntity.ok().body("No se ha podido actualizar el estado de la reserva. ERROR: " + e);
        }
    }

    @GetMapping
    public ResponseEntity<?> listarReservasUsuario(@RequestBody ReservasUsuarioNombrePassDTO dto) {
        try {
            List<salidaListarReservasDTO> reservas = reservaServicio.listarReservasUsuario(dto);

            if (reservas.isEmpty()) {
                return ResponseEntity.ok().body("El usuario no tiene reservas.");
            }

            return ResponseEntity.ok(reservas);

        } catch (Exception e) {
            return ResponseEntity.ok().body("No se han podido encontrar las reservas asociadas a este usuario.");
        }
    }

    @GetMapping("{estado}")
    public ResponseEntity<?> listarReservasSegunEstado(@RequestBody ReservasUsuarioNombrePassDTO dto, @PathVariable EstadoReserva estado) {
        try {
            List<salidaListarReservasDTO> reservas = reservaServicio.listarReservasSegunEstado(dto, estado);

            if (reservas.isEmpty()) {
                return ResponseEntity.ok().body("No existen reservas con ese estado.");
            }

            return ResponseEntity.ok(reservas);

        } catch (Exception e) {
            return ResponseEntity.ok().body("No se han podido encontrar las reservas asociadas a ese estado.");
        }
    }
}
