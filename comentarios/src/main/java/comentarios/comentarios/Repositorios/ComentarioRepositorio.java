package comentarios.comentarios.Repositorios;

import comentarios.comentarios.DTO.ComentarioHotelDTO;
import comentarios.comentarios.Entidades.Comentarios;
import org.springframework.data.mongodb.repository.Aggregation;
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
    List<Comentarios> findByUsuarioId(int usuarioId);
    List<Comentarios> findByReservaId(int reservaId);

    Optional<Comentarios> findByReservaIdAndUsuarioId(int reservaId, int usuarioId);

    @Aggregation(pipeline = {
            "{ '$match': { 'hotelId': ?0 } }",
            "{ '$group': { '_id': null, 'media': { '$avg': '$puntuacion' } } }"
    })
    Double getMediaPuntuacionByHotelId(int hotelId);

    @Aggregation(pipeline = {
            "{ '$match': { 'usuarioId': ?0 } }",
            "{ '$group': { '_id': null, 'media': { '$avg': '$puntuacion' } } }"
    })
    Double getMediaPuntuacionByUsuarioId(int usuarioId);
}
