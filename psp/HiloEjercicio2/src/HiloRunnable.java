import java.util.Scanner;

public class HiloRunnable implements Runnable{
   private String nombre;

   public HiloRunnable(String nombre){
       this.nombre=nombre;
   }

    @Override
    public void run() {
        Scanner sc=new Scanner(System.in);
        while (true){
            System.out.println("Hilo "+nombre+" en ejecución, si quiere salir introduzca 1");
            int numero=sc.nextInt();
            if(numero==1)return;
        }
    }
}
