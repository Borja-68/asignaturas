import java.util.ArrayList;
import java.util.Scanner;

public class gestionContacto {
    private static ArrayList<Contacto> contactos=new ArrayList<>();
    private static Scanner input=new Scanner(System.in);
    public gestionContacto(){}
    public static  void menu(){
    int opcion;
    boolean fin=false;
    do{
        System.out.println("selecciona accion 1(añade contacto) 2( muestra contacto) 3 (quita contacto) 4(salir)");
        opcion=input.nextInt();
        switch (opcion){
            case 1:anadirContacto(input);
            break;
            case 2:muestraContactos();
            break;
            case 3:quitaContacto(input);
            break;
            case 4:fin=true; break;
            default:break;
        }
    }while(!fin);
    }

    public  static String insertaNumero(Scanner input){
        String nunmero;
        do {
            System.out.println("introduce el numero con este formato 11111111");;
            nunmero= input.next();
            if (nunmero.matches("[0-9]{8}")) {
                break;
            }
            else{System.out.println("formato introducido incorrecto");}
        }while(true);
        return nunmero;
    }

    public  static String insertaNombre(Scanner input){
            String nombre;
            System.out.println("introduce el nombre ");;
            nombre= input.next();
            return nombre;
    }

    public static void anadirContacto(Scanner input){
        Contacto contacto= new Contacto(insertaNumero(input),insertaNombre(input));
        contactos.add(contacto);
    }
    public static void muestraContactos(){
        for (Contacto contacto: contactos){
            System.out.println(contacto.toString());
        }
    }
    public static void quitaContacto(Scanner input){
        String numero=insertaNumero(input);
        for(Contacto contacto: contactos){
            if(contacto.getNumero().equals(numero)) contactos.remove(contacto);
        }
    }
}
