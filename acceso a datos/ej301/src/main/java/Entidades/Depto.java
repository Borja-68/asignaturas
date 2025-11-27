package Entidades;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Deptos")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Depto{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dep_id")
    private int id;

    @NonNull
    private String nombre;

    @NonNull
    private String localidad;

    @OneToMany(mappedBy = "departamento")
    private List<Emp> empleados=new ArrayList<Emp>();


    public  void nuevoEmpleado(Emp empleado){
        empleados.add(empleado);
        empleado.setDepto(this);
    }
}
