package Repositorios;

import Entidades.Emp;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;


public class EmpRepositorio implements Repositorio<Emp>{

    private Session session;

    public EmpRepositorio(Session session) {
        super();
        this.session = session;
    }

    @Override
    public void guardar(Emp t) {
        Transaction trx = this.session.beginTransaction();
        session.persist(t);
        trx.commit();
    }

    public void actualizarJefeDepartamento(int idDepartamento,int idEmpl) {
        Transaction trx = this.session.beginTransaction();
        Query queryNoJefe = session.createQuery("FROM Emp e WHERE e.departamento.id=:dep_id and e.esJefe=true");
        queryNoJefe.setParameter("dep_id", idDepartamento);
        if(queryNoJefe.getSingleResult()!=null) {
            Emp empleadoNoJefe = (Emp) queryNoJefe.getSingleResult();
            empleadoNoJefe.setEsJefe(false);
            session.merge(empleadoNoJefe);
        }
        Query queryJefe = session.createQuery("FROM Emp e WHERE e.departamento.id=:dep_id and e.id=:emp_id");
        queryJefe.setParameter("dep_id", idDepartamento);
        queryJefe.setParameter("emp_id", idEmpl);
        Emp empleadoJefe = (Emp) queryJefe.getSingleResult();
        empleadoJefe.setEsJefe(true);
        session.merge(empleadoJefe);
        trx.commit();
    }
    public void empleadosTécnicos() {
        Query empleadosTecnicos = session.createQuery("Select id,nombre FROM Emp e WHERE e.puesto='Técnico'");
        List<Object[]> empleados = empleadosTecnicos.list();
        for (Object[] empleado : empleados) {
            System.out.println(empleado[0] + " " + empleado[1]);
        }
    }
    public void empleadoConMayorSueldo() {
        Query empleadoMayorSueldo = session.createQuery("FROM Emp e order by e.sueldo desc limit 1");
        Emp empleado =(Emp) empleadoMayorSueldo.getSingleResult();
        System.out.println(empleado);
    }

    public void aumentarSalarioJefes(int cantidad) {
        Transaction trx = this.session.beginTransaction();
        Query aumentoSalario = session.createQuery("FROM Emp e where e.esJefe=true");
        List<Emp> jefes = aumentoSalario.list();
        for (Emp jefe:jefes){
            jefe.setSueldo(jefe.getSueldo()+cantidad);
            session.merge(jefe);
        }
        trx.commit();
    }

    public void borrarEmpleadosDepartamento(int idDepartamento) {
        Transaction trx = this.session.beginTransaction();
        Query quitaEmpleadosDepartamento = session.createQuery("FROM Emp e Where e.departamento.id=:dep_id");
        quitaEmpleadosDepartamento.setParameter("dep_id",idDepartamento);
        List<Emp> empleados = quitaEmpleadosDepartamento.list();

        for (Emp empleado:empleados){
            session.remove(empleado);
        }
        trx.commit();
    }

}