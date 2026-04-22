package reservas.reservas.DTO.HabitacionesDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import reservas.reservas.Enums.TipoHabitacion;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class crearHabitacionDTO {
    private int numero_habitacion;
    private TipoHabitacion tipo;
    private BigDecimal precio;
    private Long hotel_id;
    private String usuario;
    private String contrasena;
}
