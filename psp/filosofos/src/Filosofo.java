import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Filosofo implements Runnable {
    public static Boolean[] cubiertos= {true,true,true,true,true};
    private int filosofo;

    public Filosofo(int filosofo){
        this.filosofo=filosofo;
    }

    @Override
    public void run() {
    while(true){
        synchronized (this) {
            if (cubiertos[filosofo] && cubiertos[filosofo - 1]) {
                cubiertos[filosofo] = false;
                cubiertos[filosofo - 1] = false;
                System.out.println("Filosofo " + filosofo + ", comiendo");
                Random random = new Random();
                try {
                    System.out.println("Comí, Filosofo " + filosofo + ", pensando");
                    Thread.sleep((random.nextInt(400)));
                    cubiertos[filosofo] = true;
                    cubiertos[filosofo - 1] = true;
                    Thread.sleep((1000 + random.nextInt(4000)));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        if(!cubiertos[filosofo] || !cubiertos[filosofo - 1]) System.out.println("no pudo comer Filosofo "+filosofo+", pensando");
    }

    }
}
