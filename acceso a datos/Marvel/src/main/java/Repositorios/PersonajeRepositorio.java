package Repositorios;


import Entidades.Evento;
import Entidades.Personaje;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;


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


    public void borrar(int id){
        Transaction trx = this.session.beginTransaction();
        Query query = session.createQuery("FROM Personaje p WHERE p.id=:per_id");
        query.setParameter("per_id", id);
        Personaje personaje = (Personaje) query.getSingleResult();
        System.out.println(personaje);
        session.remove(personaje);
        System.out.println("Todo bien");
        trx.commit();
    }

    public void modificar(int id,Personaje personajeCambios){
        Transaction trx = this.session.beginTransaction();
        Query query = session.createQuery("FROM Personaje p WHERE p.id=:per_id");
        query.setParameter("per_id", id);
        Personaje personaje = (Personaje) query.getSingleResult();
        personaje.setNombre(personajeCambios.getNombre());
        personaje.setAlias(personajeCambios.getAlias());
        session.merge(personaje);
        System.out.println("Todo bien");
        trx.commit();
    }

    public void muestraId(){
        Query query = session.createQuery("select id FROM Personaje p d");
        List<Object[]> personajes =  query.list();
        for (Object[] personaje: personajes){
           System.out.println(personaje[0]);
        }
    }

}