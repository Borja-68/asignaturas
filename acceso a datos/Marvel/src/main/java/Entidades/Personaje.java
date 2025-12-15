package Entidades;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Personajes")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Personaje {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "per_id",columnDefinition = "Integer")
    private Integer id;
    @NonNull
    private String nombre;
    @NonNull
    private String alias;
    @OneToMany(mappedBy = "personajeHabilidad",cascade = CascadeType.ALL)
    private List<PersonajeHabilidad> habilidades=new ArrayList<>();

    @OneToMany(mappedBy = "personajeEvento",cascade = CascadeType.ALL)
    private List<Participa> eventos=new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "id_traje")
    @Column(unique = true)
    private Traje traje;

    public void addHabilidad(PersonajeHabilidad habilidad){
        habilidades.add(habilidad);
    }
    public void setPersonajeBidireccional(Traje traje){
        this.traje=traje;
        traje.setHeroe(this);
    }

    public void addEvento(Participa evento){
        eventos.add(evento);
    }
}
