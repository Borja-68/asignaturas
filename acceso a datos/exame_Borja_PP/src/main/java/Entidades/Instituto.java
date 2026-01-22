package Entidades;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "instituto")
@Data
@NoArgsConstructor
public class Instituto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "int(11)")
    private int codigo;

    @Column(nullable = true,columnDefinition = "Varchar(255)")
    private String nombre;

    @Column(nullable = true,columnDefinition = "char(9)")
    private String tf;

    @OneToOne
    @JoinColumn(name = "director")
    private Director director;

    @ManyToMany
    @JoinTable(
            name = "ies_ciclos",
            joinColumns = @JoinColumn(name = "cod_instituto"),
            inverseJoinColumns = @JoinColumn(name = "cod_ciclo")
    )
    private List<Ciclo> listaCiclos=new ArrayList<>();


    public void addCiclo(Ciclo ciclo){
        listaCiclos.add(ciclo);
        ciclo.addInstituto(this);
    }

    public void setDirector(Director director) {
        this.director = director;
        director.setInstituto(this);
    }
}
