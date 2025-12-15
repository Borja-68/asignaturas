package Repositorios;


import Entidades.Evento;
import Entidades.PersonajeHabilidad;
import org.hibernate.Session;
import org.hibernate.Transaction;


public class PersonajeHabilidadRepositorio implements Repositorio<PersonajeHabilidad>{

    private Session session;

    public PersonajeHabilidadRepositorio(Session session) {
        super();
        this.session = session;
    }

    @Override
    public void guardar(PersonajeHabilidad t) {
        Transaction trx = this.session.beginTransaction();
        session.persist(t);
        trx.commit();
    }

}