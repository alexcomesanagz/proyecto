package reservas.reservas.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservasUsuarioNombrePassDTO {
    @JsonProperty("usuario")
    private String usuario;
    private String contrasena;
}
