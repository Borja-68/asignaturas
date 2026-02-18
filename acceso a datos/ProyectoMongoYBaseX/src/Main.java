import com.mongodb.client.*;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.basex.examples.api.BaseXClient;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Projections.include;
import static com.mongodb.client.model.Updates.set;

public class Main {
    static Scanner sc;
    static MongoClient mongoClient;
    static MongoDatabase database=null;
    static ObjectId idUsuario=null;
    static String consultas="Consultas BaseX\n" +
            "1 Modificar el valor de un elemento de un XML según un ID.\n" +
            "2 Eliminar un producto según su ID.\n" +
            "3 Consulta 1: Obtener todos los productos por orden alfabético del nombre (se mostrarán los siguientes campos: id, nombre, precio, disponibilidad y categoria).\n" +
            "4 Consulta 2: Listar productos con una disponibilidad mayor a X unidades (se mostrarán los siguientes campos: id, nombre, precio, disponibilidad y categoria).\n" +
            "5 Consulta 3: Mostrar la categoría, el nombre y el precio del producto más caro para cada categoría. En el caso de haber varios se devolverá el de la primera posición.\n" +
            "6 Consulta 4: Mostrar el nombre de los productos y su fabricante para aquellos productos cuya descripción incluya una subcadena. Se deberá mostrar la información ordenada según el nombre del fabricante de forma inversa al alfabeto.\n" +
            "7 Consulta 5: Mostrar la cantidad total de productos en cada categoría (teniendo en cuenta el elemento disponibilidad) y calcular el porcentaje que representa respecto al total de productos.\n" +
            "Consultas Mongo\n"+
            "8 Crear un nuevo cliente (no podrá haber email repetidos).\n" +
            "9 Identificar cliente según el email. Dado el email se obtendrá el ID del cliente de forma que las siguientes consultas se harán sobre ese cliente. Para cambiar de cliente se tendrá que volver a seleccionar esta opción.\n" +
            "10 Borrar un cliente.\n" +
            "11 Modificar el valor de un campo de la información del cliente.\n" +
            "12 Añadir producto al carrito del cliente. Se pedirá: id del producto y cantidad, así como si se desea seguir introduciendo más productos.\n" +
            "13 Mostrar el carrito del cliente. Se mostrarán los datos del carrito y el precio total.\n" +
            "14 Mostrar pedidos del cliente.\n" +
            "15 Pagar el carrito de un cliente: se mostrará el carrito junto con una orden de confirmación. Si la orden es positiva se pasarán todos los productos a formar parte de un nuevo pedido.\n" +
            "16 Consulta 1: Teniendo en cuenta todos los clientes, calcular el total de la compra para cada carrito y listar los resultados ordenados por el total de forma ascendente. (No es necesario tener en cuenta la multiplicación de precio_unitario * cantidad con sumar los precio_unitario es suficiente).\n" +
            "17 Consulta 2: Teniendo en cuenta todos los clientes, obtener el total gastado por cada cliente en todos sus pedidos.\n"+
            "18 Salir";

