package reservas.reservas.DTO.ReservasDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class crearReservaDTO {
    private Long habitacion_id;
    private String fecha_inicio;
    private String fecha_fin;
    private String usuario;
    private String contrasena;
}
