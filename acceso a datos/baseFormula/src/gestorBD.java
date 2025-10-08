import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class gestorBD {

    public static void menu() {
        Connection conexion = CreateDB.getConection();
        boolean salida = false;
        int eleccion = -1;
        Scanner input = new Scanner(System.in);
        do {
            try {

                System.out.println("Elige lo que quieres hacer: " + "\n" + "1(Listar todos los equipos y sus directores) " + "\n" + "2(Obtener los pilotos y sus equipos actuales) " + "\n" + "3(Resultados de una carrera específica)" + "\n" + "4(Piloto más viejo)" + "\n" + "5(Número de victorias por equipo)" + "\n" + "6(salir)");
                eleccion = input.nextInt();
                input.nextLine();
                switch (eleccion) {
                    case 1:
                        consultaListaEquipos(conexion);
                        break;
                    case 2:
                        consultaPilotosYEquipo(conexion);
                        break;
                    case 3:
                        consultaResultadosCarrera(conexion,input);
                        break;
                    case 4:
                        consultaPilotoMasViejo(conexion);
                        break;
                    case 5:
                        consultaVictoriasPorEquipo(conexion);
                        break;
                    case 6:
                        CreateDB.close();
                        input.close();
                        salida = true;
                        break;
                    default:
                        System.out.println("no introdujo un numero para las opciones validas");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("se introdujo un elemento que no era un numero");
                input.nextLine();
            }
        } while (!salida);
    }


    private static void consultaListaEquipos(Connection conexion) {
        ResultSet resultado = AccionesDB.listaDeEquipos(conexion);
        try {
            while (resultado.next()) {
                System.out.println(resultado.getString(1)
                        + " " + resultado.getString(2));
            }
        } catch (SQLException e) {
            System.out.println("sucedio un error en el resultado");
        }catch (NullPointerException e){
            System.out.println("resultado vacio");
        }
    }

    private static void consultaPilotosYEquipo(Connection conexion) {
        ResultSet resultado = AccionesDB.obtenerPilotosConEquipos(conexion);
        try {
            while (resultado.next()) {
                System.out.println(resultado.getString(1)
                        + " " + resultado.getString(2));
            }
        } catch (SQLException e) {
            System.out.println("sucedio un error en el resultado");
        }catch (NullPointerException e){
            System.out.println("resultado vacio");
        }
    }

    private static void consultaResultadosCarrera(Connection conexion, Scanner input) {
        ResultSet resultado = AccionesDB.obtenerresultadosSegunCarrera(conexion, input);
        try {
            while (resultado.next()) {
                System.out.println(
                                resultado.getInt(1)
                                + " " + resultado.getInt(2)
                                + " " + resultado.getInt(3)
                                + " " + resultado.getInt(4)
                                + " " +resultado.getString(5));
            }
        } catch (SQLException e) {
            System.out.println("sucedio un error en el resultado");
        }catch (NullPointerException e){
            System.out.println("resultado vacio");
        }
    }

    private static void consultaPilotoMasViejo(Connection conexion) {
        ResultSet resultado = AccionesDB.obtenerPilotoMasViejo(conexion);
        try {
            while (resultado.next()) {
                System.out.println(resultado.getString(1)
                        + " " + resultado.getString(2));
            }
        } catch (SQLException e) {
            System.out.println("sucedio un error en el resultado");
        }catch (NullPointerException e){
            System.out.println("resultado vacio");
        }
    }

    private static void consultaVictoriasPorEquipo(Connection conexion) {
        ResultSet resultado = AccionesDB.obtenerVictoriasPorEquipo(conexion);
        try {
            while (resultado.next()) {
                System.out.println(resultado.getInt(1)
                        + " " + resultado.getString(2));
            }
        } catch (SQLException e) {
            System.out.println("sucedio un error en el resultado");
        }catch (NullPointerException e){
            System.out.println("resultado vacio");
        }
    }

}

