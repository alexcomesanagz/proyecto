package reservas.reservas.Controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reservas.reservas.DTO.HabitacionesDTO.crearHabitacionDTO;
import reservas.reservas.DTO.HotelesDTO.crearHotelDTO;
import reservas.reservas.Entidades.Hotel;
import reservas.reservas.Servicios.HabitacionServicio;
import reservas.reservas.Servicios.HotelServicio;

@RestController
@RequestMapping("/reservas/hotel")
public class HotelControlador {

    private final HotelServicio hotelServicio;

    @Autowired
    public HotelControlador(HotelServicio hotelServicio) {
        this.hotelServicio = hotelServicio;
    }

    @PostMapping
    public ResponseEntity<String> crearHotel(@RequestBody crearHotelDTO hotel) {
        try {
            hotelServicio.crearHotel(hotel);
            return ResponseEntity.ok().body("Hotel creado con éxito.");
        } catch (Exception e) {
            return ResponseEntity.ok().body("No se ha podido crear el hotel. ERROR: " + e);
        }
    }
}
