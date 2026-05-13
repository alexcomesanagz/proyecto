package comentarios.comentarios.Repositorios;

import comentarios.comentarios.DTO.ComentarioHotelDTO;
import comentarios.comentarios.Entidades.Comentarios;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ComentarioRepositorio extends MongoRepository<Comentarios, String> {
    boolean existsByUsuarioIdAndHotelIdAndReservaId(int usuarioId, int hotelId, int reservaId);

    List<Comentarios> findByHotelId(int hotelId);

//    @Query("SELECT c FROM Comentarios c WHERE c.reserva.id = :reservaId")
//    Optional<Comentarios> buscarComentarioPorReservaId(@Param("reservaId") int reservaId);

}
