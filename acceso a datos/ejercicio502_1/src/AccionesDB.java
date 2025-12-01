import java.sql.*;
import java.util.ArrayList;

public class AccionesDB {
    public static Connection conection;
    private String url = "jdbc:postgresql://localhost:5432/Formula";

    private AccionesDB() {
        try {
            conection = DriverManager.getConnection(url, "postgres", "abc123.");
        } catch (SQLException e) {
            System.out.println("hubo un error en la creacion");
        }
    }

    public static Connection getConection() {
        if (conection == null) {
            new AccionesDB();
        }
        return conection;
    }



    public static ArrayList showDbInfo() {
        ArrayList<String> listaDatos = new ArrayList<>();
        try {
            DatabaseMetaData dbmd = conection.getMetaData();

            listaDatos.add(dbmd.getDatabaseProductName());
            listaDatos.add(dbmd.getDriverName());
            listaDatos.add(dbmd.getURL());
            listaDatos.add(dbmd.getUserName());
            return listaDatos;
        }
        catch (SQLException e) {
            System.out.println("sucedio un error");
            return listaDatos;
        }
    }






    public static void close () {
        try {
            conection.close();
        }catch (SQLException e) {
            System.out.println("sucedio algo");
        }
    }
}
