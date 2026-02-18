import com.mongodb.client.*;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Sorts.descending;
import static com.mongodb.client.model.Updates.inc;
import static com.mongodb.client.model.Updates.set;

public class Main {

    static Scanner sc;
    static MongoClient mongoClient;
    private static MongoDatabase database = null;
    private static MongoCollection<Document> coleccion;

    public static void main(String[] args) {
        sc = new Scanner(System.in).useDelimiter("\n");

        mongoClient = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase database;

        int opcion;
        do{
            opcion = pedirInt("1. Crear base de datos\n" +
                    "2. Crear colección 'equipos' y añdir datos\n" +
                    "3. Crear colección 'pilotos' y añdir datos\n" +
                    "4. Crear colección 'carreras' y añdir datos\n" +
                    "5. Mostrar equipos de F1\n" +
                    "6. Mostrar pilotos de un equipo\n" +
                    "7. Mostrar información de una carrera\n" +
                    "8. Mostrar pilotos ordenados por puntos de mayor a menor\n" +
                    "9. Actualizar puntos de un piloto después de una victoria\n" +
                    "10. Eliminar un equipo y sus pilotos\n" +
                    "11. Mostrar pilotos que han ganado una carrera\n" +
                    "12. Sumar los puntos de todos los pilotos\n" +
                    "13. Actualizar la fecha de realización de una carrera\n" +
                    "14. Mostrar los equipos fundados antes de un determinado año\n" +
                    "15. Incrementar los puntos de todos los pilotos en una cantidad\n" +
                    "16. Mostrar la cantidad total de puntos por nacionalidad\n" +
                    "17. Salir");
            switch (opcion){
                case 1:
                    crearBaseDatos();
                    break;
                case 2:
                    seleccionarColeccion("equipos");
                    insertarDatosEquipo("equipos");
                    break;
                case 3:
                    seleccionarColeccion("pilotos");
                    insertarDatosPilotos("pilotos");
                    break;
                case 4:
                    seleccionarColeccion("carreras");
                    insertarDatosCarreras("carreras");
                    break;
                case 5:
                    mostrarEquiposF1();
                    break;
                case 6:
                    mostrarPilotosEquipo();
                    break;
                case 7:
                    mostrarInformacionCarrera();
                    break;
                case 8:
                    mostrarPilotosOrdenadosPuntos();
                    break;
                case 9:
                    actualizarPuntosDespuesVictoria();
                    break;
                case 10:
                    eliminarEquipoyPilotos();
                    break;
                case 11:
                    mostrarPilotosGanaronCarrera();
                    break;
                case 12:
                    sumarPuntosPilotos();
                    break;
                case 13:
                    actualizarFechaGranPremio();
                    break;
                case 14:
                    mostrarEquiposFundadosAntesAnho();
                    break;
                case 15:
                    incrementarPuntosTodosPilotosCantidad();
                    break;
                case 16:
                    mostrarCantidadTotalPuntosPorNacionalidad();
                    break;
            }
        }while(opcion != 17);
    }

    private static void mostrarCantidadTotalPuntosPorNacionalidad(){
        seleccionarColeccion("pilotos");

        AggregateIterable<Document> iterDoc = coleccion.aggregate(
                Arrays.asList(
                        Aggregates.group(
                                "$nacionalidad",
                                Accumulators.sum("totalPuntos", "$puntos"))
                )
        );

        for (Document document : iterDoc) {
            System.out.println(document);
        }
    }

    private static void incrementarPuntosTodosPilotosCantidad(){
        seleccionarColeccion("pilotos");

        int cantidadAumentar = pedirInt("Introduzca la cantidad a aumentar");

        UpdateResult ur = coleccion.updateMany(new Document(), inc("puntos", cantidadAumentar));

        if(ur.getModifiedCount() > 0){
            System.out.println("Actualizacion correcta. " + ur.getModifiedCount() + " documentos actualizados.");
        }else{
            System.out.println("No se ha actualizado ningún documento");
        }
    }

    private static void mostrarEquiposFundadosAntesAnho(){
        seleccionarColeccion("equipos");

        int anho = pedirInt("Introduzca el año");
        FindIterable<Document> iterDoc = coleccion.find(lt("fundacion", anho));

        for (Document document : iterDoc) {
            System.out.println(document);
        }
    }

    private static void actualizarFechaGranPremio(){
        seleccionarColeccion("carreras");

        UpdateResult ur = coleccion.updateOne(new Document("nombre", "Gran Premio de Italia"), set("fecha", new Date(2023, 6, 15)));

        if(ur.getModifiedCount() > 0){
            System.out.println("Actualizacion correcta. " + ur.getModifiedCount() + " documentos actualizados.");
        }else{
            System.out.println("No se ha actualizado ningún documento");
        }
    }

    private static void sumarPuntosPilotos() {
        seleccionarColeccion("pilotos");

        AggregateIterable<Document> iterDoc = coleccion.aggregate(
                Arrays.asList(
                        Aggregates.group(
                                "puntos",
                                Accumulators.sum("totalPuntos", "$puntos"))
                )
        );

        for (Document document : iterDoc) {
            System.out.println(document);
        }
    }

    private static void mostrarPilotosGanaronCarrera() {
        seleccionarColeccion("pilotos");

        FindIterable<Document> iterDoc = coleccion.find(gt("puntos", 0));

        for (Document document : iterDoc) {
            System.out.println(document);
        }
    }

