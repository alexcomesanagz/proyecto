package usuarios.usuarios.Entidades;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usuario_id")
    private int usuario_id;

    @NonNull
    @Column(name= "nombre", length = 100)
    private String nombre;

    @NonNull
    @Column(name= "correo_electronico", length = 255)
    private String correo;

    @NonNull
    @Column(name= "direccion", length = 255)
    private String direccion;

    @NonNull
    @Column(name= "contrasena", length = 255)
    private String contrasena;
}
