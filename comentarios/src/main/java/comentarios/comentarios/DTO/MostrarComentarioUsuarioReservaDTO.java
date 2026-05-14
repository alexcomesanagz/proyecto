package comentarios.comentarios.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MostrarComentarioUsuarioReservaDTO {
    private int id_reserva;
    private String usuario;
    private String contrasena;
}
