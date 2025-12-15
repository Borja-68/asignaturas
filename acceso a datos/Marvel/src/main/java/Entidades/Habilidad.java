package Entidades;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Habilidades")
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
    @OneToMany(mappedBy = "habilidadPersonaje",cascade = CascadeType.ALL)
    private List<PersonajeHabilidad> personajes=new ArrayList<>();

    public void addPersonaje(PersonajeHabilidad personaje){
        personajes.add(personaje);
    }
}
