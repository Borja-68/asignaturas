import java.util.Scanner;

public class HiloRunnable implements Runnable{
    private String nombre;
    private Thread hilo;


    public HiloRunnable(String nombre,Thread hilo){
        this.nombre=nombre;
        this.hilo=hilo;

    }

    @Override
    public void run() {
        try {
            hilo.start();
            hilo.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Scanner sc=new Scanner(System.in);
        while (true){
            System.out.println("Hilo "+nombre+" en ejecución, si quiere salir introduzca 1");
            int numero=sc.nextInt();
            sc.nextLine();
            if(numero==1){
                sc.close();
                return;
            }
        }

    }
}