    public static void main(String[] args)  {
        sc = new Scanner(System.in);
        mongoClient = MongoClients.create("mongodb://localhost:27017");
        int opcion=-1;
        do {
            try {
                System.out.println(consultas);
                opcion = pedirInt("introduzca opcion: ");
                if(opcion>=1 && opcion<=7){
                    consultaBaseX("","productos",opcion);
                }
                else if(opcion>=8 && opcion<=17){
                    consultasMongoDB(opcion);
                }
            } catch (InputMismatchException e) {
                System.out.println("no se introdujo un numero");

            }
        } while (opcion!=18);

    }
    //acciones de BaseX
    private static void consultaBaseX(String consulta,String nombreDB,Integer opcion){
        try(BaseXClient sesion = new BaseXClient("localhost", 1984, "admin", "abc123")) {
            sesion.execute("open productos");
            switch (opcion){
                case 1: cambiaProductoPorIdBaseX(sesion);
                        break;

                case 2: borraProductoPorIdBaseX(sesion);
                        break;

                case 3: ejecutarConsultaBaseX(sesion,
                        "for $i in db:get('productos')/productos/producto \n" +
                        "order by $i/nombre\n" +
                        " return <il>{$i/id}-{$i/nombre}-{$i/precio}-{$i/disponibilidad }-{$i/categoria }</il>");
                        break;

                case 4: int cantDisponibilidad=pedirInt("cantidad de disponibilidad minima");
                        ejecutarConsultaBaseX(sesion,
                        "for $i in db:get('productos')/productos/producto \n" +
                        "where $i/disponibilidad>"+cantDisponibilidad+"\n" +
                        " return <il>{$i/id}-{$i/nombre}-{$i/precio}-{$i/disponibilidad }-{$i/categoria }</il>");
                        break;

                case 5: ejecutarConsultaBaseX(sesion,
                        "for $i in distinct-values(db:get('productos')/productos/producto/categoria)\n" +
                        "let $maxPrecio := max(db:get('productos')/productos/producto[categoria = $i]/precio)\n" +
                        "let $productoMasCaro := (db:get('productos')/productos/producto[categoria = $i and precio = $maxPrecio])\n" +
                        "return <producto>{$i}-{$productoMasCaro/nombre}-{$productoMasCaro/precio}</producto>");
                break;

                case 6: String subCadenaDescripcion=pedirString("introduzca subcadena a revisar");
                    ejecutarConsultaBaseX(sesion,
                            "for $i in db:get('productos')/productos/producto\n" +
                            "where contains(lower-case($i/descripcion), '"+subCadenaDescripcion+"')\n" +
                            "order by $i/fabricante descending\n" +
                            "return <producto>{$i/nombre}-{$i/fabricante}</producto>");
                    break;

                case 7: ejecutarConsultaBaseX(sesion,
                        "for $i in distinct-values(db:get('productos')/productos/producto/categoria)\n" +
                        "let $total :=sum(db:get('productos')/productos/producto/disponibilidad)\n" +
                        "let $totalCategoria := sum(db:get('productos')/productos/producto[categoria=$i]/disponibilidad)\n" +
                        "return <producto>{$i}-{$totalCategoria}-{concat(format-number(($totalCategoria div $total) * 100,'0.00'),'%')}</producto>");
                break;

            }

        }catch (Exception ignored){
            System.out.println("paso algo");
        }
    }

    private static void cambiaProductoPorIdBaseX(BaseXClient sesion) {
        ArrayList<String> resultados = devuelveTodosProductos(sesion);
        for(int i = 0; i < resultados.size(); i++)
            System.out.println(resultados.get(i));

        int id = pedirInt("Indique id del producto a quitar: ");
        String dato=pedirString("indique que caracteristica quiere cambiar");
        String nuevoValor=pedirString("introduzca el nuevo valor");

        System.out.println(id+" "+dato+" "+nuevoValor);
        ejecutarConsultaUnitaria(sesion,"replace value of node db:get(\'productos\')/productos/producto[id="+id+"]//"+dato+ " with " + nuevoValor);
        ejecutarConsultaBaseX(sesion, "for $i in db:get(\'productos\')/productos/producto[id="+id+"] return <producto>{$i/id}-{$i//"+dato+"}</producto>");
    }

private static void borraProductoPorIdBaseX(BaseXClient sesion) {
    ArrayList<String> resultados = devuelveTodosProductos(sesion);
    for(int i = 0; i < resultados.size(); i++)
        System.out.println(resultados.get(i));

    int id = pedirInt("Indique id del producto a quitar: ");
    ejecutarConsultaUnitaria(sesion,"delete node db:get(\"productos\")/productos/producto[id="+id+"]");
    System.out.println("producto quitado si existía");
    }

