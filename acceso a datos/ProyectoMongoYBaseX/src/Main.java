import org.basex.examples.api.BaseXClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    static Scanner sc;
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
    public static void main(String[] args) {
        sc = new Scanner(System.in);
        int opcion=-1;
        do {
            try {
                System.out.println(consultas);
                opcion = pedirInt("introduzca opcion: ");
                if(opcion>=1 && opcion<=7){
                    consultaBaseX("","productos",opcion);
                }
                else {
                    System.out.println("nc");
                }
            } catch (InputMismatchException e) {
                System.out.println("no se introdujo un numero");

            }
        } while (opcion!=18);

    }
    private static void consultaBaseX(String consulta,String nombreDB,Integer opcion){
        try(BaseXClient sesion = new BaseXClient("localhost", 1984, "admin", "abc123")) {
            sesion.execute("open productos");
            switch (opcion){
                case 1: cambiaProductoPorIdBaseX(sesion);break;
                case 2: borraProductoPorIdBaseX(sesion);
                break;
                case 3: ejecutarConsultaBaseX(sesion,"for $i in db:get('productos')/productos/producto \n" +
                        "order by $i/nombre\n" +
                        " return <il>{$i/id}-{$i/nombre}-{$i/precio}-{$i/disponibilidad }-{$i/categoria }</il>");
                break;
                case 4: int cantDisponibilidad=pedirInt("cantidad de disponibilidad minima");
                        ejecutarConsultaBaseX(sesion,"for $i in db:get('productos')/productos/producto \n" +
                        "where $i/disponibilidad>"+cantDisponibilidad+"\n" +
                        " return <il>{$i/id}-{$i/nombre}-{$i/precio}-{$i/disponibilidad }-{$i/categoria }</il>");
                        break;
                case 5: ejecutarConsultaBaseX(sesion,"for $i in distinct-values(db:get('productos')/productos/producto/categoria)\n" +
                        "let $maxPrecio := max(db:get('productos')/productos/producto[categoria = $i]/precio)\n" +
                        "let $productoMasCaro := (db:get('productos')/productos/producto[categoria = $i and precio = $maxPrecio])\n" +
                        "return <producto>{$i}-{$productoMasCaro/nombre}-{$productoMasCaro/precio}</producto>");
                break;
                case 6: String subCadenaDescripcion=pedirString("introduzca subcadena a revisar");
                    ejecutarConsultaBaseX(sesion,"for $i in db:get('productos')/productos/producto\n" +
                            "where contains(lower-case($i/descripcion), '"+subCadenaDescripcion+"')\n" +
                            "order by $i/fabricante descending\n" +
                            "return <producto>{$i/nombre}-{$i/fabricante}</producto>");
                    break;
                case 7: ejecutarConsultaBaseX(sesion,"for $i in distinct-values(db:get('productos')/productos/producto/categoria)\n" +
                        "let $total :=sum(db:get('productos')/productos/producto/disponibilidad)\n" +
                        "let $totalCategoria := sum(db:get('productos')/productos/producto[categoria=$i]/disponibilidad)\n" +
                        "return <producto>{$i}-{$totalCategoria}-{concat(format-number(($totalCategoria div $total) * 100,'0.00'),'%')}</producto>");
                break;

            }

//            ejecutarConsultaBaseX(sesion, consulta);
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
        try(BaseXClient.Query query = sesion.query("for $i in db:get(\"productos\")/productos/producto return $i" )) {

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
}