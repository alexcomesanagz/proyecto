package reservas.reservas.Controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reservas.reservas.DTO.HotelesDTO.actualizarHotelDTO;
import reservas.reservas.DTO.HotelesDTO.crearHotelDTO;
import reservas.reservas.Servicios.HotelServicio;
import reservas.reservas.DTO.ReservasUsuarioNombrePassDTO;

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

    @PatchMapping
    public ResponseEntity<String> actualizarHotel(@RequestBody actualizarHotelDTO hotel) {
        try {
            hotelServicio.actualizarHotel(hotel);
            return ResponseEntity.ok().body("Hotel actualizado con éxito.");
        } catch (Exception e) {
            return ResponseEntity.ok().body("No se ha podido actualizar el hotel. ERROR: " + e);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> eliminarHotel(@RequestBody ReservasUsuarioNombrePassDTO usuario, @PathVariable Long id) {
        try {
            hotelServicio.eliminarHotel(usuario, id);
            return ResponseEntity.ok().body("Hotel eliminado con éxito.");
        } catch (Exception e) {
            return ResponseEntity.ok().body("No se ha podido eliminar el hotel. ERROR:"+ e);
        }
    }

    @PostMapping("/id/{nombre}")
    public ResponseEntity<String> obtenerIdApartirNombre(@RequestBody ReservasUsuarioNombrePassDTO usuario, @PathVariable String nombre) {
        try {
            int id = hotelServicio.obtenerIdApartirNombre(usuario, nombre);
            String respuesta = "ID: " + id + " | Nombre: " + nombre;
            return ResponseEntity.ok().body(respuesta);
        } catch (Exception e) {
            return ResponseEntity.ok().body("No se ha podido encontrar el hotel con el nombre proporcionado. ERROR:"+ e);
        }
    }

    @PostMapping("/nombre/{id}")
    public ResponseEntity<String> obtenerNombreAPartirId(@RequestBody ReservasUsuarioNombrePassDTO usuario, @PathVariable Long id) {
        try {
            String nombre = hotelServicio.obtenerNombreAPartirId(usuario, id);
            return ResponseEntity.ok().body("ID: "+ id +" | Nombre: "+ nombre);
        } catch (Exception e) {
            return ResponseEntity.ok().body("No se ha podido encontrar el hotel con la ID proporcionada. ERROR:"+ e);
        }
    }
}
