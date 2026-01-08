//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        HiloThread hilo1=new HiloThread("1");
        HiloRunnable hilRun=new HiloRunnable("2",hilo1);
        Thread hilo2=new Thread(hilRun);

        hilo2.start();
    }
}