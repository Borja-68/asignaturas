import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBase {
    private static Connection basePostgre=null;
    private String url="jdbc:postgresql://localhost:5432/examenud1";
    private String usuario="postgres";
    private String contrasena="abc123.";
    private ConexionBase() {
        try {
            basePostgre = DriverManager.getConnection(url, usuario, contrasena);
        } catch (SQLException e) {
            System.err.println("creacion base fallida");
        }
    }

    public static Connection gitConexionPostgre(){
        if(basePostgre==null) new ConexionBase();
        return basePostgre;
    }
    public static void close(){
        try{
            basePostgre.close();
        } catch (SQLException e) {
            System.err.println("base no cerrada");
        }
    }
}
