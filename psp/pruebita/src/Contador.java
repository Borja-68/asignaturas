import java.util.Random;

class Contador implements Runnable{
    int cuenta;

    public Contador() {
        cuenta = 0;
    }
    public synchronized void incrementar(){
        cuenta=cuenta+1;
    }
    public synchronized int getCuenta(){
        return cuenta;
    }

    @Override
    public void run() {
            Random random=new Random();
           int vueltas=(random.nextInt(9))*100;
           for(int i=0; i<vueltas; i++){
               incrementar();
               System.out.println(getCuenta());
           }
    }
}
