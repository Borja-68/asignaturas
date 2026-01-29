import java.util.ArrayList;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Scanner input=new Scanner(System.in);
        int puertas=-1;
        do {
            System.out.println("introduce numero de puertas 1-4");
             puertas = input.nextInt();
            input.nextLine();
        }while (puertas<0 || puertas>4);

        ArrayList<Boolean> puertasCrear=new ArrayList<>();
        for (int i=0;i<puertas;i++)puertasCrear.add(true);

        int personasMax=-1;
        do {
            System.out.println("introduce numero de personas maximas 1-infinit");
            personasMax= input.nextInt();
        }while (personasMax<=0);


        int personas=-1;
        do {
            System.out.println("introduce numero de personas 1-100");
            personas=input.nextInt();
        }while (personas>personasMax || personas<0|| personas>100);

        ArrayList<Persona> personasCrear=new ArrayList<>();
        for(int z=0;z<personasMax;z++)personasCrear.add(new Persona(z,Estado.fuera));

        Jardin jar=new Jardin(puertasCrear,personasCrear,personasMax,personas);
        jar.start();

    }
}