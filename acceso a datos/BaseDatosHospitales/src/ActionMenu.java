import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ActionMenu {
    public static void menu(){
        Scanner input=new Scanner(System.in);
        boolean salir=false;
        ConexionesDB.getConectionMysql();
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
                    case 1: nuevaEspecialidad(input);   break;
                    case 2: nuevomedico(input);         break;
                    case 3: quitaMedico(input);         break;
                    case 4: nuevoPaciente(input);       break;
                    case 5: quitaPaciente(input);       break;
                    case 6: creaTratamiento(input);     break;
                    case 7: quitaTratamiento(input);    break;
                    case 8: listarTratamientosPorPaciente(input); break;
                    case 9: listaCitasPaciente(); break;
                    case 10: listaSalasTratamiento(); break;
                    case 11: devueveDatosTratamiento(); break;
                    case 12: obtenerPacientesPorEspecialidad(input); break;
                    case 13: input.close();
                        AccionesMysql.close(); AccionesPostgre.close(); AccionMixta.close(); salir=true; break;
                    default: System.out.println("opcion seleccionada no valida");break;

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
        AccionesPostgre.crearEspecialidad(nombreEspecialidad);
    }

    private static  void nuevomedico(Scanner input){
        System.out.println("introduzca el nombre del medico");
        String nombreMedico = getNormalString(input);
        Contacto contacto = creaContacto(input);
        AccionesPostgre.crearMedico(nombreMedico,contacto.getNif(),contacto.getTelefono(),contacto.getCorreo());
    }

    public static void quitaMedico(Scanner input){
       if (AccionesPostgre.anyMedicExists()){
            int numero = -1;
            do {
                try {
                    System.out.println("nombres medicos existentes");
                    AccionesPostgre.listaIdMedicos();
                    System.out.println("introduzca la id del medico que quiere eliminar");
                    numero = input.nextInt();
                    input.nextLine();

                } catch (InputMismatchException e) {
                    System.out.println("lo introducido no fue un numero");
                    input.nextLine();
                }
            } while (!AccionesPostgre.medicExist(numero));
           AccionesPostgre.eliminarMedico(numero);
        }
    }

    public static void nuevoPaciente(Scanner input){

        System.out.println("introduce el nombre");
        String nombre = getNormalString(input);

        System.out.println("introduce el correo, sin @gmail.com");
        String email = getNormalString(input);
        if (!email.contains("@gmail.com")) email = email + "@gmail.com";

        LocalDate fecha= LocalDate.parse(getFecha(input));
        AccionesMysql.crearPaciente(nombre,email,fecha);

    }

    public static void quitaPaciente(Scanner input){
        int numero = -1;
        if (AccionesMysql.anyPaciente()){
            do {
                try {
                    System.out.println("ids pacientes existentes");
                    AccionesMysql.listaIdPacientes();
                    System.out.println("introduzca la id del paciente que quiere eliminar");
                    numero = input.nextInt();
                    input.nextLine();

                } catch (InputMismatchException e) {
                    System.out.println("lo introducido no fue un numero");
                    input.nextLine();
                }
            } while (!AccionesMysql.pacienteExist(numero));
            AccionesMysql.eliminarPaciente(numero);
        }
    }

    public  static  void creaTratamiento(Scanner input){

        System.out.println("introduce nombre tratamiento");
        String nombre = getNormalString(input);

        System.out.println("introduce la descripcion tratamiento");
        String descripcion = input.next();
        input.nextLine();

        System.out.println("nombres especialidades existentes");
        AccionesPostgre.listaNombreEspecialidades();
        System.out.println("introduzca la nombre de la especialidad");

        String nombreEspecialidad = getNormalString(input);

        String nif = "";
        do {
            System.out.println("nifs medicos existentes");
            AccionesPostgre.listaNifMedicos();
            System.out.println("inserta el nif");
            nif = input.next();
            input.nextLine();
        } while (!Contacto.nifCorrecto(nif));

        AccionMixta.crearTratamiento(nombre,descripcion,nombreEspecialidad,nif);
    }

    public static void quitaTratamiento(Scanner input){
        System.out.println("nombres tratamientos existentes");
        AccionesMysql.listaNombreTratamientos();
        System.out.println("introduce el nombre del tratamiento");
        String nombre = getNormalString(input);

        AccionMixta.eliminarTratamientoPorNombre(nombre);
    }

    public  static void listarTratamientosPorPaciente(Scanner input){
        int maxPacientes = -1;
        do {
            try {
                System.out.println("introduzca la cantidad de pacientes maxima");
                maxPacientes = input.nextInt();
                input.nextLine();

            } catch (InputMismatchException e) {
                System.out.println("lo introducido no fue un numero");
                input.nextLine();
            }

        } while (maxPacientes <= 0);
        AccionesMysql.listarTratamientosConPocosPacientes(maxPacientes);

    }

    public  static void listaCitasPaciente(){
        AccionesMysql.obtenerTotalCitasPorPaciente();
    }

    public  static void listaSalasTratamiento(){
        AccionesPostgre.obtenerCantidadTratamientosPorSala();
    }

    public static void devueveDatosTratamiento(){
        AccionMixta.listarTratamientosConEspecialidadYMedico();
    }

    public static void obtenerPacientesPorEspecialidad(Scanner input){

        int id_especialidad = -1;
        do {
            try {
                System.out.println("ids especialidades existentes");
                AccionesPostgre.listaIdEspecialidades();
                System.out.println("introduzca el numero de la especialidad deseada");
                id_especialidad = input.nextInt();
                input.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("lo introducido no fue un numero");
                input.nextLine();
            }

        } while (id_especialidad <= 0);
        AccionMixta.obtenerPacientesPorEspecialidad(id_especialidad);
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

        return new Contacto(nif, Integer.parseInt(telefono), correo);
    }



    private static String getFecha(Scanner input){
        String anio="-1";
        String mes="-1";
        String dia="-1";
        do {
            try {

                System.out.println("introduce el año entre 1925 y 2025");
                anio=input.next();
                input.nextLine();
                Integer.parseInt(anio);
            } catch (NumberFormatException e){
                System.out.println("introduzca numeros");
                anio="-1";
            }
        }while (Integer.parseInt(anio)<1925 || Integer.parseInt(anio)>2025 || anio.length()!=4);

        do {
            try {
                System.out.println("introduce el mes entre 01 y 12");
                mes = input.next();
                input.nextLine();
                Integer.parseInt(mes);
            }catch (NumberFormatException e){
                    System.out.println("introduzca numeros");
                    mes="-1";
                }
        }while (Integer.parseInt(mes)<1 || Integer.parseInt(mes)>12 || mes.length()!=2);

        int maximoDias=-1;
        switch (mes){
            case "01","03","05","07","08","10","12":maximoDias=31; break;
            case "02":maximoDias=28; break;
            case "04","06","09","11":maximoDias=28; break;
        }

        do{
            try{
                System.out.println("introduce el dia entre 01 y "+maximoDias);
                dia=input.next();
                input.nextLine();
                Integer.parseInt(dia);
            }catch (NumberFormatException e){
                System.out.println("introduzca numeros");
                dia="-1";
            }
        }while (Integer.parseInt(dia)<1 || Integer.parseInt(dia)>maximoDias || dia.length()!=2);
        return anio+"-"+mes+"-"+dia;
    }


}
