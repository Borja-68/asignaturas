package org.example;
import Entidades.*;
import Repositorios.*;
import org.hibernate.Session;

import java.time.LocalDate;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    private static  EventoRepositorio repEven;
    private static  HabilidadRepositorio repHab;
    private static  ParticipaRepositorio repParticip;
    private static  PersonajeRepositorio repPersonaj;
    private static  TrajeRepositorio repTraj;

    private static String pregunta=
            "1 crear personaje"+"\n"+
            "2 borrar personaje"+"\n"+
            "3 modificar personaje"+"\n"+
            "4 crear habilidad"+"\n"+
            "5 borrar habilidad"+"\n"+
            "6 modificar habilidad"+"\n"+
            "7 asignar habilidad a personaje"+"\n"+
            "8 registrar participacien en evento de un personaje"+"\n"+
            "9 cambiar traje personaje"+"\n"+
            "10 mostrar datos personaje"+"\n"+
            "11 mostrar personajes de evento determinado"+"\n"+
            "12 mostrar cantidad de personajes con habilidad concreta"+"\n"+
            "13 salir";



    public static void main(String[] args) {
        System.out.println("Test");

        Session session = HibernateUtil.get().openSession();
        Scanner input=new Scanner(System.in);

        repEven=new EventoRepositorio(session);
        repHab=new HabilidadRepositorio(session);
        repParticip= new ParticipaRepositorio(session);
        repPersonaj=new PersonajeRepositorio(session);
        repTraj=new TrajeRepositorio(session);

        boolean salir=false;
        do{
            System.out.println(pregunta);
            int opcion=input.nextInt();

            switch (opcion){

                case 1:
                        repPersonaj.guardar(creaPersonaje(input));
                        break;

                case 2:
                        borraPersonaje(input);
                        break;

                case 3:
                        modificaPersonaje(input);
                        break;

                case 4:
                        repHab.guardar(creaHabilidad(input));
                        break;

                case 5:
                        borraHabilidad(input);
                        break;

                case 6:
                        modificaHabilidad(input);
                        break;

                case 7:
                        anadirHabilidadHeroe(input);
                        break;

                case 8:
                        creaParticipacion(input);
                        break;

                case 9:
                        modificaTrajePersonaje(input);
                        break;

                case 10:
                        muestraDatosPersonaje(input);
                        break;

                case 11:
                        muestraPersonajesDeEvento(input);
                        break;

                case 12:
                        cuentaPersonajesDeHabilidad(input);
                        break;

                case 13:
                        salir=true;
                        break;

                default:
                        System.out.println("opcion no valida");
                        break;

            }
        }while (!salir);

        session.close();
        System.out.println("Finalizando la conexion a MySQL");
    }


    public  static void borraPersonaje(Scanner sc){
        muestraIdsYNombre(repPersonaj);
        System.out.println("seleccione la id de la persona que quiere eliminar, numero");
        int id=sc.nextInt();
        repPersonaj.borrar(repPersonaj.getPersonajePorId(id));
    }


    public  static void modificaPersonaje(Scanner sc){
        muestraIdsYNombre(repPersonaj);
        System.out.println("seleccione la id de la persona que quiere modificar, numero");
        int id=sc.nextInt();
        Personaje personajeOR=repPersonaj.getPersonajePorId(id);

        System.out.println("introduce el nombre del personaje");
        String nombre=sc.next();
        personajeOR.setNombre(nombre);

        System.out.println("introduce el alias del personaje");
        String alias=sc.next();
        personajeOR.setAlias(alias);
        repPersonaj.modificar(personajeOR);
    }


    public  static void borraHabilidad(Scanner sc){
        muestraIdsYNombre(repHab);
        sc.nextLine();
        System.out.println("seleccione la habilidad que quiere eliminar, nombre");
        String hab=sc.nextLine();
        repHab.borrar(repHab.getHabilidadPorNombre(hab));
    }


    public  static void modificaHabilidad(Scanner sc){
        muestraIdsYNombre(repHab);
        System.out.println("seleccione la id de la habilidad que quiere modificar, numero");
        int id=sc.nextInt();
        Habilidad habilidadOR=repHab.getHabilidadPorId(id);
        Habilidad habilidadCambiado=creaHabilidad(sc);
        habilidadOR.setNombre(habilidadCambiado.getNombre());
        habilidadOR.setDescripcion(habilidadCambiado.getDescripcion());
        repHab.modificar(habilidadOR);
    }


    public  static void anadirHabilidadHeroe(Scanner sc){
        muestraIdsYNombre(repPersonaj);
        sc.nextLine();
        System.out.println("selecciona al heroe al que quieres darle la habilidad, escribe su nombre");
        String nombreHeroe= sc.nextLine();
        Personaje personaje=repPersonaj.getPersonajePorNombre(nombreHeroe);

        muestraIdsYNombre(repHab);
        System.out.println("selecciona la habilidad que quieres darle, escribe su nombre");
        String nombreHab= sc.nextLine();
        Habilidad habilidad=repHab.getHabilidadPorNombre(nombreHab);

        personaje.addHabilidad(habilidad);
        repPersonaj.modificar(personaje);
    }


    public  static void modificaTrajePersonaje(Scanner sc){
        muestraIdsYNombre(repPersonaj);
        sc.nextLine();
        System.out.println("escribe el nombre de la persona que quiere modificar");
        String nombre=sc.nextLine();
        Personaje personajeOR=repPersonaj.getPersonajePorNombre(nombre);

        Traje nuevoTraje=creaTraje(sc);
        personajeOR.setTraje(nuevoTraje);

        repPersonaj.modificar(personajeOR);
    }


    public  static void creaParticipacion(Scanner sc){
        muestraIdsYNombre(repPersonaj);
        sc.nextLine();
        System.out.println("selecciona al heroe que atenderá el evento, escribe su nombre");
        String nombreHeroe=sc.nextLine();
        Personaje personaje=repPersonaj.getPersonajePorNombre(nombreHeroe);

        sc.nextLine();
        muestraIdsYNombre(repEven);
        System.out.println("selecciona el evento que atiende, escribe su nombre");
        String nombreEv= sc.nextLine();
        Evento evento=repEven.getEventoPorNombre(nombreEv);

        ParticipaPK id=new ParticipaPK(personaje.getId(),evento.getId());

        System.out.println("escribe la fecha, como 2015-02-22");
        LocalDate fecha=LocalDate.parse(sc.next());

        sc.nextLine();
        System.out.println("escribe el rol del personaje");
        String rol=sc.nextLine();

        Participa participa=new Participa(fecha,rol);
        participa.setId(id);
        participa.setEvento(evento);
        participa.setPersonaje(personaje);

        repParticip.guardar(participa);
    }


    public static void muestraDatosPersonaje(Scanner sc){
        muestraIdsYNombre(repPersonaj);
        System.out.println("selecciona el personaje para mirar sus datos, numero");
        int id=sc.nextInt();

        repPersonaj.muestraDatosPersonajePorId(id);
    }


    public static void muestraPersonajesDeEvento(Scanner sc){
        muestraIdsYNombre(repEven);
        System.out.println("selecciona el evento, numero");
        int id=sc.nextInt();

        repPersonaj.muestraPersonajesEvento(id);

    }


    public static void cuentaPersonajesDeHabilidad(Scanner sc){
        muestraIdsYNombre(repHab);
        System.out.println("selecciona la habilidad, numero");
        int id=sc.nextInt();

        repPersonaj.cuentaPersonajesPorHabilidad(id);
    }


    public static Personaje creaPersonaje(Scanner sc){
        sc.nextLine();
        System.out.println("introduce el nombre del personaje");
        String nombre=sc.nextLine();

        System.out.println("introduce el alias del personaje");
        String alias=sc.nextLine();

        return new Personaje(nombre,alias,creaTraje(sc));
    }


    public static Traje creaTraje(Scanner sc){

        System.out.println("introduce la especificacion del traje");
        sc.nextLine();
        String especificacion= sc.nextLine();
        Traje traj=new Traje(especificacion);
        repTraj.guardar(traj);
        return  traj;
    }


    public static Habilidad creaHabilidad(Scanner sc){
        sc.nextLine();
        System.out.println("introduce el nombre de la habilidad");
        String nombre=sc.nextLine();

        sc.nextLine();
        System.out.println("introduce la descripcion de la habilidad");
        String descripcion=sc.nextLine();

        Habilidad habilidad=new Habilidad(nombre,descripcion);
        return habilidad;
    }


    public static void muestraIdsYNombre(Repositorio rep){
         rep.muestraId();
    }
}
