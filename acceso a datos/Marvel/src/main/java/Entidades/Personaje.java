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
public class Personaje {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "Integer")
    private Integer id;
    @NonNull
    private String nombre;
    @NonNull
    private String alias;

    @OneToOne
    @JoinColumn(name = "id_traje")
    private Traje traje;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "Personaje_Habilidad",
            joinColumns = @JoinColumn(name = "id_personaje") ,
            inverseJoinColumns = @JoinColumn(name = "id_habilidad")
    )
    private List<Habilidad> listaHabilidades;

    @OneToMany(mappedBy = "personajeEvento",cascade = CascadeType.ALL)
    private List<Participa> eventos=new ArrayList<>();


    public void addHabilidad(Habilidad habilidad){
        listaHabilidades.add(habilidad);
        habilidad.addPersonaje(this);
    }
    public void setTraje(Traje traje){
        this.traje=traje;
        traje.setHeroe(this);
    }

    public void addEvento(Participa evento){
        eventos.add(evento);
    }

    @Override
    public String toString(){
        return id+"\n"+
            nombre+"\n"+
                alias+"\n"+
                traje.getId()+"\n"+
                listaHabilidades.toString()+"\n"+
                eventos.toString();
    }
}
