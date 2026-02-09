package Repos;

import Entidades.Ciclo;
import Entidades.Instituto;
import Entidades.Taller;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.ArrayList;

public class TallerRepo {
    public org.hibernate.Session sesion;

    public TallerRepo(Session sesion){
        super();
        this.sesion=sesion;
    }
    public Taller tallerExiste(int codigo) {
        try {
            Query consulta = sesion.createQuery("From Tallere i where i.codigo=:codigo");
            consulta.setParameter("codigo", codigo);
            Taller taller = (Taller) consulta.getSingleResult();
            return taller;
        } catch (Exception e) {
            System.out.println("no existia taller con el codigo introducido");
            throw new ClassCastException();
        }
    }

    public void getTaller(int id){
        Query consulta = sesion.createQuery(" From Taller t join t.listaUsosTaller u join u.cicloUso c join c.listaInstitutos i where i.codigo=:codigo");
        consulta.setParameter("codigo",id);
        ArrayList<Taller> ciclos=(ArrayList<Taller>) consulta.getResultList();
        System.out.println(ciclos);
    }
}
