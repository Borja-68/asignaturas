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
        Random random = new Random();
        synchronized (this) {
            if (cubiertos[filosofo] && cubiertos[getCubierto(filosofo)]) {
                cubiertos[filosofo] = false;
                cubiertos[getCubierto(filosofo)] = false;
                for (Boolean valor:cubiertos){
                    System.out.println(valor);
                }
                System.out.println("Filosofo " + filosofo + ", comiendo");
                try {
                    Thread.sleep((random.nextInt(1000)));
                    System.out.println("Comí, Filosofo " + filosofo + ", pensando");
                    cubiertos[filosofo] = true;
                    cubiertos[getCubierto(filosofo)] = true;
                    Thread.sleep((1000 + random.nextInt(4000)));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        if(!cubiertos[filosofo] || !cubiertos[getCubierto(filosofo)]){
            System.out.println("no pudo comer Filosofo "+filosofo+", pensando");
            try {
                Thread.sleep((random.nextInt(400)));
            }
            catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
    }

    }

    private int getCubierto(int filosofo){
        if(((cubiertos.length-1)-((cubiertos.length-1)-filosofo))==0)return (cubiertos.length-1);
        else return (cubiertos.length-1)-((cubiertos.length-1)-filosofo);
    }
}
