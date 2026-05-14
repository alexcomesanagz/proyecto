package comentarios.comentarios.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EliminarComentarioDTO {
    private String id_comentario;
    private String usuario;
    private String contrasena;
}
