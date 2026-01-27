import java.util.ArrayList;

public class Jardin extends Thread {

    public static int personas= 95;
    public static boolean puertaEntrada=true;
    public static boolean puertaSalida=true;
    public ArrayList<Persona> listaPersonas= new ArrayList<>();

public Jardin (){
    super.run();
}
    @Override
    public void run() {
    for (Persona persona: listaPersonas){
        persona.start();
    }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        for (Persona persona: listaPersonas){
            persona.setFuncionandoFalse();
        }
    }
}
