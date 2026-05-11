package comentarios.comentarios.Repositorios;

import comentarios.comentarios.Entidades.Comentarios;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComentarioRepositorio extends MongoRepository<Comentarios, String> {
}
