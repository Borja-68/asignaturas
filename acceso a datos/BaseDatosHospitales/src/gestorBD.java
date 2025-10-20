import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class gestorBD {

    public static void menu() {
        Connection conexionMysql = ConexionesDB.getConectionMysql();
        Connection conexionPostgre = ConexionesDB.getConectionPostgre();
        boolean salida = false;
        int eleccion = -1;
        Scanner input = new Scanner(System.in);
        do {
            try {

                System.out.println("Elige lo que quieres hacer: " + "\n" +
                        "1(mostrar info base de datos) " + "\n" +
                        "2(mostrar informacion de proyectos) " + "\n" +
                        "3(realizar cambios en proyectos)" + "\n" +
                        "4(consulta nombres empleados)" + "\n" +
                        "5(borar empleado)" + "\n" +
                        "6(salir)");
                eleccion = input.nextInt();
                input.nextLine();
                switch (eleccion) {
                    case 1:

                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    case 5:
                        break;
                    case 6:
                        ConexionesDB.close();
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
}

