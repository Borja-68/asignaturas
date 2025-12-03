package Repositorios;

import Entidades.Libros;
import com.mysql.cj.Session;
import org.hibernate.Transaction;

public class LibrosRepositorio implements Repositorio<Libros>{
    private Session session;

    public LibrosRepositorio(Session session){
        super();
        this.session = session;
    }

    @Override
    public void guardar(Libros libros) {
        Transaction trx = this.session.beginTransaction();
        session.persist(libros);
        trx.commit();
    }

}
