package reservas.reservas.Controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reservas.reservas.DTO.HabitacionesDTO.crearHabitacionDTO;
import reservas.reservas.Servicios.HabitacionServicio;

@RestController
@RequestMapping("/habitacion")
public class HabitacionControlador {

    private final HabitacionServicio habitacionServicio;

    @Autowired
    public HabitacionControlador(HabitacionServicio habitacionServicio) {
        this.habitacionServicio = habitacionServicio;
    }

    @PostMapping
    public ResponseEntity<String> crearHabitacion(@RequestBody crearHabitacionDTO habitacion) {
        try {
            habitacionServicio.crearHabitacion(habitacion);
            return ResponseEntity.ok().body("Habitación creada con éxito.");
        } catch (Exception e) {
            return ResponseEntity.ok().body("No se ha podido crear la habitación.");
        }
    }
}
