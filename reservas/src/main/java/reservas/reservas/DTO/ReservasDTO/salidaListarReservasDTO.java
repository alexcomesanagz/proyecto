package reservas.reservas.DTO.ReservasDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class salidaListarReservasDTO {
    private String fecha_inicio;
    private String fecha_fin;
    private int habitacion_id;
}
