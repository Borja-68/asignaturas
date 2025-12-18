import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Filosofo filosofo1=new Filosofo(0);
        Filosofo filosofo2=new Filosofo(1);
        Filosofo filosofo3=new Filosofo(2);
        Filosofo filosofo4=new Filosofo(3);
        Filosofo filosofo5=new Filosofo(4);
        List<Filosofo> filosofos=new ArrayList<>(Arrays.asList(filosofo1,filosofo2,filosofo3,filosofo4,filosofo5));
        for (Filosofo filosofo:filosofos){
            Thread hilo=new Thread(filosofo);
            hilo.start();
        }

    }
}