import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionesDB {

    private static Connection conectionPostgre;
    private String urlPostgre = "jdbc:postgresql://localhost:5432/hospital_postgre";
    private String usuarioPostgre="postgres";

    private static Connection conectionMysql;
    private String urlMysql = "jdbc:mysql://localhost:3306/hospital_mysql";
    private String usuarioMysql="root";

    private String contrasena="abc123.";

    private ConexionesDB() {
        try {
            conectionMysql = DriverManager.getConnection(urlMysql, usuarioMysql, contrasena);
            conectionPostgre = DriverManager.getConnection(urlPostgre, usuarioPostgre, contrasena);

        } catch (SQLException e) {
            System.out.println("hubo un error en la creacion");
        }
    }

    public static Connection getConectionMysql() {
        if (conectionMysql == null) {
            new ConexionesDB();
        }
        return conectionMysql;
    }

    public static Connection getConectionPostgre() {
        if (conectionPostgre == null) {
            new ConexionesDB();
        }
        return conectionPostgre;
    }

    public static void close() {
        try {
            conectionMysql.close();
            conectionPostgre.close();
        } catch (SQLException e) {
            System.out.println("sucedio algo");
        }
    }
}
