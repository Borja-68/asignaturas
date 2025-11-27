package Repositorios;

import Entidades.Depto;
import org.hibernate.Session;
import org.hibernate.Transaction;


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
}