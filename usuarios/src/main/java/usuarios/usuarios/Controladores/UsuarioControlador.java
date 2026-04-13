package usuarios.usuarios.Controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import usuarios.usuarios.DTO.UsuarioDTO;
import usuarios.usuarios.DTO.UsuarioSinIdDTO;
import usuarios.usuarios.Servicios.UsuarioServicio;

@RestController
@RequestMapping("/usuarios")
public class UsuarioControlador {

    private final UsuarioServicio usuarioServicio;

    @Autowired
    public UsuarioControlador(UsuarioServicio usuarioServicio){
        this.usuarioServicio = usuarioServicio;
    }

    @PostMapping("/registrar")
    private ResponseEntity<String> crearUsuario(@RequestBody UsuarioSinIdDTO usuario){
        usuarioServicio.crearUsuario(usuario);
        return ResponseEntity.ok().body("Usuario creado con éxito.");
    }

}
