import java.util.Scanner;

public class HiloThread extends Thread{
    private String nombre;
    public  HiloThread(String nombre){
        super();
        this.nombre=nombre;
    }

    public void run(){

        Scanner sc=new Scanner(System.in);
        while (true){

            System.out.println("Hilo "+nombre+" en ejecución, si quiere salir introduzca 0");
            int numero=sc.nextInt();
            sc.nextLine();
            if(numero==0) return;
        }
    }
}