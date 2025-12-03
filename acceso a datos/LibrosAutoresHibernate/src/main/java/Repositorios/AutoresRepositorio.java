package Repositorios;

import Entidades.Autores;
import org.hibernate.Session;
import org.hibernate.Transaction;


public class AutoresRepositorio implements Repositorio<Autores> {

    private Session session;

    public AutoresRepositorio(Session session) {
        super();
        this.session = session;
    }

    @Override
    public void guardar(Autores t) {
        Transaction trx = this.session.beginTransaction();
        session.persist(t);
        trx.commit();
    }
}