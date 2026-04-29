package reservas.reservas.DTO.HotelesDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class actualizarHotelDTO {
    private int hotel_id;
    private String nombre;
    private String direccion;
    private String usuario;
    private String contrasena;
}
