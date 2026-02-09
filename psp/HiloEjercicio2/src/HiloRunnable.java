
public class HiloRunnable implements Runnable{
   private String nombre;

   public HiloRunnable(String nombre){
       this.nombre=nombre;
   }

    @Override
    public void run() {
            System.out.print("Hilo ");
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(nombre+" acaba");
    }
}
