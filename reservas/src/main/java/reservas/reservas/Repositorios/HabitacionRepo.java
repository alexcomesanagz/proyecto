package reservas.reservas.Repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import reservas.reservas.Entidades.Habitacion;

@Repository
public interface HabitacionRepo extends JpaRepository<Habitacion, Long> {
}
