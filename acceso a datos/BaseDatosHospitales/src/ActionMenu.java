import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ActionMenu {
    public static void meun(){
        Scanner input=new Scanner(System.in);
        boolean salir=false;
        int opcion=-1;
        do{
            try {

                System.out.println("1 Crear una nueva especialidad médica (PostgreSQL)\n" +
                        "2 Crear un nuevo médico (PostgreSQL)\n" +
                        "3 Eliminar un médico por ID (PostgreSQL)\n" +
                        "4 Crear un nuevo paciente (MySQL)\n" +
                        "5 Eliminar un paciente (MySQL)\n" +
                        "6 Crear nuevo tratamiento (nombre, descripcion, especialidad, médico) (MySQL + PostgreSQL)\n" +
                        "7 Eliminar un tratamiento por su nombre (MySQL + PostgreSQL)\n" +
                        "8 Listar tratamientos (menos de X pacientes asignados) (MySQL)\n" +
                        "9 Obtener el total de citas realizadas por cada paciente (MySQL)\n" +
                        "10 Obtener la cantidad de tratamientos por sala (PostgreSQL)\n" +
                        "11 Listar todos los tratamientos con sus respectivas especalidades y médicos (MySQL + PostgreSQL)\n" +
                        "12 Obtener todos los pacientes que han recibido un tratamiento de una especialidad dada (MySQL + PostgreSQL).\n"+
                        "13 salir");
                opcion=input.nextInt();
                input.nextLine();
                switch (opcion){
                    case 1: break;
                    case 2: break;
                    case 3: break;
                    case 4: break;
                    case 5: break;
                    case 6: break;
                    case 7: break;
                    case 8: break;
                    case 9: break;
                    case 10: break;
                    case 11: break;
                    case 12: break;
                    case 13: break;
                    default: break;

                }
            }catch (InputMismatchException e){
                System.out.println("no se introdujo un numero");
                input.nextLine();
            }
        }while(!salir);
    }

    private static void nuevaEspecialidad(Scanner input){
        System.out.println("introduzca el nombre de la especialidad");
        String nombreEspecialidad = getNormalString(input);
        AccionesDB.nuevaEspecialidad(nombreEspecialidad);
    }

    private static  void nuevomedico(Scanner input){
        System.out.println("introduzca el nombre del medico");
        String nombreMedico = getNormalString(input);
        Contacto contacto = creaContacto(input);
        AccionesDB.nuevoMedico(nombreMedico,contacto.getNif(),contacto.getTelefono(),contacto.getCorreo());
    }

    public static void quitaMedico(Scanner input){
       if (AccionesDB.anyMedicExists()){
            int numero = -1;
            do {
                try {
                    System.out.println("introduzca la id del medico que quiere eliminar");
                    numero = input.nextInt();
                    input.nextLine();

                } catch (InputMismatchException e) {
                    System.out.println("lo introducido no fue un numero");
                    input.nextLine();
                }
            } while (!AccionesDB.medicExist(numero));
            AccionesDB.quitaMedico(numero);
        }
    }

    public static void nuevoPaciente(Scanner input){

        System.out.println("introduce el nombre");
        String nombre = getNormalString(input);

        System.out.println("introduce el correo, sin @gmail.com");
        String email = getNormalString(input);
        if (!email.contains("@gmail.com")) email = email + "@gmail.com";

        LocalDate fecha;
        fecha.format("yy/mm/dd");
    }


    private static String getNormalString(Scanner input){
        String cadena="";
        do{
            System.out.println("introduce la cadena menor de 100 caracteres");
            cadena=input.next();
            input.nextLine();
        }while(cadena.length()>100);
        return cadena;
    }


    private static Contacto creaContacto(Scanner input) {
        System.out.println("inserta nombre del contacto");
        String nombreContacto = getNormalString(input);

        String nif = "";
        do {
            System.out.println("inserta el nif");
            nif = input.next();
            input.nextLine();
        } while (!Contacto.nifCorrecto(nif));

        String telefono = "";
        do {
            System.out.println("inserta el telefono, sin el +34");
            telefono = input.next();
            input.nextLine();
        } while (!Contacto.telefonoCorrecto(telefono));

        System.out.println("inserta el correo sin @gmail.com");
        String correo = getNormalString(input);

        return new Contacto(nombreContacto, nif, Integer.parseInt(telefono), correo);
    }



    private static String getFecha(Scanner input){
        int anio=-1;
        int mes=-1;
        int dia=-1;
        do {
            try {
                System.out.println("introduce el año entre 1925 y 2025");
                anio=input.nextInt();
                input.nextLine();

            }catch (InputMismatchException e){
                System.out.println("no se introdujo un numero");
                input.nextLine();
            }
        }while (anio<1925 || anio>2025);

        do {
            try {
                System.out.println("introduce el mes entre 1 y 12");
                mes=input.nextInt();
                input.nextLine();

            }catch (InputMismatchException e){
                System.out.println("no se introdujo un numero");
                input.nextLine();
            }
        }while (mes<1 || mes>12);

        int maximoDias=-1;
        switch (mes){
            case 1,3,5,7,8,10,12:maximoDias=31; break;
            case 2:maximoDias=28; break;
            case 4,6,9,11:maximoDias=28; break;
        }

        do{
            try{
                System.out.println("introduce el dia entre 1 y "+maximoDias);
                dia=input.nextInt();
                input.nextLine();

            }catch (InputMismatchException e){
                System.out.println("no se introdujo un numero");
                input.nextLine();
            }
        }while (dia<1 || dia>maximoDias);
        return anio+"-"+mes+"-"+dia;
    }


}
