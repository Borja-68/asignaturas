import org.basex.examples.api.BaseXClient;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public final class Main {

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws IOException {

        ArrayList<XML401> info = new ArrayList<XML401>();

        String opcion = "";
        do{
            String nombre = introducirString("Introduzca el nombre del alumno");
            String apellidos = introducirString("Introduzca los apellidos");
            int edad = introducirInt("Introduzca la edad");
            String correo = introducirString("Introduzca el correo electrónico");

            info.add(new XML401(nombre, apellidos, edad, correo));

            opcion = introducirString("Desea seguir introduciendo datos (si/no)");
        }while(!opcion.equalsIgnoreCase("no"));

        // Crear la sesión
        try(BaseXClient session = new BaseXClient("localhost", 1984, "admin", "abc123")) {

            // Creamos los datos de entrada del primer documento
            InputStream bais = new ByteArrayInputStream(info.get(0).toString().getBytes());

            // Crear una base de datos llamada "ejercicio401" e insertar el primer documento
            session.create("ejercicio401", bais);

            for(int i = 1; i < info.size(); i++){
                bais = new ByteArrayInputStream(info.get(i).toString().getBytes());
                session.add("ejercicio401/alumno" + i + ".xml", bais);
            }

            System.out.println(session.info());

            String menu = "1. Número total de documentos en el sistema\n" +
                    "2. Media de edad de los alumnos.\n" +
                    "3. Alumno mayor y menor.\n" +
                    "4. Mostrar el nombre de los alumnos ordenado por edad de mayor a menor.\n" +
                    "5. Mostrar el nombre de un alumno de forma aleatoria.\n" +
                    "6. Salir";

            int opcionMenu;

            do{
                opcionMenu = introducirInt(menu);
                switch (opcionMenu){
                    case 1:
                        gestionConsulta(session, "count(for $i in db:get('ejercicio401') return $i)");
                        // Alternativa
                        //gestionConsulta(session, "count(for $i in /alumno return $i)");
                        break;
                    case 2:
                        gestionConsulta(session, "avg(for $i in db:get('ejercicio401')/alumno/edad return $i)");
                        // Alternativa
                        //gestionConsulta(session, "avg(for $i in /alumno/edad return $i)");
                        break;
                    case 3:
                        gestionConsulta(session, "let $minimo := min(/alumno/edad)\n" +
                                "let $maximo := max(/alumno/edad)\n" +
                                "return <alumno><min>{$minimo}</min>\n" +
                                "<max>{$maximo}</max></alumno>");
                        break;
                    case 4:
                        gestionConsulta(session, "for $alumno in /alumno\n" +
                                "order by $alumno/edad descending\n" +
                                "return $alumno/nombre/text()");
                        break;
                    case 5:
                        int totalDocumentos = obtenerResultadoConsulta(session, "count(for $i in /alumno return $i)");
                        int numRandom = (int) (Math.random()*totalDocumentos) + 1;
                        gestionConsulta(session, "let $documents := /alumno\n" +
                                "return $documents[" + numRandom + "]");
                        break;
                }
            }while(opcionMenu != 6);
        }
    }

    private static int obtenerResultadoConsulta(BaseXClient session, String input) {
        // Ejecución de la consulta
        try(BaseXClient.Query query = session.query(input)) {

            // Obtención del parámetro
            return Integer.parseInt(query.next());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void gestionConsulta(BaseXClient session, String input) {
        // Ejecución de la consulta
        try(BaseXClient.Query query = session.query(input)) {
            // Comprobación e iteración de los resultados
            while(query.more()) {
                // Obtención del parámetro
                System.out.println(query.next());
            }

            // Imprimir la información de la consulta
            System.out.println(query.info());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private static String introducirString(String mensaje) {
        while(true){
            try{
                System.out.print(mensaje + ": ");
                return sc.next();
            }catch (Exception e){
                System.out.println("Error al introducir los datos. Vuelva a introducirlos.");
            }
        }
    }

    private static int introducirInt(String mensaje) {
        while(true){
            try{
                System.out.print(mensaje + ": ");
                return sc.nextInt();
            }catch (Exception e){
                System.out.println("Error al introducir los datos. Vuelva a introducirlos.");
            }
        }
    }

}