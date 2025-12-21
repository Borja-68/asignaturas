package Repositorios;


import Entidades.Habilidad;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;


public class HabilidadRepositorio implements Repositorio<Habilidad>{

    private Session session;

    public HabilidadRepositorio(Session session) {
        super();
        this.session = session;
    }

    @Override
    public void guardar(Habilidad t) {
        Transaction trx = this.session.beginTransaction();
        Query query = session.createQuery("select Max(id) FROM Habilidad h");
        Integer id=1+(Integer) query.getSingleResult();
        t.setId(id);
        session.persist(t);
        trx.commit();
    }


    public void borrar(Habilidad habilidad){
        Transaction trx = this.session.beginTransaction();
        session.remove(habilidad);
        trx.commit();
        System.out.println("Todo bien");
    }

    public void modificar(Habilidad habilidadCambios){
        Transaction trx = this.session.beginTransaction();
        session.merge(habilidadCambios);
        trx.commit();
        System.out.println("Todo bien");

    }



    public Habilidad getHabilidadPorId(int id){
        Query query = session.createQuery("FROM Habilidad h WHERE h.id=:hab_id");
        query.setParameter("hab_id", id);
        return (Habilidad) query.getSingleResult();

    }

    @Override
    public void muestraId(){
        Query query = session.createQuery("select id,nombre FROM Habilidad h");
        List<Object[]> habilidades =  query.list();
        for (Object[] habilidad: habilidades){
            System.out.println(habilidad[0]+" "+habilidad[1]);
        }
    }


    public Habilidad getHabilidadPorNombre(String nombre){
        Query query = session.createQuery("FROM Habilidad h WHERE h.nombre=:hab_nombre");
        query.setParameter("hab_nombre", nombre);
        return (Habilidad) query.getSingleResult();

    }



}