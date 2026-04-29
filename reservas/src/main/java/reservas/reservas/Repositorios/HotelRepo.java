package reservas.reservas.Repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import reservas.reservas.Entidades.Hotel;
import usuarios.usuarios.Entidades.Usuario;

import java.util.Optional;

@Repository
public interface HotelRepo extends JpaRepository<Hotel, Long> {
    Optional<Hotel> findByNombre(String nombre);
}
