package Repositorios;


import Entidades.Evento;
import Entidades.Personaje;
import org.hibernate.Session;
import org.hibernate.Transaction;


public class PersonajeRepositorio implements Repositorio<Personaje>{

    private Session session;

    public PersonajeRepositorio(Session session) {
        super();
        this.session = session;
    }

    @Override
    public void guardar(Personaje t) {
        Transaction trx = this.session.beginTransaction();
        session.persist(t);
        trx.commit();
    }

}