    private static void eliminarEquipoyPilotos() {
        seleccionarColeccion("equipos");

        String equipoEliminado = pedirString("Introduzca el nombre del equipo a eliminar");
        DeleteResult dr1 = coleccion.deleteMany(new Document("nombre", equipoEliminado));
        if (dr1.getDeletedCount() > 0){
            seleccionarColeccion("pilotos");
            DeleteResult dr2 = coleccion.deleteMany(new Document("equipo", equipoEliminado));
            if (dr2.getDeletedCount() > 0){
                System.out.println("Equipo y sus pilotos eliminados");
            }else{
                System.out.println("Se ha eliminado el equipo no había pilotos que eliminar");
            }
        }else{
            System.out.println("No hay ningún equipo con ese nombre");
        }

    }

    private static void actualizarPuntosDespuesVictoria() {
        seleccionarColeccion("pilotos");

        String nombrePiloto = pedirString("Introduzca el nombre del piloto");
        UpdateResult ur = coleccion.updateOne(new Document("nombre", nombrePiloto), inc("puntos", 25));

        if(ur.getModifiedCount() > 0){
            System.out.println("Actualizacion correcta. " + ur.getModifiedCount() + " documentos actualizados.");
        }else{
            System.out.println("No se ha actualizado ningún documento");
        }
    }

    private static void mostrarPilotosOrdenadosPuntos() {
        seleccionarColeccion("pilotos");

        FindIterable<Document> iterDoc = coleccion.find().sort(descending("puntos"));

        for (Document document : iterDoc) {
            System.out.println(document);
        }
    }

    private static void mostrarInformacionCarrera() {
        seleccionarColeccion("carreras");

        String nombreGranPremio = pedirString("Introduzca el nombre del gran premio");
        FindIterable<Document> iterDoc = coleccion.find(new Document("nombre", nombreGranPremio));

        for (Document document : iterDoc) {
            System.out.println(document);
        }
    }

    private static void mostrarPilotosEquipo() {
        seleccionarColeccion("pilotos");

        String nombreEquipo = pedirString("Introduzca el nombre del equipo");
        FindIterable<Document> iterDoc = coleccion.find(new Document("equipo", nombreEquipo));

        for (Document document : iterDoc) {
            System.out.println(document);
        }
    }

    private static void mostrarEquiposF1() {
        seleccionarColeccion("equipos");

        FindIterable<Document> iterDoc = coleccion.find();

        for (Document document : iterDoc) {
            System.out.println(document);
        }
    }

    private static void insertarDatosCarreras(String nombreColeccion) {
        seleccionarColeccion(nombreColeccion);

        ArrayList<Document> listaDocumentos = new ArrayList<>();
        listaDocumentos.add(new Document("nombre", "Gran Premio de España")
                .append("fecha", new Date(2023, 5, 1))
                .append("ganador", "Lewis Hamilton"));

        listaDocumentos.add(new Document("nombre", "Gran Premio de Italia")
                .append("fecha", new Date(2023, 6, 1))
                .append("ganador", "Sebastian Vettel"));

        try {
            coleccion.insertMany(listaDocumentos);
        }catch (Exception e){
            System.out.println("Hubo un problema en la introducción de los valores");
        }
    }

    private static void insertarDatosPilotos(String nombreColeccion) {
        seleccionarColeccion(nombreColeccion);

        ArrayList<Document> listaDocumentos = new ArrayList<>();
        listaDocumentos.add(new Document("nombre", "Lewis Hamilton")
                .append("nacionalidad", "Británico")
                .append("equipo", "Mercedes")
                .append("puntos", 100));

        listaDocumentos.add(new Document("nombre", "Sebastian Vettel")
                .append("nacionalidad", "Aleman")
                .append("equipo", "Ferrari")
                .append("puntos", 80));

        try {
            coleccion.insertMany(listaDocumentos);
        }catch (Exception e){
            System.out.println("Hubo un problema en la introducción de los valores");
        }
    }

    private static void insertarDatosEquipo(String nombreColeccion) {
        seleccionarColeccion(nombreColeccion);

        ArrayList<Document> listaDocumentos = new ArrayList<>();
        listaDocumentos.add(new Document("nombre", "Mercedes")
                .append("pais", "Alemania")
                .append("fundacion", 2010));

        listaDocumentos.add(new Document("nombre", "Ferrari")
                .append("pais", "Italia")
                .append("fundacion", 1929));

        try {
            coleccion.insertMany(listaDocumentos);
        }catch (Exception e){
            System.out.println("Hubo un problema en la introducción de los valores");
        }
    }

    private static void seleccionarColeccion(String nombreColeccion){
        if(database == null)
            database = mongoClient.getDatabase("F1DB");
        try{
            coleccion = database.getCollection(nombreColeccion);
        }catch (Exception e){
            database.createCollection(nombreColeccion);
            coleccion = database.getCollection(nombreColeccion);
        }
    }

    private static void crearBaseDatos() {
        database = mongoClient.getDatabase("F1DB");
    }

    private static int pedirInt(String mensaje) {
        System.out.println(mensaje);
        while(true) {
            try {
                return sc.nextInt();
            } catch (Exception ignored) {
            }
        }
    }

    private static String pedirString(String mensaje){
        System.out.println(mensaje);
        return sc.next();
    }
}