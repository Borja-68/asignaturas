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
public class Evento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "Integer")
    private Integer id;
    @NonNull
    private String nombre;
    @NonNull
    private String lugar;

    @OneToMany(mappedBy = "eventoPersonaje",cascade = CascadeType.ALL)
    private List<Participa> personajes=new ArrayList<>();

    public void addPersonaje(Participa personaje){
        personajes.add(personaje);
    }
}
