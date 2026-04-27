package reservas.reservas.DTO.HotelesDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import reservas.reservas.Enums.TipoHabitacion;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class crearHotelDTO {
    private String nombre;
    private String direccion;
    private String usuario;
    private String contrasena;
}
