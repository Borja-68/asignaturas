package Entidades;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "Emps")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Emp{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NonNull
    private String nombre;
    @NonNull
    private String puesto;
    @NonNull
    private double sueldo;
    @NonNull
    private int edad;
    @NonNull
    private String DNI;
    @NonNull
    private boolean esJefe;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "dep_id")
    private Depto departamento;


    public void setDepto(Depto depto){
        this.departamento= depto;
    }

    @Override
    public String toString(){
        return id+" "+nombre+" "+puesto+" "+sueldo+" "+edad+" "+DNI+" "+esJefe+" "+departamento.getId();
    }
}
