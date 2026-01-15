import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Filosofo implements Runnable {
    public static Boolean[] cubiertos= {true,true,true,true,true};
    private int filosofo;
    private int tiempoEspera_1=1000;
    private int tiempoEspera_2=4000;
    private int tiempoEspera_3=400;
    private int tiempoExtraEspera=1000;
    public boolean salir=false;

    private int come=0;
    private int noCome=0;

    public Filosofo(int filosofo){
        this.filosofo=filosofo;
    }

    @Override
    public void run() {
        try {
            while (!salir) {
                Random random = new Random();
                synchronized (this) {
                    if (cubiertos[filosofo] && cubiertos[getCubierto(filosofo)]) {
                        cubiertos[filosofo] = false;
                        cubiertos[getCubierto(filosofo)] = false;

                        try {
                            int espera_1 = random.nextInt(tiempoEspera_1);
                            System.out.println("Filosofo " + filosofo + ", comiendo durante " + espera_1 + " mseg");
                            come++;
                            Thread.sleep(espera_1);

                            int espera_2 = tiempoExtraEspera + random.nextInt(tiempoEspera_2);
                            System.out.println("Comí, Filosofo " + filosofo + ", pensando durante " + espera_2 + " mseg");
                            cubiertos[filosofo] = true;
                            cubiertos[getCubierto(filosofo)] = true;
                            Thread.sleep(espera_2);

                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
                if (!cubiertos[filosofo] || !cubiertos[getCubierto(filosofo)]) {


                    try {
                        int espera_3 = random.nextInt(tiempoEspera_3);
                        System.out.println("no pudo comer Filosofo " + filosofo + ", pensando durante " + espera_3 + " mseg");
                        noCome++;

                        Thread.sleep(espera_3);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                }
            }
        }catch (Exception e) {
            System.out.println("Filosofo " + filosofo + "  no comió: " + noCome + "   comió: " + come);
        }
    }

    private int getCubierto(int filosofo){
        if(((cubiertos.length-1)-((cubiertos.length-1)-filosofo+1))==-1)return 0;
        else return (cubiertos.length-1)-((cubiertos.length-1)-filosofo+1);
    }
}
