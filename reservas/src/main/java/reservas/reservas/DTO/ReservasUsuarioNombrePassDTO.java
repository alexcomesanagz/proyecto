package reservas.reservas.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservasUsuarioNombrePassDTO {
    private String usuario;
    private String contrasena;
}
