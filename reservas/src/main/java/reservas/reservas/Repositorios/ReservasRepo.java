package reservas.reservas.Repositorios;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import reservas.reservas.Entidades.Reserva;

@Repository
public interface ReservasRepo extends JpaRepository<Reserva, Long> {
}
