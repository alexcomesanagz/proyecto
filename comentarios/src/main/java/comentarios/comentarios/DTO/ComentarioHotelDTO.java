package comentarios.comentarios.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComentarioHotelDTO {
    private String nombreHotel;
    private int reserva_id; // Ajusta a Long si tus IDs son Long
    private double puntuacion;
    private String comentario;
}
