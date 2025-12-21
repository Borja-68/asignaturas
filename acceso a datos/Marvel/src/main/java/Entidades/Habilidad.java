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
    @Column(columnDefinition = "Integer")
    private Integer id;
    @NonNull
    private String nombre;
    @NonNull
    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @ManyToMany(mappedBy = "habilidades",cascade = CascadeType.ALL)
    private List<Personaje> personajes =new ArrayList<>();


    public void addPersonaje(Personaje personaje){
        personajes.add(personaje);
    }

    @Override
    public String toString(){
        return id+", "+nombre+", "+descripcion;
    }
}
