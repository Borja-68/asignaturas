package Repositorios;


import Entidades.Evento;
import Entidades.Participa;
import org.hibernate.Session;
import org.hibernate.Transaction;


public class ParticipaRepositorio implements Repositorio<Participa>{

    private Session session;

    public ParticipaRepositorio(Session session) {
        super();
        this.session = session;
    }

    @Override
    public void guardar(Participa t) {
        Transaction trx = this.session.beginTransaction();
        session.persist(t);
        trx.commit();
    }

}