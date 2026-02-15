import org.basex.examples.api.BaseXClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    static Scanner sc;

    public static void main(String[] args) {
        sc = new Scanner(System.in);
        int opcion=-1;
        do {
            try {
                opcion = sc.nextInt();
                sc.nextLine();
                if(opcion>=1 && opcion<=7){
                    consultaBaseX("","productos",opcion);
                }
            } catch (InputMismatchException e) {
                System.out.println("no se introdujo un numero");
                sc.nextLine();
            }
        } while (opcion!=18);

    }
    private static void consultaBaseX(String consulta,String nombreDB,Integer opcion){
        try(BaseXClient sesion = new BaseXClient("localhost", 1984, "admin", "abc123")) {
            switch (opcion){
                case 1: break;
                case 2: break;
                case 3: break;
                case 4: break;
                case 5: break;
                case 6: break;
                case 7: break;

            }
            sesion.execute("open " + nombreDB);
            ejecutarConsultaBaseX(sesion, consulta);
        }catch (Exception ignored){}
    }
private static void borraProductoPorIdBaseX(BaseXClient sesion) {
    ArrayList<String> resultados = devuelveTodosProductos(sesion);
    for(int i = 0; i < resultados.size(); i++)
        System.out.println(resultados.get(i));

    int posicion = pedirInt("Indique id del producto a quitar: ");
    try {
        sesion.execute("delete node db:get('pedidos')[" + (posicion + 1) + "]");
    } catch (IOException e) {

    }
}
    private static void ejecutarConsultaBaseX(BaseXClient sesion, String consulta) {
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

    private static ArrayList<String> devuelveTodosProductos(BaseXClient sesion) {
        ArrayList<String> lista = new ArrayList<>();
        // Comprobamos si existe o si no la BD
        try(BaseXClient.Query query = sesion.query("for $i in db:get('productos') return $i" )) {

            // Comprobación e iteración de los resultados
            while(query.more()) {
                lista.add(query.next());
            }
        }catch (Exception e){}
        return lista;
    }

    private static int pedirInt(String mensaje) {
        while(true){
            try{
                System.out.println(mensaje);
                return sc.nextInt();
            }catch (Exception e){}
        }
    }
}