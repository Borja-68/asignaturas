//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        HiloSimple hs1=new HiloSimple("hilo1");
        HiloSimple hs2=new HiloSimple("hilo2");
        HiloSimple hs3=new HiloSimple("hilo3");
        HiloSimple hs4=new HiloSimple("hilo4");
        hs1.start();
        hs2.start();
        hs3.start();
        hs4.start();
        for(int i=0; i<5;i++){
            System.out.println("fuera del hilo...");
        }
    }
}