    private static ArrayList<String> obtenerProductoPorId(BaseXClient sesion,int producto) {
        ArrayList<String> resultado=new ArrayList<>();
        try(BaseXClient.Query query = sesion.query("for $i in db:get(\"productos\")/productos/producto where $i/id="+producto+" return data(concat($i/nombre,\" - \",$i/precio))")) {
            while (query.more()) {
                resultado.add(query.next());
            }
        }catch (Exception e){}
        return resultado;
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

    private static ArrayList<String> devuelveTodosProductos(BaseXClient sesion) {
        ArrayList<String> lista = new ArrayList<>();
        // Comprobamos si existe o si no la BD
        try(BaseXClient.Query query = sesion.query("for $i in db:get(\"productos\")/productos/producto return $i" )) {

            // Comprobación e iteración de los resultados
            while(query.more()) {
                lista.add(query.next());
            }
        }catch (Exception e){}
        return lista;
    }

    private static String ejecutarConsultaUnitaria(BaseXClient sesion, String consulta) {
        // Comprobamos si existe o si no la BD
        try(BaseXClient.Query query = sesion.query(consulta)) {

            // Comprobación e iteración de los resultados
            if(query.more()) {
                return query.next();
            }
        }catch (Exception e){}
        return null;
    }

//consulta mongoDB
    public static void consultasMongoDB(int opcion){
        if(database==null)database=mongoClient.getDatabase("tienda");
        switch (opcion){
            case 8: creaCliente(database)
            ;break;

            case 9:obtenClientePorEmail(database);
            break;

            case 10:eliminarCliente(database);
            break;

            case 11:cambiaValorCliente(database);
            break;

            case 12:anadeProductosCarrito(database);
            break;

            case 13:mostrarCarrito(database);
            break;
            case 14:pedidosCliente(database);
            break;

            case 15:carroAPedido(database);
            break;

            case 16:listadoPreciosCarrosOrdenados(database);
            break;

            case 17:devuelveCostePedidos(database);
            break;
        }
    }
private static void devuelveCostePedidos(MongoDatabase database){
    MongoCollection<Document> coleccion = database.getCollection("pedidos");
    System.out.println("Colección carritos seleccionada correctamente");
    AggregateIterable<Document>resultado=coleccion.aggregate(Arrays.asList(new Document("$group",
                                    new Document("_id", "$cliente_id")
                                            .append("total_gastado",
                                                new Document("$sum", "$total"))
                                            .append("pedidos",
                                                new Document("$push", "$_id"))),
                                            new Document("$project",
                                            new Document("cliente_id", 1L)
                                            .append("total_gastado", 1L))));

    Iterator<Document> it = resultado.iterator();
    while(it.hasNext()){
        Document doc=it.next();
        System.out.println(doc);
    }
}
    private static void creaCliente(MongoDatabase database){
        MongoCollection<Document> coleccion = database.getCollection("clientes");
        System.out.println("Colección clientes seleccionada correctamente");

        String nombre=pedirString("dame un nombre");
        String email=pedirString("dame un correo");
        String direccion=pedirString("dame una direccion");

        Document document = new Document("nombre", nombre)
                .append("email", email)
                .append("direccion", direccion);
        try {
            coleccion.insertOne(document);
        } catch (Exception e) {
            System.out.println("el correo del usuario ya existia");
        }
    }

    private static void anadeProductosCarrito(MongoDatabase database){
        if(idUsuario==null) System.out.println("no hay cliente seleccionado");
        else {
            MongoCollection<Document> coleccion = database.getCollection("carritos");
            System.out.println("Colección carritos seleccionada correctamente");
            List<Document> documentos = new ArrayList<>();
            String opcion = "";
            do {
                try(BaseXClient sesion = new BaseXClient("localhost", 1984, "admin", "abc123")) {
                    sesion.execute("open productos");

                    int idProducto = pedirInt("inserta la id del producto");
                    ArrayList<String> datos = obtenerProductoPorId(sesion, idProducto);
                    if (!datos.isEmpty()) {

                        String[] cadenaDatos = datos.getFirst().split(" - ");
                        int cantidad = pedirInt("cantidad del producto");
                        System.out.println(cadenaDatos[0]+" "+cadenaDatos[1]);
                        Document producto = new Document("producto_id", idProducto)
                                .append("nombre", cadenaDatos[0])
                                .append("cantidad", cantidad)
                                .append("precio_unitario", Double.parseDouble(cadenaDatos[1]));
                        documentos.add(producto);
                    }
                } catch (Exception e) {
                    System.out.println("sucedio algo");
                }
                opcion = pedirString("Deseas introducir otro producto (Si/No)");
            } while (!opcion.equalsIgnoreCase("no"));

            Document document = new Document("cliente_id", idUsuario)
                    .append("productos", documentos)
                    .append("fecha_actualizacion",LocalDate.now());
            try {
                coleccion.insertOne(document);
                System.out.println("consulta hecha");
            } catch (Exception e) {
                System.out.println("el correo del usuario ya existia");
            }
        }
    }

    private static void obtenClientePorEmail(MongoDatabase database) {
        MongoCollection<Document> coleccion = database.getCollection("clientes");
        System.out.println("Colección clientes seleccionada correctamente");
        String email=pedirString("inserta email del cliente");

        FindIterable<Document> iterDoc = coleccion.find(eq("email", email)).projection(include("_id"));

        for (Document document : iterDoc) {
            idUsuario=document.getObjectId("_id");
        }
        System.out.println(idUsuario);
    }


private static void listadoPreciosCarrosOrdenados(MongoDatabase database){
    MongoCollection<Document> coleccion = database.getCollection("carritos");
    System.out.println("Colección carritos seleccionada correctamente");
    AggregateIterable<Document>resultado=coleccion.aggregate(Arrays.asList(new Document("$addFields",
                            new Document("total_carrito",
                            new Document("$reduce",
                            new Document("input", "$productos")
                                    .append("initialValue", 0L)
                                    .append("in",
                                        new Document("$add", Arrays.asList("$$value",
                                        new Document("$toDouble", "$$this.precio_unitario"))))))),
                                            new Document("$sort",
                                            new Document("total_carrito", 1L)),
                                                new Document("$project",
                                                new Document("cliente_id", 1L)
                                                .append("total_carrito", 1L))));


    Iterator<Document> it = resultado.iterator();
    while(it.hasNext()){
       Document doc=it.next();
        System.out.println(doc);
    }
}


    private static void eliminarCliente(MongoDatabase database) {
        if (idUsuario==null) System.out.println("aun no se habia seleccionado un usuario");
        else {
            MongoCollection<Document> coleccion = database.getCollection("clientes");
            System.out.println("Colección clientes seleccionada correctamente");
            DeleteResult dr = coleccion.deleteOne(eq("_id", idUsuario));
            if(dr.getDeletedCount() > 0){
                System.out.println("se borro");
                idUsuario=null;
            }
            else System.out.println("no se borro");
        }
    }

    private static void carroAPedido(MongoDatabase database){
        MongoCollection<Document> coleccion = database.getCollection("carritos");
        System.out.println("Colección carritos seleccionada correctamente");
        AggregateIterable<Document>resultado=coleccion.aggregate(Arrays.asList(new Document("$match",
                                new Document("cliente_id",
                                new Document("$eq",idUsuario))),
                                new Document("$addFields",
                                new Document("total_carrito",
                                new Document("$reduce",
                                new Document("input", "$productos")
                                                .append("initialValue", 0L)
                                                .append("in",
                                                        new Document("$add", Arrays.asList("$$value",
                                                        new Document("$multiply", Arrays.asList("$$this.cantidad", "$$this.precio_unitario"))))))))));


        Iterator<Document> it = resultado.iterator();
        if (!it.hasNext()){
            System.out.println("vacio");
        }
        else {
            String opcion = pedirString("Deseas pasarlo a pedido? (Si/No)");
            if (!opcion.equalsIgnoreCase("no")) {
                Document carrito= it.next();
                MongoCollection<Document> coleccionPedidos = database.getCollection("pedidos");
                Document document = new Document("cliente_id", idUsuario)
                        .append("productos", carrito.get("productos"))
                        .append("total", carrito.get("total_carrito"))
                        .append("total_carrito", LocalDate.now());
                try {
                    coleccionPedidos.insertOne(document);
                    DeleteResult dr = coleccion.deleteOne(eq("cliente_id", idUsuario));
                    if(dr.getDeletedCount() > 0){
                        System.out.println("se borro");
                    }
                } catch (Exception e) {
                    System.out.println("sucedio algo");
                }

            }
        }
    }
    private static void mostrarCarrito(MongoDatabase database) {
        if (idUsuario==null) System.out.println("aun no se habia seleccionado un usuario");
        else {
            MongoCollection<Document> coleccion = database.getCollection("carritos");
            System.out.println("Colección carritos seleccionada correctamente");
            AggregateIterable<Document> resultado = coleccion.aggregate(Arrays.asList(new Document("$match",
                                    new Document("cliente_id",
                                    new Document("$eq",idUsuario))),
                                    new Document("$addFields",
                                    new Document("total_carrito",
                                    new Document("$reduce",
                                    new Document("input", "$productos")
                                                    .append("initialValue", 0L)
                                                    .append("in",
                                                                new Document("$add", Arrays.asList("$$value",
                                                                new Document("$multiply", Arrays.asList("$$this.cantidad", "$$this.precio_unitario"))))))))));

            Iterator<Document> it = resultado.iterator();
            if (!it.hasNext()){
                System.out.println("vacio");
            }
            else {
                Document carrito = it.next();
                System.out.println("Carrito actual del cliente:");
                System.out.println(carrito.toJson());
            }
        }
    }
    private static void pedidosCliente(MongoDatabase database){
        MongoCollection<Document> coleccion = database.getCollection("pedidos");
        System.out.println("Colección pedidos seleccionada correctamente");
        FindIterable<Document>resultado=coleccion.find(eq("cliente_id", idUsuario));

        Iterator<Document> it = resultado.iterator();
        if (!it.hasNext()){
            System.out.println("vacio");
        }
        else {
            Document pedido = it.next();
            System.out.println("Pedidos actuales del cliente:");
            System.out.println(pedido.toJson());
        }
    }

    private static void cambiaValorCliente(MongoDatabase database) {
        if (idUsuario==null) System.out.println("aun no se habia seleccionado un usuario");
        else {
            MongoCollection<Document> coleccion = database.getCollection("clientes");
            System.out.println("Colección personas seleccionada correctamente");
            List<String> datosCambio = valoresAcambiar();
            try {
                UpdateResult rs1 = coleccion.updateOne(eq("_id", idUsuario),
                        set(datosCambio.get(0), datosCambio.get(1))
                );
                if (rs1.getModifiedCount() > 0) {
                    System.out.println("se cambio");
                } else System.out.println("no se cambio");

            } catch (Exception e) {
                System.out.println("el correo introducido ya existia");
            }
        }
    }

    private static List<String> valoresAcambiar(){
        List<String> lista=new ArrayList<>();
        int opcion=-1;
        do{
        opcion=pedirInt("selecciona uno\n" +
        "0 nombre\n" +
        "1 email\n" +
        "2 direccion");
        }while (opcion<0 || opcion>2);

        switch (opcion){
            case 0:lista.add("nombre");
            break;

            case 1:lista.add("email");
            break;

            case 2:lista.add("direccion");
            break;

        }
        String nuevoValor=pedirString("introduce nuevo valor para "+lista.getFirst());
        lista.add(nuevoValor);
        return lista;
    }
    private static int pedirInt(String mensaje) {
        while(true){
            try{
                System.out.println(mensaje);
                return sc.nextInt();
            }catch (Exception e){sc.nextLine();}
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
}