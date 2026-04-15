package usuarios.usuarios.Repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import usuarios.usuarios.DTO.UsuarioNombrePassDTO;
import usuarios.usuarios.Entidades.Usuario;

import java.util.Optional;

@Repository
public interface UsuarioRepo extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByNombreAndContrasena(String nombre, String contrasena);
}
