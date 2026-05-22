package reservas.reservas.Repositorios;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reservas.reservas.Entidades.Reserva;
import reservas.reservas.Enums.EstadoReserva;

import java.util.List;

@Repository
public interface ReservasRepo extends JpaRepository<Reserva, Long> {
    List<Reserva> findByUsuario(int usuario);
    List<Reserva> findByEstado(EstadoReserva estado);

    @Query("select count(r) > 0 from Reserva r where r.habitacion.hotel.hotel_id = :hotelId")
    boolean existsByHotelId(@Param("hotelId") int hotelId);

}
