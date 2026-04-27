package reservas.reservas.Controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reservas.reservas.DTO.HabitacionesDTO.actualizarHabitacionDTO;
import reservas.reservas.DTO.HabitacionesDTO.crearHabitacionDTO;
import reservas.reservas.Servicios.HabitacionServicio;
import usuarios.usuarios.DTO.UsuarioDTO;
import usuarios.usuarios.DTO.UsuarioNombrePassDTO;

@RestController
@RequestMapping("/reservas/habitacion")
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
            return ResponseEntity.ok().body("No se ha podido crear la habitación. ERROR: " + e);
        }
    }

    @PatchMapping
    public ResponseEntity<String> actualizarHabitacion(@RequestBody actualizarHabitacionDTO habitacion) {
        try {
            habitacionServicio.actualizarHabitacion(habitacion);
            return ResponseEntity.ok().body("Habitación actualizada con éxito.");
        } catch (Exception e) {
            return ResponseEntity.ok().body("No se ha podido actualizar la habitación. ERROR: " + e);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> eliminarHabitacion(@PathVariable Long id) {
        try {
            habitacionServicio.eliminarHabitacion(id);
            return ResponseEntity.ok().body("Habitación eliminada con éxito.");
        } catch (Exception e) {
            return ResponseEntity.ok().body("No se ha podido eliminar la habitación. ERROR:"+ e);
        }
    }
}
