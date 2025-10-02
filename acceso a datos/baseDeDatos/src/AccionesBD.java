import java.sql.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class AccionesBD {
    public static Connection conection;
    private String url = "jdbc:mysql://localhost:3306/empleados";

    private AccionesBD() {
        try {
            conection = DriverManager.getConnection(url, "root", "abc123.");
        } catch (SQLException e) {
            System.out.println("hubo un error en la creacion");
        }
    }

    public static Connection getConection() {
        if (conection == null) {
            new AccionesBD();
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
