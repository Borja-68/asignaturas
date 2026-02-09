package org.example;
import Entidades.*;
import Repos.*;
import org.hibernate.Session;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    public static CicloRepo ciclorep;
    public static InstiRepo instiRepo;
    public static DirectorRep dirRep;
    public static TallerRepo tallrep;
    public static UsoRepo usorep;


    public static void main(String[] args) {
        System.out.println("Test");

         String mensaje = "1. Crear instituto\n" +
                "2. Eliminar instituto\n" +
                "3. Crear ciclo\n" +
                "4. Eliminar ciclo\n" +
                "5. Modificar telefono instituto\n" +
                "6. Asignar director al IES\n" +
                "7. Asignar Ciclo al IES\n" +
                "8. Consulta 1\n" +
                "9. Consulta 2\n" +
                "10. Consulta 3\n" +
                "11. Salir";

        Session session = HibernateUtil.get().openSession();
        ciclorep=new CicloRepo(session);
        instiRepo=new InstiRepo(session);
        dirRep=new DirectorRep(session);
        tallrep=new TallerRepo(session);
        usorep=new UsoRepo(session);

        Scanner input=new Scanner(System.in);
        boolean salir=false;
        while (!salir) {
            System.out.println(mensaje);

            int opcion=input.nextInt();
            input.nextLine();
            switch (opcion){
                case 1: instiRepo.guardar(new Instituto()); break;
                case 2: borraInsti(input); break;
                case 3: ciclorep.guardar(new Ciclo()); break;
                case 4:borraCiclo(input); break;
                case 5: cambiaTelefono(input); break;
                case 6: directorAInstituto(input); break;
                case 7: nuevoUso(input); break;
                case 8: getInstiporDirec(input); break;
                case 9: getciclosInsti(input); break;
                case 10: getTalleresInsti(input); break;
                case 11: salir=true; break;

            }
        }


        session.close();
        System.out.println("Finalizando la conexion a MySQL");
    }

    public static void borraInsti(Scanner input){
        System.out.println("introduce el codigo del instituto a borrar");
        instiRepo.borrar(input.nextInt());
        input.nextLine();
    }

    public static void borraCiclo(Scanner input){
        System.out.println("introduce el codigo del ciclo a borrar");
        ciclorep.borrar(input.nextInt());
        input.nextLine();
    }

    public static void cambiaTelefono(Scanner input){
        System.out.println("introduce el codigo del Instituto a cambiar");
        int codigo=input.nextInt();
        input.nextLine();
        System.out.println("introduce el nuevo numero de telefono");
        String telefono=input.next("[0-9]{9}");
        instiRepo.actualizarTelefono(codigo,telefono);
        input.nextLine();
    }
    public static void directorAInstituto(Scanner input) {
        try {
            System.out.println("introduce el codigo del Instituto");
            int codigo = input.nextInt();
            input.nextLine();
            Instituto insti=instiRepo.instiExiste(codigo);

            System.out.println("introduce id del derector");
            int id = input.nextInt();
            input.nextLine();
            Director direc=dirRep.directorExiste(id);

            insti.setDirector(direc);

            instiRepo.actualizar(insti);
        } catch (ArrayIndexOutOfBoundsException e) {
            return;
        } catch (RuntimeException e) {
            return;
        }
    }

    public static void nuevoUso(Scanner input){
        try {
            System.out.println("introduce el codigo del ciclo");
            int codigo1 = input.nextInt();
            input.nextLine();
            Ciclo ciclo=ciclorep.cicloExiste(codigo1);

            System.out.println("introduce el codigo del taller");
            int codigo2 = input.nextInt();
            input.nextLine();
            Taller tall=tallrep.tallerExiste(codigo2);

            System.out.println("hora del uso");
            LocalDateTime hora=LocalDateTime.parse(input.next());
            System.out.println("dia del uso");
            LocalDate dia=LocalDate.parse(input.next());

            UsoPK id=new UsoPK(ciclo.getCodigo(),tall.getCodigo());
            Uso uso=new Uso(id,ciclo,tall,hora,dia);

            usorep.guardar(uso);
        } catch (ArrayIndexOutOfBoundsException e) {
            return;
        } catch (RuntimeException e) {
            return;
        }
    }

    public static void getInstiporDirec(Scanner input){
        System.out.println("introduce id del derector");
        int id = input.nextInt();
        input.nextLine();
       instiRepo.obtenerInstiPorDirector(id);
    }

    public static void getciclosInsti(Scanner input){
        System.out.println("introduce codigo del insti");
        int id = input.nextInt();
        input.nextLine();
        ciclorep.getCiclos(id);
    }

    public static void getTalleresInsti(Scanner input){
        System.out.println("introduce codigo del insti");
        int id = input.nextInt();
        input.nextLine();
        tallrep.getTaller(id);
    }
}
