import java.util.ArrayList;
import java.util.List;

public class Temporizador extends Thread {
    private List<Filosofo> filosofos=new ArrayList<>();
    private int tiempo=10000;

    public Temporizador(ArrayList<Filosofo> filosofos){
        super();
        this.filosofos=filosofos;
    }

    @Override
    public void run() {
        List<Thread> hilos=new ArrayList<>();
        for (Filosofo filosofo:filosofos){
            Thread hilo=new Thread(filosofo);
            hilo.start();
            hilos.add(hilo);
        }
        try {
            Thread.sleep(tiempo);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        for (Thread hilo:hilos){
                hilo.interrupt();
            }

    }
}
