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
    @Column(columnDefinition = "Integer")
    private Integer id;

    @NonNull
    private String nombre;
    @NonNull
    private String alias;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_traje")
    @NonNull
    private Traje traje;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "Personaje_Habilidad",
            joinColumns = @JoinColumn(name = "id_personaje") ,
            inverseJoinColumns = @JoinColumn(name = "id_habilidad")
    )
    private List<Habilidad> habilidades =new ArrayList<>();

    @OneToMany(mappedBy = "personajeEvento",cascade = CascadeType.ALL)
    private List<Participa> eventos=new ArrayList<>();


    public void addHabilidad(Habilidad habilidad){
        habilidades.add(habilidad);
        habilidad.addPersonaje(this);
    }
    public void setTraje(Traje traje){
        this.traje=traje;
        traje.setHeroe(this);
    }

    public void addEvento(Participa evento)
    {
        eventos.add(evento);
    }

    private String eventosCadena(){
        String resultado="";
        for(Participa participa: eventos){
            resultado+=participa.toString()+"\n";
        }
        return resultado;
    }

    private String habilidadesCadena(){
        String resultado="";
        for(Habilidad habilidad: habilidades){
            resultado+=habilidad.toString()+"\n";
        }
        return resultado;
    }
    @Override
    public String toString(){
        return  "datos personaje: "+id+nombre+alias+"\n"+
                "datos traje: "+traje.toString()+"\n"+
                "habilidades: "+"\n"+habilidadesCadena()+
                "eventos: "+"\n"+eventosCadena();
    }
}
