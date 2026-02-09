package Repos;

import Entidades.Director;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class DirectorRep {
    public org.hibernate.Session sesion;

    public DirectorRep(Session sesion) {
        super();
        this.sesion = sesion;
    }

    public Director directorExiste(int id) {
        try {
            Query consulta = sesion.createQuery("From Director d where d.id=:id");
            consulta.setParameter("id", id);
            Director director = (Director) consulta.getSingleResult();
            return director;
        } catch (Exception e) {
            System.out.println("no existia director con la id introducida");
            throw new RuntimeException();
        }
    }
}
