package org.example;
import Entidades.*;
import Repositorios.*;
import org.hibernate.Session;

import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    private static String pregunta=
            "1 crear personaje"+"\n"+
            "2 borrar personaje"+"\n"+
            "3 modificar personaje"+"\n"+
            "4 crear habilidad"+"\n"+
            "5 borrar habilidad"+"\n"+
            "6 modificar habilidad"+"\n"+
            "7 asignar habilidada personaje"+"\n"+
            "8 registrar participacien en evento de un personaje"+"\n"+
            "9 cambiar traje personaje"+"\n"+
            "10 mostrar datos personaje"+"\n"+
            "11 mostrar personajes de evento determinado"+"\n"+
            "12 mostrar cantidad de personajes con habilidad concreta"+"\n"+
            "13 salir"+"\n";

    public static void main(String[] args) {
        System.out.println("Test");

        Session session = HibernateUtil.get().openSession();
        Scanner input=new Scanner(System.in);

        EventoRepositorio repEven=new EventoRepositorio(session);
        HabilidadRepositorio repHab=new HabilidadRepositorio(session);
        ParticipaRepositorio repParticip= new ParticipaRepositorio(session);
        PersonajeRepositorio repPersonaj=new PersonajeRepositorio(session);
        TrajeRepositorio repTraj=new TrajeRepositorio(session);
        boolean salir=false;
        do{
            System.out.println(pregunta);
            int id;
            int opcion=input.nextInt();
            switch (opcion){
                case 1: Personaje personaje=creaPersonaje(input);
                        repPersonaj.guardar(personaje);
                        break;

                case 2:
                        id=input.nextInt();
                        repPersonaj.borrar(id);
                        break;

                case 3: Personaje personajeCambio=creaPersonaje(input);
                        id=input.nextInt();
                        repPersonaj.modificar(id,personajeCambio);
                        break;

                case 4: break;
                case 5: break;
                case 6: break;
                case 7: break;
                case 8: break;
                case 9: break;
                case 10: break;
                case 11: break;
                case 12: break;
                case 13:salir=true; break;
                default:System.out.println("opcion no valida"); break;

            }
        }while (!salir);


        session.close();
        System.out.println("Finalizando la conexion a MySQL");
    }

    public static Personaje creaPersonaje(Scanner sc){
        System.out.println("introduce el nombre del persoonaje");
        String nombre=sc.next();

        System.out.println("introduce el alias del persoonaje");
        String alias=sc.next();

        Personaje personaje=new Personaje(nombre,alias);
        return personaje;
    }

    public static Personaje creaHabilidad(Scanner sc){
        System.out.println("introduce el nombre de la habilidad");
        String nombre=sc.next();

        System.out.println("introduce la descripcion de la habilidad");
        String alias=sc.next();

        Personaje personaje=new Personaje(nombre,alias);
        return personaje;
    }
}
