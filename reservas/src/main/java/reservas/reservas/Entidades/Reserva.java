package reservas.reservas.Entidades;

import jakarta.persistence.*;
import lombok.*;
import reservas.reservas.Enums.EstadoReserva;
import usuarios.usuarios.Entidades.Usuario;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "reserva")
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reserva_id")
    private int reserva_id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name= "habitacion_id")
    private Habitacion habitacion;

    private LocalDate fecha_inicio;
    private LocalDate fecha_fin;

    @Column(length = 20) //varchar(20)
    @Enumerated(EnumType.STRING)
    private EstadoReserva estado;
}
