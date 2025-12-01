package Repositorios;

import Entidades.Depto;
import Entidades.Emp;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;


public class DeptoRepositorio implements Repositorio<Depto>{

    private Session session;

    public DeptoRepositorio(Session session) {
        super();
        this.session = session;
    }

    @Override
    public void guardar(Depto t) {
        Transaction trx = this.session.beginTransaction();
        session.persist(t);
        trx.commit();
    }

    public void visualizarDepartamento(int id){
        Query query = session.createQuery("FROM Depto d WHERE d.id=:dep_id");
        query.setParameter("dep_id", id);
        Depto departamento = (Depto) query.getSingleResult();
        System.out.println(departamento);
    }

    public void añadirEmpleado(int idDepartamento, Emp empleado){
        Query query = session.createQuery("FROM Depto d WHERE d.id=:dep_id");
        query.setParameter("dep_id", idDepartamento);
        Depto departamento = (Depto) query.getSingleResult();
        departamento.nuevoEmpleado(empleado);
        EmpRepositorio empRepositorio = new EmpRepositorio(session);
        empRepositorio.guardar(empleado);
    }
    public void eliminarDepartamento(int id){
        Query query = session.createQuery("FROM Depto d WHERE d.id=:dep_id");
        query.setParameter("dep_id", id);
        Depto departamento = (Depto) query.getSingleResult();
        session.remove(departamento);
    }
}