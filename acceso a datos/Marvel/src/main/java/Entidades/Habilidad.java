package Entidades;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Habilidad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "Integer")
    private Integer id;
    @NonNull
    private String nombre;
    @NonNull
    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @ManyToMany(mappedBy = "listaHabilidades",cascade = CascadeType.ALL)
    private List<Personaje> listaPersonajes;


    public void addPersonaje(Personaje personaje){
        listaPersonajes.add(personaje);
    }
}
