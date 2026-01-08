package Repositorios;


import Entidades.Personaje;
import Entidades.Traje;
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
        Query query = session.createQuery("select Max(id) FROM Personaje p");
        Integer id=1+(Integer) query.getSingleResult();
        t.setId(id);
        session.persist(t);
        trx.commit();
    }

    public void muestraDatosPersonajePorId(int id){
        Query query = session.createQuery("FROM Personaje p WHERE p.id=:per_id");
        query.setParameter("per_id", id);
        System.out.println((Personaje) query.getSingleResult());
    }

    public void modificarTraje(String nombrePers, Traje traje){
        Transaction trx = this.session.beginTransaction();
        Query query = session.createQuery("FROM Personaje p WHERE p.nombre=:per_nomb");
        query.setParameter("per_nomb", nombrePers);
        Personaje personaje = (Personaje) query.getSingleResult();
        personaje.setTraje(traje);
        session.merge(personaje);
        trx.commit();
        System.out.println("Todo bien");
    }

    public void borrar(Personaje personaje){
        Transaction trx = this.session.beginTransaction();
        session.remove(personaje);
        trx.commit();
        System.out.println("Todo bien");

    }

    public void modificar(Personaje personaje){
        Transaction trx = this.session.beginTransaction();
        session.merge(personaje);
        trx.commit();
        System.out.println("Todo bien");

    }

    public void muestraPersonajesEvento(int id){
        Query query = session.createQuery("select p.id,p.nombre,p.alias FROM Personaje p join Participa par on par.personajeEvento.id=p.id where par.eventoPersonaje.id=:ev_id");
        query.setParameter("ev_id", id);
        List<Object[]> personajes =  query.list();
        for (Object[] personaje: personajes){
            System.out.println(personaje[0]+" "+personaje[1]+" "+personaje[2]);
        }
        System.out.println();
    }

    public void cuentaPersonajesPorHabilidad(int id){
        Query query = session.createQuery("select h.nombre,count(p.id) FROM Personaje p join p.habilidades h where h.id=:hab_id");
        query.setParameter("hab_id", id);
        Object[] resultado = (Object[]) query.getSingleResult();

        System.out.println(resultado[0]+" "+resultado[1]);
        System.out.println();
    }

    public Personaje getPersonajePorId(int id){
        Query query = session.createQuery("FROM Personaje p WHERE p.id=:per_id");
        query.setParameter("per_id", id);
        return (Personaje) query.getSingleResult();
    }


    @Override
    public void muestraId(){
        Query query = session.createQuery("select id,nombre FROM Personaje h");
        List<Object[]> personajes =  query.list();
        for (Object[] personaje: personajes){
           System.out.println(personaje[0]+" "+personaje[1]);
        }
    }

    public Personaje getPersonajePorNombre(String nombre){
        Query query = session.createQuery("FROM Personaje p WHERE p.nombre=:per_nombre");
        query.setParameter("per_nombre", nombre);
        return (Personaje) query.getSingleResult();
    }

}