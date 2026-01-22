package Entidades;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Taller {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "int(11)")
    private int codigo;


    @Column(nullable = true,columnDefinition = "Varchar(10)")
    private String nombre;

    @OneToMany(mappedBy = "tallerUso")
    private List<Uso> listaUsosTaller=new ArrayList<>();

    public void  addUso(Uso uso){
        listaUsosTaller.add(uso);
    }

    @Override
    public String toString(){
        return codigo+"   "+nombre;
    }
}
