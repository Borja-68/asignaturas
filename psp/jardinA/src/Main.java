import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Scanner in=new Scanner(System.in);
        int dentro=0;
        int fuera=0;
        int numero=0;
        System.out.println("personas dentro");
        dentro=in.nextInt();
        in.nextLine();

        System.out.println("personas fuera");
        fuera=in.nextInt();
        in.nextLine();
        Jardin jar=new Jardin();

        for (int z=0;z<dentro;z++){
            Persona per1=new Persona(numero,Estado.dentro);
            jar.listaPersonas.add(per1);
            numero++;
        }

        for (int x=0;x<fuera;x++){
            Persona per2=new Persona(numero,Estado.fuera);
            jar.listaPersonas.add(per2);
            numero++;
        }

        jar.start();

    }
}