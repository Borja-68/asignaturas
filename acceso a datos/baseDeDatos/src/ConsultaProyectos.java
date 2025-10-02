import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ConsultaProyectos {

    public static ResultSet selectAllProyectos() {
        ResultSet result = null;
        try {
            Statement sql = AccionesBD.conection.createStatement();
            result = sql.executeQuery("select * from proyecto;");
            return result;
        } catch (SQLException e) {
            System.out.println("consulta no ejecutada, sucedio un error");
            return result;
        }
    }
    public static void insercion() {
        try {
            PreparedStatement sql = AccionesBD.conection.prepareStatement("insert into proyecto values(11, 'Acceso a datos', 'Lugo', 3);");
            sql.executeUpdate();
        }
        catch (SQLException e) {
            System.out.println("consulta no ejecutada, sucedio un error");
        }
    }

    public static void modificacion() {
    }

    public static void eliminacion(Scanner input) throws SQLException {
        ResultSet result = null;
        try {
            int eleccion = -1;

            do {
                System.out.println("elige el codigo del proyecto a eliminar");
                eleccion = input.nextInt();
                input.nextLine();
            } while (eleccion < 0);

            PreparedStatement sql = AccionesBD.conection.prepareStatement("delete from proyecto Where numproy=?;");
            sql.setInt(1, eleccion);
            sql.executeUpdate();
            System.out.println("proyecto eliminado o no");
        }

        catch (SQLException e) {
            System.out.println("consulta no ejecutada, sucedio un error");
        }

        catch (InputMismatchException e) {
            System.out.println("Se inserto algo diferente a un numero");
            input.nextLine();
        }
    }
}
