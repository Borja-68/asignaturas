package Repositorios;


import Entidades.Evento;
import Entidades.Traje;
import org.hibernate.Session;
import org.hibernate.Transaction;


public class TrajeRepositorio implements Repositorio<Traje>{

    private Session session;

    public TrajeRepositorio(Session session) {
        super();
        this.session = session;
    }

    @Override
    public void guardar(Traje t) {
        Transaction trx = this.session.beginTransaction();
        session.persist(t);
        trx.commit();
    }

}