package com.example.plantillaSpring.Entidades;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Emp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "decimal(4,0)")
    private int numemp;
    @NonNull
    @Column(columnDefinition = "varchar(10))")
    private String noemp;
    @NonNull
    @Column(columnDefinition = "varchar(10)")
    private String puesto;
    @NonNull
    @Column(columnDefinition = "date")
    private LocalDate feccont;
    @NonNull
    @Column(columnDefinition = "decimal(7,2)")
    private double sal;
    @Column(columnDefinition = "decimal(7,2)")
    private double comision;


    @ManyToOne
    @JoinColumn(name = "numdep",
    columnDefinition = "decimal(2,0)"
    )
    private Depto depto;

    @OneToOne(mappedBy = "jefe")
    private Depto depJefe;

    public Emp(String nomemp, String puesto, LocalDate feccont, double sal, double comision) {
        this.noemp = nomemp;
        this.puesto = puesto;
        this.feccont = feccont;
        this.sal = sal;
        this.comision = comision;
    }

    public void anadirDepto(Depto dep){
        this.depto=dep;
        depto.anadirEmpleado(this);
    }

    public void anadirjefe(Depto dep){
        this.depJefe=dep;
        depto.setJefe(this);
    }


}
