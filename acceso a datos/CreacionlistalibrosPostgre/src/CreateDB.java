import java.sql.*;
import java.util.ArrayList;

public class CreateDB {
    private static Connection conection;
    private static String url = "jdbc:postgresql://localhost:5432/";

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
    public static void creacionInicial(){
        try {
        conection = DriverManager.getConnection(url, "postgres", "abc123.");
        // Existe, la eliminamos y la volvemos a crear
        Statement stmt = conection.createStatement();
        try {
            stmt.executeUpdate("DROP DATABASE " + "listalibros");
        }catch (Exception e) {
            System.out.println("No existe la base de datos");
        }
        stmt = conection.createStatement();
        stmt.executeUpdate("CREATE DATABASE " + "listalibros" + ";");
        conection.close();

    } catch (SQLException e) {
    }


    }

    public static void creaListaLibros() {
            try{
            conection = DriverManager.getConnection(url+"listalibros", "postgres", "abc123.");
            PreparedStatement sentencia2 = conection.prepareStatement(
                "Create Schema objetos;"
                +"create type objetos.Autor as(nombre_autor char(90),fecha varChar(90));"
                +"create table if not exists objetos.libros (id int  primary key,titulo Char(90),autor objetos.Autor,año_publicacion int);");
            sentencia2.executeLargeUpdate();
            conection.close();

        }catch (SQLException e) {
        System.out.println("hubo un error en la creacion 2da parte");
        }
    }


}
