package reservas.reservas.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioNombrePassDTO {
    private String nombre;
    private String contrasena;
}
