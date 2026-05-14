package usuarios.usuarios.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioNombrePassDTO {
    @JsonProperty("usuario")
    private String nombre;
    private String contrasena;
}
