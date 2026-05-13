package comentarios.comentarios.Controladores;

import comentarios.comentarios.DTO.CrearComentarioDTO;
import comentarios.comentarios.Servicios.ComentariosServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
//@RequestMapping("/comentarios") no tiene ningún efecto en GraphQL.
//GraphQL maneja todas las operaciones bajo una única ruta interna configurada por Spring (usualmente /graphql)

//entonces q empleo?

//chatgpt me dijo esto en properties
/*
# Cambia el endpoint por defecto de GraphQL de /graphql a /comentarios
spring.graphql.path=/comentarios

# Cambia también el endpoint de la interfaz gráfica de GraphiQL para que funcione en consonancia
spring.graphql.graphiql.path=/graphiql
spring.graphql.graphiql.enabled=true
 */
@RequestMapping("/comentarios")

public class ComentarioControlador {

    private final ComentariosServicio comentariosServicio;

    @Autowired
    public ComentarioControlador(ComentariosServicio comentariosServicio) {
        this.comentariosServicio = comentariosServicio;
    }

    //GraphQL requiere que devuelvas directamente el tipo de dato definido en tu esquema .graphqls

    //seguro q debo devolver eso?, me suena q dijeras q era con responseEntity

    //Tipo de Objeto Retornado: Tu esquema GraphQL especifica que la mutación devuelve un TypeHotelReservaPuntuacionComentarioDTO.
    //No puedes devolver un CrearComentarioDTO directamente si no tiene exactamente los mismos campos o si no coincide con lo mapeado

    //aqui me lo vuelve a decir
    @MutationMapping
    public ResponseEntity<CrearComentarioDTO> crearComentario(@Argument CrearComentarioDTO dto) {
        try {
            CrearComentarioDTO comentario = comentariosServicio.crearComentario(dto);
            return ResponseEntity.ok().body(comentario);
        } catch (Exception e) {
            return ResponseEntity.ok().body(null);
        }
    }

    @MutationMapping
    public ResponseEntity<String> eliminarComentarios() {
        try {
            String resultado = comentariosServicio.eliminarComentarios();
            return ResponseEntity.ok().body(resultado);
        } catch (Exception e) {
            return ResponseEntity.ok().body("No se han podido eliminar los usuarios. ERROR:" + e.getMessage());
        }
    }
}
