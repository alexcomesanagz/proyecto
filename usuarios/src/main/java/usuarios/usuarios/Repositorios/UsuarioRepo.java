package usuarios.usuarios.Repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import usuarios.usuarios.Entidades.Usuario;

@Repository
public interface UsuarioRepo extends JpaRepository<Usuario, Long> {
}
