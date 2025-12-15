package Repositorios;


import Entidades.Evento;
import Entidades.Habilidad;
import org.hibernate.Session;
import org.hibernate.Transaction;


public class HabilidadRepositorio implements Repositorio<Habilidad>{

    private Session session;

    public HabilidadRepositorio(Session session) {
        super();
        this.session = session;
    }

    @Override
    public void guardar(Habilidad t) {
        Transaction trx = this.session.beginTransaction();
        session.persist(t);
        trx.commit();
    }

}