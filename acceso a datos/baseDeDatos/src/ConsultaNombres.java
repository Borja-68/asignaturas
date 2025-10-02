import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ConsultaNombres {

    public static ResultSet getNamesStartedByN(Scanner input){
        ResultSet result = null;
        try {
            char eleccion;

            do {
                System.out.println("elige la primera letra del nombre");
                eleccion = input.next().charAt(0);
                input.nextLine();
            } while (!Character.isLetter(eleccion));

            PreparedStatement sql = AccionesBD.conection.prepareStatement("select Nombre from empleado Where nombre like ?;");
            sql.setString(1, eleccion+"%");
            sql.executeQuery();
            return  sql.getResultSet();
        }

        catch (SQLException e) {
            System.out.println("consulta no ejecutada, sucedio un error");
            return result;
        }

        catch (InputMismatchException e) {
            System.out.println("Se inserto algo diferente a un numero");
            input.nextLine();
            return result;
        }
    }

    public static void BorraEmpleados(Scanner input){
        ResultSet result = null;
        try {
            String eleccion = "";
            Boolean valido=false;
            do {

                try {
                    System.out.println("elige el codigo del usuario formato xxxxx");
                    eleccion = input.next();
                    input.nextLine();
                    if(stringDigitos(eleccion) && eleccion.length()==5){
                        valido=true;
                    }
                }catch (InputMismatchException e) {
                    System.out.println(" no se introdujo un numero de formato xxxxx");
                    input.nextLine();
                }catch (NumberFormatException e){
                    System.out.println("no se introdujo un numero");
                }
            } while (!valido);

            PreparedStatement sql = AccionesBD.conection.prepareStatement("delete from empleado Where NSS=?;");
            sql.setInt(1, Integer.parseInt(eleccion));
            sql.executeQuery();
            System.out.println("empleado eliminado o no");
        }

        catch (SQLException e) {
            System.out.println("consulta no ejecutada, sucedio un error");
        }

        catch (InputMismatchException e) {
            System.out.println("Se inserto algo diferente a un numero");
            input.nextLine();
        }
    }

    private static Boolean stringDigitos(String cedena){
        for(int i=0;i<cedena.length();i++){
            if(!Character.isDigit(cedena.charAt(i))) return false;
        }
            return true;
    }

    public static void transaccionEmpleados(){
        try {
            PreparedStatement ps = AccionesBD.conection.prepareStatement("INSERT INTO empleado(NSS, Nombre, Numdept) VALUES (?, ?, ?)");
            AccionesBD.conection.setAutoCommit(false);

            ps.setInt(1, 1);
            ps.setString(2, "Pedro");
            ps.setInt(3, 1);
            ps.executeUpdate();

            ps.setInt(1, 2);
            ps.setString(2, "Lucia");
            ps.executeUpdate();

            ps.setInt(1, 3);
            ps.setString(2, "Daniel");
            ps.executeUpdate();

            AccionesBD.conection.commit();

            System.out.println("Todos los datos han sido introducidos");

        } catch (SQLException ex) {
            System.out.println("Error: " + ex.toString());
        }finally {
            try {
                AccionesBD.conection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
