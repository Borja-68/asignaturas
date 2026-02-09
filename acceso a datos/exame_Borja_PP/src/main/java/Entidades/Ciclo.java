package Entidades;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ciclo")
@Data
@NoArgsConstructor
public class Ciclo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "int(11)")
    private int codigo;


    @Column(nullable = true,columnDefinition = "Varchar(10)")
    private String nombreCiclo;

    @ManyToMany(mappedBy = "listaCiclos")
    private List<Instituto> listaInstitutos=new ArrayList<>();

    @OneToMany(mappedBy = "cicloUso")
    private List<Uso> listaUsosCiclo=new ArrayList<>();


    public void  addUso(Uso uso){
        listaUsosCiclo.add(uso);
    }

    public void  addInstituto(Instituto insti){
        listaInstitutos.add(insti);
    }
    @Override
    public String toString(){
        return codigo+"   "+nombreCiclo;
    }
}
