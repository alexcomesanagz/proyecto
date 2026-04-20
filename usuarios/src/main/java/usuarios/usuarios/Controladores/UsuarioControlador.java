package usuarios.usuarios.Controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import usuarios.usuarios.DTO.UsuarioDTO;
import usuarios.usuarios.DTO.UsuarioNombrePassDTO;
import usuarios.usuarios.DTO.UsuarioSinIdDTO;
import usuarios.usuarios.Servicios.UsuarioServicio;

@RestController
@RequestMapping("/usuarios")
public class UsuarioControlador {

    private final UsuarioServicio usuarioServicio;

    @Autowired
    public UsuarioControlador(UsuarioServicio usuarioServicio) {
        this.usuarioServicio = usuarioServicio;
    }

    @PostMapping("/registrar")
    public ResponseEntity<String> crearUsuario(@RequestBody UsuarioSinIdDTO usuario) {
        try {
            usuarioServicio.crearUsuario(usuario);
            return ResponseEntity.ok().body("Usuario creado con éxito.");
        } catch (Exception e) {
            return ResponseEntity.ok().body("No se ha podido crear el usuario.");
        }
    }

    @PutMapping("/registrar")
    public ResponseEntity<String> actualizarUsuario(@RequestBody UsuarioDTO usuario) {
        try {
            usuarioServicio.actualizarUsuario(usuario);
            return ResponseEntity.ok().body("Usuario actualizado con éxito.");
        } catch (Exception e) {
            return ResponseEntity.ok().body("No se ha podido actualizar el usuario.");
        }
    }

    @DeleteMapping
    public ResponseEntity<String> eliminarUsuario(@RequestBody UsuarioNombrePassDTO usuario) {
        try {
            usuarioServicio.eliminarUsuario(usuario);
            return ResponseEntity.ok().body("Usuario eliminado con éxito.");
        } catch (Exception e) {
            return ResponseEntity.ok().body("No se ha podido eliminar el usuario.");
        }
    }

    @PostMapping("/validar")
    public ResponseEntity<Boolean> validarUsuario(@RequestBody UsuarioNombrePassDTO usuario) {
        boolean valido = usuarioServicio.validarUsuario(usuario);
        return ResponseEntity.ok(valido);
    }

    @GetMapping("/info/id/{id}")
    public ResponseEntity<String> obtenerInfoUsuarioPorId(@PathVariable Long id) {
        try {
            String nombre = usuarioServicio.obtenerInfoUsuarioPorId(id);
            return ResponseEntity.ok(nombre);
        } catch (Exception e) {
            return ResponseEntity.ok().body("No se ha podido encontrar el usuario con la ID dada.");
        }
    }

    @GetMapping("/info/nombre/{nombre}")
    public ResponseEntity<String> obtenerInfoUsuarioPorNombre(@PathVariable String nombre) {
        try {
            int id = usuarioServicio.obtenerInfoUsuarioPorNombre(nombre);
            return ResponseEntity.ok(Integer.toString(id));
        } catch (Exception e) {
            return ResponseEntity.ok().body("No se ha podido encontrar el usuario con el nombre dado.");
        }
    }

    @GetMapping("/checkIfExist/{id}")
    public ResponseEntity<Boolean> checkIfExist(@PathVariable int id) {
        boolean exist = usuarioServicio.checkIfExist(id);
        return ResponseEntity.ok(exist);
    }

}
