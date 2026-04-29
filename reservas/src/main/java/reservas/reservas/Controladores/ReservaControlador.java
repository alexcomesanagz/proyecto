package reservas.reservas.Controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reservas.reservas.DTO.HotelesDTO.crearHotelDTO;
import reservas.reservas.DTO.ReservasDTO.crearReservaDTO;
import reservas.reservas.Servicios.HotelServicio;
import reservas.reservas.Servicios.ReservaServicio;

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
}
