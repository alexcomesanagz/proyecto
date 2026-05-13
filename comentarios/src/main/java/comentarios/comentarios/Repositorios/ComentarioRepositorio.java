package comentarios.comentarios.Repositorios;

import comentarios.comentarios.DTO.ComentarioHotelDTO;
import comentarios.comentarios.Entidades.Comentarios;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComentarioRepositorio extends MongoRepository<Comentarios, String> {
    boolean existsByUsuarioIdAndHotelIdAndReservaId(int usuarioId, int hotelId, int reservaId);

//    @Query("SELECT new com.tuproyecto.dto.ComentarioHotelDTO(" +
//            "h.nombre, r.id, c.puntuacion, c.textoComentario) " +
//            "FROM Comentarios c " +
//            "JOIN c.reserva r " +
//            "JOIN r.hotel h " +
//            "WHERE h.nombre = :nombreHotel")
    List<ComentarioHotelDTO> buscarComentariosPorNombreHotel(@Param("nombreHotel") String nombreHotel);

}
