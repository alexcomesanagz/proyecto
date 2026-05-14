package comentarios.comentarios.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NombreHotelUsuarioDTO {
    private String nombreHotel;
    private String usuario;
    private String contrasena;
}
