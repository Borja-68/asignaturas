package Repositorios;

import org.hibernate.Session;
import org.hibernate.Transaction;

import Entidades.Emp;

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
}