package com.example.plantillaSpring.Entidades;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class Depto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "decimal(2,0)")
    private int numdep;
    @NonNull
    @Column(columnDefinition = "varchar(14)")
    private String nomdep;
    @NonNull
    @Column(columnDefinition = "varchar(13)")
    private String localidad;

    @OneToMany(mappedBy = "depto")
    @NonNull
    private List<Emp> empleados=new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "codjefe",
    columnDefinition = "decimal(4,0)")
    private Emp jefe;

    public void anadirEmpleado(Emp emp){
        this.empleados.add(emp);
        emp.setDepto(this);
    }
   public void cambiarJefe(Emp emp){
        this.jefe=emp;
        emp.setDepJefe(this);
   }

}
