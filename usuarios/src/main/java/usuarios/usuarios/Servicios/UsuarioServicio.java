package usuarios.usuarios.Servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import usuarios.usuarios.DTO.UsuarioDTO;
import usuarios.usuarios.DTO.UsuarioNombrePassDTO;
import usuarios.usuarios.DTO.UsuarioSinIdDTO;
import usuarios.usuarios.Entidades.Usuario;
import usuarios.usuarios.Repositorios.UsuarioRepo;

@Service
public class UsuarioServicio {

    @Autowired
    private UsuarioRepo usuarioRepo;

    public void crearUsuario(UsuarioSinIdDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setNombre(dto.getUsuario());
        usuario.setCorreo(dto.getCorreo_electronico());
        usuario.setDireccion(dto.getDireccion());
        usuario.setContrasena(dto.getContrasena());

        usuarioRepo.save(usuario);
    }

    public void actualizarUsuario(UsuarioDTO dto) {
        if (!checkIfExist(dto.getId())) {
            throw new RuntimeException("Usuario no encontrado");
        }

        Usuario usuario = usuarioRepo.findById((long) dto.getId()).get();

        usuario.setNombre(dto.getUsuario());
        usuario.setCorreo(dto.getCorreo_electronico());
        usuario.setDireccion(dto.getDireccion());
        usuario.setContrasena(dto.getContrasena());

        usuarioRepo.save(usuario);
    }

    public void eliminarUsuario(UsuarioNombrePassDTO dto) {
        Usuario usuario = usuarioRepo
                .findByNombreAndContrasena(
                        dto.getUsuario(),
                        dto.getContrasena()
                ).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        usuarioRepo.delete(usuario);
    }

    public boolean validarUsuario(UsuarioNombrePassDTO dto) {
        return usuarioRepo
                .findByNombreAndContrasena(dto.getUsuario(), dto.getContrasena())
                .isPresent();
    }

    public String obtenerInfoUsuarioPorId(Long id) {
        Usuario usuario = usuarioRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado."));
        return usuario.getNombre();
    }

    public int obtenerInfoUsuarioPorNombre(String nombre) {
        Usuario usuario = usuarioRepo.findByNombre(nombre)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado."));
        return usuario.getUsuario_id();
    }

    public boolean checkIfExist(int id) {
        return usuarioRepo.existsById((long) id);
    }
}
