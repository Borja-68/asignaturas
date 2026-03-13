import com.mongodb.client.*;
import com.mongodb.client.model.DeleteManyModel;
import com.mongodb.client.result.DeleteResult;
import org.basex.examples.api.BaseXClient;
import org.bson.Document;

import java.io.IOException;
import java.util.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static MongoClient mongo;
    public static MongoDatabase database = null;
    public static Scanner sc;
    public static String mensaje = "XML\n" +
            "\t1. Consulta 1\n" +
            "\t2. Consulta 2\n" +
            "\t3. Consulta 3\n" +
            "\t4. Consulta 4\n" +
            "\t5. Eliminar libros\n" +
            "MongoDB\n" +
            "\t6. Consulta 1\n" +
            "\t7. Consulta 2\n" +
            "\t8. Consulta 3\n" +
            "\t9. Consulta 4\n" +
            "\t10. Eliminar peliculas\n" +
            "\t11. Anhadir nuevo documento\n" +
            "12. Salir";

    public static void main(String[] args) {
        mongo = MongoClients.create("mongodb://localhost:27017/");
        sc = new Scanner(System.in);
        int opcion = -1;
        do {
            System.out.println(mensaje);
            opcion = pedirInt("introduce el numero de la consulta");
            if (opcion >= 1 && opcion <= 5) baseX(opcion);
            if (opcion > 5 && opcion <= 11) {
                mongo(opcion);
            }
        } while (opcion != 12);
    }


    public static void baseX(int opcion) {
        try {
            BaseXClient clienteX = new BaseXClient("localhost", 1984, "admin", "abc123");
            clienteX.execute("open biblioteca");
            switch (opcion) {
                case 1:
                    System.out.println(consultaunitario(clienteX, "for $i in db:get('biblioteca')//libro\n" +
                            "where $i//paginas=max(db:get('biblioteca')//libro//paginas)\n" +
                            "return <il>{data($i//titulo)}-{data($i//paginas)}</il>\n"));

                    break;
                case 2:
                    List<String> lista = consultaMultiple(clienteX, "for $i in db:get('biblioteca')//libro\n" +
                            "order by $i//puntuacion\n" +
                            "return <il>{data($i//titulo)}-{data($i//puntuacion)}</il>");
                    for (String cadena : lista) {
                        System.out.println(cadena);
                    }
                    break;
                case 3:
                    System.out.println(consultaunitario(clienteX, "avg(\n" +
                            "  for $i in db:get('biblioteca')//libro[//usuario='usuario7' and //paginas>300]//puntuacion\n" +
                            "  return $i\n" +
                            ")"));
                    break;
                case 4:
                    List<String> listaAutor = consultaMultiple(clienteX, "for $i in  distinct-values(db:get(\"biblioteca\", \"biblioteca.xml\")/biblioteca/libro/autor)\n" +
                            "\n" +
                            "let $libros:= count(db:get(\"biblioteca\", \"biblioteca.xml\")/biblioteca/libro[autor=$i])\n" +
                            "\n" +
                            "return <il>{data($i)}-{data($libros)}</il>\n");
                    for (String cadena : listaAutor) {
                        System.out.println(cadena);
                    }
                    break;
                case 5:
                    System.out.println(consultaunitario(clienteX, "\n" +
                            "delete node db:get(\"biblioteca\")/biblioteca/libro[año_publicacion<1900]"));
                    break;

            }
        } catch (IOException e) {
            System.out.println("sucedio algo");
        }

    }

    public static String pedirString(String mensaje) {
        System.out.println(mensaje);
        return sc.next();
    }


    public static int pedirInt(String mensaje) {
        while (true) {
            try {
                System.out.println(mensaje);
                return sc.nextInt();
            } catch (Exception e) {
            }
        }
    }

    //""
    public static String consultaunitario(BaseXClient sesion, String consulta) {
        try {
            BaseXClient.Query query = sesion.query(consulta);
            if (query.more()) {
                return query.next();
            }
        } catch (IOException e) {
            System.out.println("sudcedio un error");
            return "";
        }
        return "";
    }


    public static List<String> consultaMultiple(BaseXClient sesion, String consulta) {
        try {
            BaseXClient.Query query = sesion.query(consulta);
            List<String> lista = new ArrayList<String>();
            while (query.more()) {
                lista.add(query.next());
            }
            return lista;
        } catch (IOException e) {
            System.out.println("sudcedio un error");
            return new ArrayList<>();
        }
    }

    public static void mongo(int opcion) {
        try {
            if (database == null) {
                database = mongo.getDatabase("peliculas");
            }
            switch (opcion) {
                case 6:
                    peliculasmayor45(database);
                      break;
                case 7:
                peliculasGenero(database);
                    break;
                case 8:
peliculasDuracion(database);
                    break;
                case 9:
peliculasCienciaFic(database);
                    break;
                case 10:
                    eliminar(database);
                     break;

                case 11:
                    break;

            }
        } catch (Exception e) {
            System.out.println("sucedio algo");
        }

    }


    public static void peliculasmayor45(MongoDatabase sesion) {
        try {
            MongoCollection<Document> coleccion = sesion.getCollection("peliculas");

            AggregateIterable<Document> itr = coleccion.aggregate(Arrays.asList(new Document("$match",
                    new Document("criticas.puntuacion",
                            new Document("$gt", Double.parseDouble("4.5"))))));

            Iterator<Document> it = itr.iterator();
            if (it.hasNext()) {
                for (Iterator<Document> iter = it; iter.hasNext(); ) {
                    Document doc = iter.next();
                    System.out.println(doc.toJson());
                }
            }
            else {
                System.out.println("estaba vacio");
            }
        } catch (Exception e) {
            System.out.println("sudcedio un error");
        }
    }

    public static void eliminar(MongoDatabase sesion) {
        try {
            MongoCollection<Document> coleccion = sesion.getCollection("peliculas");

           DeleteResult dr = coleccion.deleteMany(new Document("año", new Document("$lt", 1990L))
                    .append("duracion_minutos", new Document("$gt", 120L))
                    .append("criticas.puntuacion", new Document("$lt", Double.parseDouble("3.5"))));


            if (dr.getDeletedCount()>0) {
                System.out.println("borrados");
                System.out.println(dr.getDeletedCount());
            }
            else {
                System.out.println("estaba vacio");
            }
        } catch (Exception e) {
            System.out.println("sudcedio un error");
        }
    }
    public static void peliculasGenero(MongoDatabase sesion) {
        try {
            MongoCollection<Document> coleccion = sesion.getCollection("peliculas");

            FindIterable<Document> itr = coleccion.find(new Document("genero", "Drama")
                    .append("director", "Christopher Nolan"));
            Iterator<Document> it = itr.iterator();
            if (it.hasNext()) {
                for (Iterator<Document> iter = it; iter.hasNext(); ) {
                    Document doc = iter.next();
                    System.out.println(doc.toJson());
                }
            }
            else {
                System.out.println("estaba vacio");
            }
        } catch (Exception e) {
            System.out.println("sudcedio un error");
        }
    }

    public static void peliculasDuracion(MongoDatabase sesion) {
        try {
            MongoCollection<Document> coleccion = sesion.getCollection("peliculas");

            FindIterable<Document> itr = coleccion.find(new Document("duracion_minutos", new Document("$gt", Integer.parseInt("120")))
                            .append("clasificacion", "R")).projection(new Document("titulo", 1L)
                            .append("director", 1L));
            Iterator<Document> it = itr.iterator();
            if (it.hasNext()) {
                for (Iterator<Document> iter = it; iter.hasNext(); ) {
                    Document doc = iter.next();
                    System.out.println(doc.toJson());
                }
            }
            else {
                System.out.println("estaba vacio");
            }
        } catch (Exception e) {
            System.out.println("sudcedio un error");
        }
    }

    public static void peliculasCienciaFic(MongoDatabase sesion) {
        try {
            MongoCollection<Document> coleccion = sesion.getCollection("peliculas");

            AggregateIterable<Document> itr = coleccion.aggregate(Arrays.asList(new Document("$match",
                            new Document("genero", "Ciencia ficción")),
                    new Document("$addFields",
                            new Document("Numactores",
                                    new Document("$reduce",
                                            new Document("input", "$actores")
                                                    .append("initialValue", 0L)
                                                    .append("in",
                                                            new Document("$add", Arrays.asList("$$value", 1L)))))),
                    new Document("$project",
                            new Document("titulo", 1L)
                                    .append("año", 1L)
                                    .append("Numactores", 1L))));

            Iterator<Document> it = itr.iterator();
            if (it.hasNext()) {
                for (Iterator<Document> iter = it; iter.hasNext(); ) {
                    Document doc = iter.next();
                    System.out.println(doc.toJson());
                }
            }
            else {
                System.out.println("estaba vacio");
            }
        } catch (Exception e) {
            System.out.println("sudcedio un error");
        }
    }
}

