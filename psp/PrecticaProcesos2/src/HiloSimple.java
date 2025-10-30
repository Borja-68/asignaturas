public class HiloSimple extends Thread{
    private String hilo;
    public HiloSimple(String hilo){
        this.hilo=hilo;
    }
    public void run(){
        for (int i=0; i<5;i++){
            System.out.println("en el hilo "+hilo);
        }
    }
}
