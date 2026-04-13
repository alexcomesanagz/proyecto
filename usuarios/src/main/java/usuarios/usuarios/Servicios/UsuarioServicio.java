package usuarios.usuarios.Servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import usuarios.usuarios.DTO.UsuarioSinIdDTO;
import usuarios.usuarios.Entidades.Usuario;
import usuarios.usuarios.Repositorios.UsuarioRepo;

@Service
public class UsuarioServicio {

    @Autowired
    private UsuarioRepo usuarioRepo;

    public void crearUsuario(UsuarioSinIdDTO usuarioSinIdDTO) {
        Usuario usuario = new Usuario();
        usuario.setNombre(usuarioSinIdDTO.getNombre());
        usuario.setCorreo(usuarioSinIdDTO.getCorreo_electronico());
        usuario.setDireccion(usuarioSinIdDTO.getDireccion());
        usuario.setContrasena(usuarioSinIdDTO.getContrasena());

        usuarioRepo.save(usuario);
    }

}
