import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

public class CreateDB {
    private static Connection conection;
    private String url = "jdbc:postgresql://localhost:5432/Formula";

    private CreateDB() {
        try {
            conection = DriverManager.getConnection(url, "postgres", "abc123.");
        } catch (SQLException e) {
            System.out.println("hubo un error en la creacion");
        }
    }

    public static Connection getConection() {
        if (conection == null) {
            new CreateDB();
        }
        return conection;
    }

    public static void close() {
        try {
            conection.close();
        } catch (SQLException e) {
            System.out.println("sucedio algo");
        }
    }
}
