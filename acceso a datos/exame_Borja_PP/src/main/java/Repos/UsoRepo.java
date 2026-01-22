package Repos;

import Entidades.Ciclo;
import Entidades.Uso;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class UsoRepo {
    public org.hibernate.Session sesion;

    public UsoRepo(Session sesion){
        super();
        this.sesion=sesion;
    }

    public void guardar(Uso uso){
        Transaction tansaccion= sesion.beginTransaction();
        sesion.persist(uso);
        tansaccion.commit();
    }
}
