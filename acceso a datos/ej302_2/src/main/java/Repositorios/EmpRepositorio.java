package Repositorios;

import Entidades.Emp;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;


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
}