import java.util.InputMismatchException;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    private static String mensaje = "1. Crear esquema desguace\n" +
            "2. Crear tipo Componente\n" +
            "3. Insertar nueva venta\n" +
            "4. Actualizar la información de un coche\n" +
            "5. Eliminar la información de un coche\n" +
            "6. Consulta 1\n" +
            "7. Consulta 2\n" +
            "8. Consulta 3\n" +
            "9. Consulta 4\n" +
            "10. Consulta 5\n" +
            "11. Consulta 6\n" +
            "12. Salir";
    private static Scanner input=new Scanner(System.in);
    public static void main(String[] args) {
        ConexionBase.gitConexionPostgre();
        Boolean salir=false;
        int opcion=-1;

        do{
            try {

                System.out.println("elige una de las opciones");
                System.out.println(mensaje);
                opcion = input.nextInt();
                input.nextLine();
                switch (opcion){
                    case 1:creaEsquema();break;
                    case 2:creaComponente();break;
                    case 3:creaVenta();break;
                    case 4:actualizaCoche();break;
                    case 5:quitaInformacionCoche();break;
                    case 6:consulta1();break;
                    case 7:consulta2();break;
                    case 8:consulta3();break;
                    case 9:consulta4();break;
                    case 10:consulta5();break;
                    case 11:consulta6();break;
                    case 12:ConexionBase.close();salir=true; input.close();break;
                    default:System.out.println("opcion introducida no valida"); break;
                }
            }catch (InputMismatchException e){
                System.err.println("no se introdujo un numero");
                input.nextLine();
            }

        }while (!salir);
    }

    private static void creaEsquema(){
            AccionesPostgre.creaEsquemaDesguace();
    }

    private static void creaComponente(){
            AccionesPostgre.creaTipoComponente();
    }

    private static void creaVenta(){
        AccionesPostgre.creaVenta();
        System.out.println("creada una venta con datos id cliente 6, id coche 5 , fecha 2023-04-05, precio 35000");
    }

    private static void actualizaCoche(){
            AccionesPostgre.cambiaCoche();
        System.out.println("cambio de año de fabricacion a 3000 del coche de id 1");

    }

    private static void quitaInformacionCoche(){
            AccionesPostgre.quitaCocheDeClienteYVentas();
        System.out.println("eliminados clientes que tengan como preferido el coche con id 1 y las ventas con el coche de id 1");
    }

    private static void consulta1(){
            AccionesPostgre.obtenEdadMediaClentes();
    }

    private static void consulta2(){
            AccionesPostgre.obtenerNombreYEspecialidadVendedores();
    }

    private static void consulta3(){
            AccionesPostgre.cochePreferidosMayores30();
    }

    private static void consulta4(){
            AccionesPostgre.datosCocheYVendedor();
    }

    private static void consulta5(){
            AccionesPostgre.numeroVentasVendedores();
    }

    private static void consulta6(){
        AccionesPostgre.datosVendedoresSinVentas();
    }

}