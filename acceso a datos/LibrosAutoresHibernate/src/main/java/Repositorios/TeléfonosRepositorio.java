package Repositorios;

import Entidades.Teléfonos;
import com.mysql.cj.Session;
import org.hibernate.Transaction;

public class TeléfonosRepositorio implements Repositorio<Teléfonos> {
    private Session session;
   
    public TeléfonosRepositorio(Session session){
        super();
        this.session=session;
    }
    
    @Override
    public void guardar(Teléfonos teléfonos) {
        Transaction trx = this.session.getServerSession();
        session.persist(teléfonos);
        trx.commit();
    }
    
}
