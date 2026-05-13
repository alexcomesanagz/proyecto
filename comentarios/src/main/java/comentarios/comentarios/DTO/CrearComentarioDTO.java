package comentarios.comentarios.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CrearComentarioDTO {
    private String nombreHotel;
    private int reserva_id;
    private double puntuacion;
    private String comentario;
    private String nombre;
    private String contrasena;
}
