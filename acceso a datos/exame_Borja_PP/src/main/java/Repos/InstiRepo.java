package Repos;

import Entidades.Ciclo;
import Entidades.Director;
import Entidades.Instituto;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class InstiRepo {
    public org.hibernate.Session sesion;

    public InstiRepo(Session sesion){
        super();
        this.sesion=sesion;
    }

    public void guardar(Instituto insti){
       Transaction tansaccion= sesion.beginTransaction();
        sesion.persist(insti);
        tansaccion.commit();
    }

    public void borrar(int codigo){
        Transaction tansaccion= sesion.beginTransaction();
        try {
            Query consulta = sesion.createQuery("From Instituto i where i.codigo=:codigo");
            consulta.setParameter("codigo", codigo);
            Instituto insti = (Instituto) consulta.getSingleResult();
            sesion.remove(insti);
        }catch (Exception e){
            System.out.println("ocurrio un erro, operacion no realizada");
            tansaccion.rollback();
            return;
        }
        tansaccion.commit();
    }

    public void actualizarTelefono(int codigo,String telefono){
        Transaction tansaccion= sesion.beginTransaction();
        try {
            Query consulta = sesion.createQuery("From Instituto i where i.codigo=:codigo");
            consulta.setParameter("codigo", codigo);
            Instituto insti = (Instituto) consulta.getSingleResult();
            insti.setTf(telefono);
            sesion.merge(insti);
        }catch (Exception e){
            System.out.println("ocurrio un erro, operacion no realizada");
            tansaccion.rollback();
            return;
        }
        tansaccion.commit();
    }

    public void actualizar(Instituto insti){
        Transaction tansaccion= sesion.beginTransaction();
        sesion.merge(insti);
        tansaccion.commit();
    }

    public Instituto instiExiste(int codigo) {
        try {
            Query consulta = sesion.createQuery("From Instituto i where i.codigo=:codigo");
            consulta.setParameter("codigo", codigo);
            Instituto insti = (Instituto) consulta.getSingleResult();
            return insti;
        } catch (Exception e) {
            System.out.println("no existia instituto con el codigo introducido");
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    public void obtenerInstiPorDirector(int id){
        Query consulta = sesion.createQuery("select i.nombre, i.tf From Instituto i join i.director d where d.id=:codigo");
        consulta.setParameter("codigo",id);
        Object[] insti=(Object[]) consulta.getSingleResult();
        System.out.println(insti[0]+"  "+insti[1]);
    }


}
