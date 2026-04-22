package reservas.reservas.Entidades;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "hotel")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "habitaciones")
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hotel_id")
    private int hotel_id;

    @Column(length = 100)
    private String nombre;

    @Column(length = 255)
    private String direccion;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Habitacion> habitaciones = new ArrayList<>();

    public void addHabitacion(Habitacion h){
        habitaciones.add(h);
        h.setHotel(this);
    }

    public void removeHabitacion(Habitacion h){
        habitaciones.remove(h);
        h.setHotel(null);
    }
}
