import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class gestorBD {

    public static void menu() {
        AccionesBD.getConection();
        boolean salida = false;
        int eleccion = -1;
        Scanner input = new Scanner(System.in);
        do {
            try {

                System.out.println("Elige lo que quieres hacer: " + "\n" +
                        "1(mostrar info base de datos) " + "\n" +
                        "2(mostrar informacion de proyectos) " + "\n" +
                        "3(realizar cambios en proyectos)"+"\n"+
                        "4(consulta nombres empleados)"+"\n"+
                        "5(borar empleado)"+"\n"+
                        "6(salir)");
                eleccion = input.nextInt();
                input.nextLine();
                switch (eleccion) {
                    case 1:
                        muestraMetadatos();
                        break;
                    case 2:
                        muestraProyectos();
                        break;
                    case 3:cambiosDatosBase(input);
                        break;
                    case 4:
                        conSultaNombresEmpleados(input);
                        break;
                    case 5:
                        borraEmpleados(input);
                        break;
                    case 6:
                        AccionesBD.close();
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


    private static void muestraMetadatos() {
        ArrayList metadatos = AccionesBD.showDbInfo();
        if (!metadatos.isEmpty()) {
            System.out.println("Información da base de datos");
            System.out.println("----------------------------");
            System.out.println("Xestor: " + metadatos.get(0));
            System.out.println("Conector: " + metadatos.get(1));
            System.out.println("URL: " + metadatos.get(2));
            System.out.println("Usuario: " + metadatos.get(3));
        } else System.out.println("la lista esta vacia");
    }

    private static void cambiosDatosBase(Scanner input) {
        try {
            int opcion = -1;
            System.out.println("1(insertar proyecto) 2(modificar proyecto) 3(eliminar proyecto)");
            opcion = input.nextInt();
            input.nextLine();
            ResultSet resultado = null;
            switch (opcion) {
                case 1:ConsultaProyectos.insercion();
                    break;
                case 2:ConsultaProyectos.modificacion();
                    break;
                case 3:ConsultaProyectos.eliminacion(input);
                    break;
                default:
                    System.out.println("no se eligio una opcion valida");
                    break;
            }
        } catch (InputMismatchException e) {
            System.out.println("se introdujo algo que no era un numero");
        } catch (SQLException e) {
            System.out.println("Hubo un error en la operacion delete");
        }
    }

    public static void muestraProyectos() {
            muestraResultadosProyectos(ConsultaProyectos.selectAllProyectos());

    }
    public static void muestraResultadosProyectos(ResultSet resultado) {
        try {
            while (resultado.next()) {

                System.out.println(resultado.getInt(1) + " "
                        + resultado.getString(2) + " " +
                        resultado.getString(3) + " " +
                        resultado.getInt(4));
            }
            System.out.println();
        }catch (SQLException e){
            System.out.println("paso algo");
        }catch (NullPointerException e){
            System.out.println("resultado vacio");
        }
    }

    public  static void muestraNombres(ResultSet resultado){
       try {
           while (resultado.next()) {
               System.out.println(resultado.getString(1));
           }
       } catch (SQLException e) {
           System.out.println("error");
       }
    }

    public static void conSultaNombresEmpleados(Scanner input){
        muestraNombres(ConsultaNombres.getNamesStartedByN(input));
        }

    public static void borraEmpleados(Scanner input){
         ConsultaNombres.BorraEmpleados(input);
    }
}
