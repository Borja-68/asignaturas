import org.basex.examples.api.BaseXClient;

import java.util.Scanner;

public class Main {
    static Scanner sc;

    public static void main(String[] args) {
        sc = new Scanner(System.in);
        String consulta;
        String nombreBD;

        try(BaseXClient sesion = new BaseXClient("localhost", 1984, "admin", "abc123")) {
            nombreBD = pedirString();
            sesion.execute("open " + nombreBD);
            do{
                consulta = pedirConsulta();
                ejecutarConsulta(sesion, consulta);
            }while(!consulta.equalsIgnoreCase("salir"));
        }catch (Exception ignored){}
    }

    private static void ejecutarConsulta(BaseXClient sesion, String consulta) {
        try (BaseXClient.Query query = sesion.query(consulta)) {

            // Comprobación e iteración de los resultados
            while (query.more()) {
                System.out.println(query.next());
            }
        } catch (Exception e) {
            System.out.println("Consulta incorrecta:\n" + e.getStackTrace());
        }
    }

    private static String pedirConsulta() {
        System.out.println("Introduzca la consulta. Introduzca la palabra FIN para indicar que se ha finalizado");
        String linea;
        StringBuilder sb = new StringBuilder();
        while(!(linea = sc.next()).equalsIgnoreCase("fin")) {
            sb.append(linea);
            sb.append(" ");
        }

        return sb.toString();
    }

    private static String pedirString() {
        while(true){
            try{
                System.out.println("Introduzca el nombre de la BD");
                return sc.next();
            }catch (Exception ignored){}
        }
    }
}