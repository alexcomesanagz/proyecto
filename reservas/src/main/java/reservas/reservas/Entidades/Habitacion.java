package reservas.reservas.Entidades;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import reservas.reservas.Enums.TipoHabitacion;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "habitacion")
public class Habitacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "habitacion_id")
    private int habitacion_id;

    @NonNull
    @Column(name= "hotel_id")
    private int hotel_id;

    @NonNull
    @Column(name= "numero_habitacion")
    private int numero_habitacion;

    @NonNull
    @Enumerated(EnumType.STRING)
    @Column(name= "tipo")
    private TipoHabitacion tipo;

    @NonNull
    @Column(name= "precio")
    private double precio;

    @NonNull
    @Column(name= "disponible")
    private boolean disponible;
}
