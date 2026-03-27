package com.example.plantillaSpring.Repos;

import com.example.plantillaSpring.Entidades.Emp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepoEmp extends JpaRepository<Emp,Integer> {
    @Query("select* from emp where (nomemp like :letra' and sal>:sal) or (comision is not null and numdep=:numdep);")
    List<Emp> nombreEmpleadoLetraSalariooComicionDepartamento(@Param("letra") char letra,@Param("sal") double slario,@Param("numdep") int numdep);

    @Query("select nomemp,puesto,feccont from emp where puesto!=:puesto;")
    List<Emp> nombreyFechaEmpleadosNoPuesto(@Param("puesto") String puesto);

    List<Emp> findByComisionIsNotNull();

    @Query("select * from emp where sal>(select sal from emp where numemp=:numemp) order by sal;")
    List<Emp> empleadosSalarioMayorASalariodeOtroCompanhero(@Param("numemp") int numemp);
}
