import java.util.ArrayList;

public class Jardin extends Thread {

    public static int personas;
    public static int personasMax;
    public static ArrayList<Boolean>puertas=new ArrayList<>();
    public ArrayList<Persona> listaPersonas= new ArrayList<>();
    public static int fallos=0;

public Jardin (ArrayList<Boolean> puertas,ArrayList<Persona> listaPersonas,int personasMax,int personas){
    super.run();
    this.listaPersonas=listaPersonas;
    this.personas=personas;
    this.puertas=puertas;
    this.personasMax=personasMax;
}
    @Override
    public void run() {
    for (Persona persona: listaPersonas){
        persona.start();
    }
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {}

        for (Persona persona: listaPersonas){
           persona.interrupt();
        }
        System.out.println("intentos fallidos: "+fallos);
    }

    public static void setXpuertaFalse(int i){
    puertas.set(i,false);
    }

    public static void setXpuertaTrue(int i){
        puertas.set(i,true);
    }
    public static void addFallos(){
      fallos++;
    }
}
