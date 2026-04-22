package reservas.reservas.Entidades;

import jakarta.persistence.*;
import lombok.*;
import reservas.reservas.Enums.TipoHabitacion;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "habitacion")
@Data
@AllArgsConstructor
@NoArgsConstructor
// Evita incluir los campos 'hotel' y 'reservas' en el toString()
// para prevenir bucles infinitos debido a relaciones bidireccionales
// entre entidades (Hotel ↔ Habitacion ↔ Reserva).
@ToString(exclude = {"hotel", "reservas"})
public class Habitacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "habitacion_id")
    private int habitacion_id;

    @Column(name= "numero_habitacion")
    private int numero_habitacion;

    @Enumerated(EnumType.STRING)
    @Column(length = 50) //varchar(50)
    private TipoHabitacion tipo;

    @Column(precision = 10, scale = 2) //decimal(10,2)
    private BigDecimal precio;
    private boolean disponible;

    //muchas habitaciones a un hotel
    @ManyToOne
    @JoinColumn(name= "hotel_id")
    private Hotel hotel;

    //una habitación con muchas reservas
    @OneToMany(mappedBy = "habitacion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reserva> reservas = new ArrayList<>();

    public void addReserva (Reserva r){
        reservas.add(r);
        r.setHabitacion(this);
    }

    public void removeReserva (Reserva r){
        reservas.remove(r);
        r.setHabitacion(null);
    }
}
