
public class HiloThread extends Thread{
    private String nombre;

    public  HiloThread(String nombre){
        super();
        this.nombre=nombre;
    }

    public void run(){
            System.out.print("Hilo ");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(nombre+ " acaba");
    }
}
