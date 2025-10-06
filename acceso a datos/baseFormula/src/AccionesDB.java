import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class AccionesDB {
    public AccionesDB() {
    }

    public static ResultSet listaDeEquipos(Connection conexion) {


        try {
            String sql = "select nombre , director from equipos;";
            PreparedStatement consulta = conexion.prepareStatement(sql);
            return consulta.executeQuery();

        } catch (SQLException e) {
            System.out.println("sucedio un error en la ejecucion");
            ResultSet vacio = null;
            return  vacio;
        }catch (NullPointerException e){
            System.out.println("esta vacio");
            ResultSet vacio = null;
            return  vacio;
        }
    }

    public static ResultSet obtenerPilotosConEquipos(Connection conexion) {
        try {
            String sql = "select pilotos.nombre , equipos.nombre from pilotos join equipos on equipos.equipo_id=pilotos.equipo_id;";
            PreparedStatement consulta = conexion.prepareStatement(sql);
            return consulta.executeQuery();

        } catch (SQLException e) {
            System.out.println("sucedio un error en la ejecucion");
            ResultSet vacio = null;
            return  vacio;

        }
    }

    public static ResultSet obtenerresultadosSegunCarrera(Connection conexion, Scanner input) {
        try {
            int numero=-1;
            do{
                try {
                    System.out.println("Inserte el numero de id de la carrera");
                    numero=input.nextInt();
                    input.nextLine();
                }catch (InputMismatchException e){
                    System.out.println("no se introdujo un numero");
                    input.nextLine();
                }
            }while(numero<=-1);

            String sql = "select * from resultados where carrera_id=?;";
            PreparedStatement consulta = conexion.prepareStatement(sql);
            consulta.setInt(1,numero);
            return consulta.executeQuery();

        } catch (SQLException e) {
            System.out.println("sucedio un error en la ejecucion");
            ResultSet vacio = null;
            return  vacio;

        }
    }

    public static ResultSet obtenerPilotoMasViejo(Connection conexion) {
        try {
            String sql = "select nombre,fecha_nacimiento from pilotos where fecha_nacimiento=(select max(fecha_nacimiento) from pilotos);";
            PreparedStatement consulta = conexion.prepareStatement(sql);
            return consulta.executeQuery();

        } catch (SQLException e) {
            System.out.println("sucedio un error en la ejecucion");
            ResultSet vacio = null;
            return  vacio;

        }
    }

    public static ResultSet obtenerVictoriasPorEquipo(Connection conexion) {
        try {
            String sql = "select count(posicion),equipos.nombre from resultados join pilotos on resultados.piloto_id=pilotos.piloto_id join equipos on pilotos.equipo_id=equipos.equipo_id where posicion=1 group by equipos.nombre;";
            PreparedStatement consulta = conexion.prepareStatement(sql);
            return consulta.executeQuery();

        } catch (SQLException e) {
            System.out.println("sucedio un error en la ejecucion");
            ResultSet vacio = null;
            return  vacio;

        }
    }

}
