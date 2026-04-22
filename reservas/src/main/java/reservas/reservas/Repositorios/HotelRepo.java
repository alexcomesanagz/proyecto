package reservas.reservas.Repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import reservas.reservas.Entidades.Hotel;

@Repository
public interface HotelRepo extends JpaRepository<Hotel, Long> {
}
