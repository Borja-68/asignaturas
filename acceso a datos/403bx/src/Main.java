import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Scanner;

import org.basex.examples.api.BaseXClient;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;

public class Main {

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        int opcion;
        String nombreBd;

        try(BaseXClient session = new BaseXClient("localhost", 1984, "admin", "abc123")) {
            do {
                opcion = pedirInt("1. Crear base de datos\n" +
                        "2. Seleccionar base de datos\n" +
                        "3. Eliminar base de datos\n" +
                        "4. Salir");

                switch (opcion) {
                    case 1:
                        nombreBd = pedirString("Introduzca el nombre de la base de datos: ");
                        crearBd(session, nombreBd);
                        break;
                    case 2:
                        nombreBd = pedirString("Introduzca el nombre de la base de datos: ");
                        seleccionarBD(session, nombreBd);
                        break;
                    case 3:
                        nombreBd = pedirString("Introduzca el nombre de la base de datos: ");
                        eliminarBD(session, nombreBd);
                        break;
                    case 4:
                        break;
                }

            } while (opcion != 4);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void seleccionarBD(BaseXClient sesion, String nombreBd) {
        try(BaseXClient.Query query = sesion.query("db:get('" + nombreBd + "')" )) {
            int opcion;
            do{
                opcion = pedirInt("1. Gestión de documentos\n" +
                        "2. Realizar consultas\n" +
                        "3. Atrás");
                switch (opcion){
                    case 1:
                        gestionDocumentos(sesion, nombreBd);
                        break;
                    case 2:
                        realizarConsultas(sesion, nombreBd);
                        break;
                }
            }while(opcion != 3);

        }catch (Exception e){}
    }

    private static void gestionDocumentos(BaseXClient sesion, String nombreBd) throws IOException {
        int opcion;
        // Abrimos la base de datos que vamos a utilizar
        sesion.execute("open " + nombreBd);
        do{
            opcion = pedirInt("1. Añadir nuevo documento\n" +
                    "2. Modificar documento\n" +
                    "3. Eliminar documentos.\n" +
                    "4. Atrás");
            switch (opcion){
                case 1:
                    anhadirDocumento(sesion, nombreBd);
                    break;
                case 2:
                    modificarDocumento(sesion, nombreBd);
                    break;
                case 3:
                    eliminarDocumento(sesion, nombreBd);
                    break;
            }
        }while(opcion != 3);
        sesion.close();
    }

    private static void eliminarDocumento(BaseXClient sesion, String nombreBd) throws IOException {
        ArrayList<String> resultados = ejecutarConsultaMultiple(sesion, "for $i in db:get('" + nombreBd + "') return $i");
        for(int i = 0; i < resultados.size(); i++)
            System.out.println(i + " --> " + resultados.get(i));

        int posicion = pedirInt("Indique la posición del documento a reemplazar: ");
        if(resultados.size() == 1 && posicion == 0){
            sesion.execute("drop database " + nombreBd);
        }else{
            ejecutarConsultaUnitaria(sesion, "delete node db:get('" + nombreBd + "')[" + (posicion + 1) + "]");
        }
    }

    private static void modificarDocumento(BaseXClient sesion, String nombreBd) {
        ArrayList<String> resultados = ejecutarConsultaMultiple(sesion, "for $i in db:get('" + nombreBd + "') return $i");
        for(int i = 0; i < resultados.size(); i++)
            System.out.println(i + " --> " + resultados.get(i));

        int posicion = pedirInt("Indique la posición del documento a reemplazar: ");
        String resultado = "";
        do {
            String campoModificar = pedirString("Introduzca el campo que quiere cambiar: ");
            String nuevoValorCampo = pedirString("Nuevo valor del campo: ");

            resultado = ejecutarConsultaUnitaria(sesion, "replace value of node db:get('" + nombreBd + "')[" + (posicion + 1) + "]//" + campoModificar + " " +
                    "with '" + nuevoValorCampo + "'");
        }while(resultado != null);
    }

    private static void anhadirDocumento(BaseXClient sesion, String nombreBd) {
        int num = Integer.parseInt(ejecutarConsultaUnitaria(sesion, "count(for $i in db:get('" + nombreBd + "') return $i)"));

        do{
            String documento = pedirXML("Introduzca el documento XML");
            try{
                Document doc = parsear(documento);
                doc.getDocumentElement().normalize();
                sesion.add(nombreBd + "/doc" + num + ".xml", new ByteArrayInputStream(documento.getBytes()));
                return;
            }catch (Exception e) {
                System.out.println("El documento no es válido. Vuelve a introducirlo");
            }
        }while(true);
    }

    private static String pedirXML(String introduzcaElDocumentoXml) {
        StringBuilder fileXML = new StringBuilder();
        System.out.println(introduzcaElDocumentoXml);
        String linea;
        while(!(linea = sc.next()).equalsIgnoreCase("fin"))
            fileXML.append(linea);

        return fileXML.toString();
    }

    private static void realizarConsultas(BaseXClient sesion, String nombreBd) {
        int opcion;
        do{
            opcion = pedirInt("1. Listar número total de documentos\n" +
                    "2. Listar por campos\n" +
                    "3. Atrás");
            switch (opcion){
                case 1:
                    String numero = ejecutarConsultaUnitaria(sesion, "count(for $i in db:get('" + nombreBd + "') return $i)");
                    System.out.println(numero);
                    break;
                case 2:
                    String estructura = ejecutarConsultaUnitaria(sesion, "let $documentos := for $i in db:get('" + nombreBd + "')/ return $documentos[1]");
                    Document doc = parsear(estructura);
                    break;
            }
        }while(opcion != 3);
    }

    private static Document parsear(String xml){
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        Document doc;
        try {
            builder = factory.newDocumentBuilder();
            doc = builder.parse(new InputSource(new StringReader(xml)));
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        }
        return doc;
    }

    private static ArrayList<String> ejecutarConsultaMultiple(BaseXClient sesion, String consulta) {
        ArrayList<String> lista = new ArrayList<>();
        // Comprobamos si existe o si no la BD
        try(BaseXClient.Query query = sesion.query(consulta )) {

            // Comprobación e iteración de los resultados
            while(query.more()) {
                lista.add(query.next());
            }
        }catch (Exception e){}
        return lista;
    }

    private static String ejecutarConsultaUnitaria(BaseXClient sesion, String consulta) {
        // Comprobamos si existe o si no la BD
        try(BaseXClient.Query query = sesion.query(consulta )) {

            // Comprobación e iteración de los resultados
            if(query.more()) {
                return query.next();
            }
        }catch (Exception e){}
        return null;
    }

    private static void eliminarBD(BaseXClient sesion, String nombreBd) {
        // Comprobamos si existe o si no la BD
        try(BaseXClient.Query query = sesion.query("db:get('" + nombreBd + "')" )) {

            // Comprobación e iteración de los resultados
            sesion.execute("drop db " + nombreBd);

        }catch (Exception e){}
    }

    public static void crearBd(BaseXClient sesion, String nombreBD){
        // Comprobamos si existe o si no la BD
        try(BaseXClient.Query query = sesion.query("for $i in db:get('" + nombreBD + "') return $i" )) {

            // Comprobación e iteración de los resultados
            if(query.more()) {
                System.out.println("La base de datos ya existe");
            }
        }catch (Exception e){
            try {
                sesion.execute("create db " + nombreBD);
                System.out.println("Base de datos creada");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

        }
    }

    private static String pedirString(String mensaje) {
        while(true){
            try{
                System.out.println(mensaje);
                return sc.next();
            }catch (Exception e){}
        }
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
