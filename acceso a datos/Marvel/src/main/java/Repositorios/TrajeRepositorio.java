package Repositorios;


import Entidades.Evento;
import Entidades.Personaje;
import Entidades.Traje;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;


public class TrajeRepositorio implements Repositorio<Traje>{

    private Session session;

    public TrajeRepositorio(Session session) {
        super();
        this.session = session;
    }

    @Override
    public void guardar(Traje t) {
        Transaction trx = this.session.beginTransaction();
        Query query = session.createQuery("select Max(id) FROM Traje t");
        Integer id=1+(Integer) query.getSingleResult();
        t.setId(id);
        session.persist(t);
        trx.commit();
    }

    public Traje getTrajeporId(int id){
        Query query = session.createQuery("FROM Traje t WHERE t.id=:tra_id");
        query.setParameter("tra_id", id);
        return (Traje) query.getSingleResult();

    }



    @Override
    public void muestraId(){
        Query query = session.createQuery("select id FROM Traje t");
        List<Integer> trajes =query.getResultList();

        for (Integer traje:trajes){
            System.out.println(traje);
        }
    }

}