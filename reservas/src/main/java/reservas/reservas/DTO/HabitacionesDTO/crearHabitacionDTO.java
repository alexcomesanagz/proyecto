package reservas.reservas.DTO.HabitacionesDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import reservas.reservas.Enums.TipoHabitacion;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class crearHabitacionDTO {
    private int numero_habitacion;
    private TipoHabitacion tipo;
    private double precio;
    private int hotel_id;
    private String usuario;
    private String contrasena;
}
