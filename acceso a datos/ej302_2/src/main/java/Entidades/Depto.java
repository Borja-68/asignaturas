package Entidades;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Deptos")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Depto{
    @Column(name = "dep_id",columnDefinition = "Integer")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NonNull
    private String nombre;

    @NonNull
    private String localidad;

    @OneToMany(mappedBy = "departamento",cascade = CascadeType.ALL)
    private List<Emp> empleados=new ArrayList<>();


    public void nuevoEmpleado(Emp empleado){
        empleados.add(empleado);
        empleado.setDepto(this);
    }
}
