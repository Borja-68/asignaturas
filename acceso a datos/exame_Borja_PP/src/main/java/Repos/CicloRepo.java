package Repos;

import Entidades.Ciclo;
import Entidades.Taller;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class CicloRepo {
    public org.hibernate.Session sesion;

    public CicloRepo(Session sesion){
        super();
        this.sesion=sesion;
    }

    public void guardar(Ciclo ciclo){
        Transaction tansaccion= sesion.beginTransaction();
        sesion.persist(ciclo);
        tansaccion.commit();
    }

    public void borrar(int codigo){
        Transaction tansaccion= sesion.beginTransaction();
        try {
            Query consulta = sesion.createQuery("From Ciclo c where c.codigo=:codigo");
            consulta.setParameter("codigo", codigo);
            Ciclo ciclo = (Ciclo) consulta.getSingleResult();
            sesion.remove(ciclo);
        }catch (Exception e){
            System.out.println("ocurrio un erro, operacion no realizada");
            tansaccion.rollback();
            return;
        }
        tansaccion.commit();
    }

    public Ciclo cicloExiste(int codigo) {
        try {
            Query consulta = sesion.createQuery("From Ciclo i where i.codigo=:codigo");
            consulta.setParameter("codigo", codigo);
            Ciclo ciclo = (Ciclo) consulta.getSingleResult();
            return ciclo;
        } catch (Exception e) {
            System.out.println("no existia ciclo con el codigo introducido");
            throw new IllegalArgumentException();
        }
    }

    public void getCiclos(int id){
        Query consulta = sesion.createQuery(" From Ciclo c join c.listaInstitutos i where i.codigo=:codigo");
        consulta.setParameter("codigo",id);
       ArrayList<Ciclo> ciclos=(ArrayList<Ciclo>) consulta.getResultList();
        System.out.println(ciclos);
    }
}
