package Repositorios;


import Entidades.Evento;
import Entidades.Personaje;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;


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

    @Override
    public void muestraId() {
        Query query = session.createQuery("select id,nombre FROM Evento h");
        List<Object[]> eventos =  query.list();
        for (Object[] evento: eventos){
            System.out.println(evento[0]+" "+evento[1]);
        }
    }

    public Evento getEventoPorNombre(String nombre){
        Query query = session.createQuery("FROM Evento e WHERE e.nombre=:ev_nombre");
        query.setParameter("ev_nombre", nombre);
        return (Evento) query.getSingleResult();
    }

    public void muestraNombre(){
        Query query = session.createQuery("select nombre FROM Evento e");
        List<String> eventos =  query.list();
        for (String evento: eventos){
            System.out.println(evento);
        }
    }
}