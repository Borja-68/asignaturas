package com.example.plantillaSpring.Servicios;

import com.example.plantillaSpring.Entidades.Emp;
import com.example.plantillaSpring.Repos.RepoEmp;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ServicioEmp {
    private RepoEmp repoEmp;

    public void guardar(Emp emp){
        repoEmp.save(emp);
    }

    public Emp nuevoDepto(Emp emp){
        return repoEmp.save(emp);
    }

    public Emp remove(int id){
        Emp dep=repoEmp.findById(id).orElse(null);
        repoEmp.deleteById(id);
        return dep;
    }

    public Emp update(Emp emp){
        return repoEmp.save(emp);
    }

    public List<Emp> findAll(){
        return repoEmp.findAll();
    }

    public Emp findById(int id){
        return repoEmp.findById(id).orElse(null);
    }

    public List<Emp> nombreEmpleadoLetraSalariooComicionDepartamento(char letra, double salario,int empresa){
        return repoEmp.nombreEmpleadoLetraSalariooComicionDepartamento(letra,salario,empresa);
    }
    public List<Emp> nombreyFechaEmpleadosNoPuesto(String puesto){
        return  repoEmp.nombreyFechaEmpleadosNoPuesto(puesto);
    }
    public List<Emp> findByComisionIsNotNull(){
        return repoEmp.findByComisionIsNotNull();
    }
    public List<Emp> empleadosSalarioMayorASalariodeOtroCompanhero(int empresa){
        return repoEmp.empleadosSalarioMayorASalariodeOtroCompanhero(empresa);
    }

}
