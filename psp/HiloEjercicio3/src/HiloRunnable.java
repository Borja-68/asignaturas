
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
            System.out.print("Hilo ");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(nombre+" acaba");
    }

}

