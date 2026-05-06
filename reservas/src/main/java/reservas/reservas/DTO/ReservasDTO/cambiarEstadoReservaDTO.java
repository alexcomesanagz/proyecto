package reservas.reservas.DTO.ReservasDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import reservas.reservas.Enums.EstadoReserva;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class cambiarEstadoReservaDTO {
    private int reserva_id;
    private EstadoReserva estado;
    private String usuario;
    private String contrasena;
}
