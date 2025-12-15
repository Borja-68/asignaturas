package Repositorios;


import Entidades.Evento;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;


public class EventoRepositorio implements Repositorio<Evento>{

    private Session session;

    public EventoRepositorio(Session session) {
        super();
        this.session = session;
    }

    @Override
    public void guardar(Evento t) {
        Transaction trx = this.session.beginTransaction();
        session.persist(t);
        trx.commit();
    }

}