//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        Contador cuenta=new Contador();
        for (int i=0; i<=1;i++){
            Thread hilo=new Thread(cuenta);
            hilo.start();
        }
    }
}