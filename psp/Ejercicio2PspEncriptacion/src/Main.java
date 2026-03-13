import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Set<String> algoritmos=Security.getAlgorithms("MessageDigest");
        System.out.println();
        int i=0;
        Map<String,String> posibles=new HashMap<>();
        for(String algoritmo: algoritmos){
            posibles.put(i+"",algoritmo);
            i++;
        }
        List<String> keys=posibles.keySet().stream().toList();
        int x=0;
        for(String cosa: posibles.values()){
            System.out.println(keys.get(x) +" "+cosa);
            x++;
        }
        Scanner in=new Scanner(System.in);
        String metodo="";
        do{
            String opcion=in.next();
            metodo=posibles.get(opcion);
        }while(metodo=="");

        String caden="puerro";

        try{
            byte[] bytes=caden.getBytes();
            MessageDigest md= MessageDigest.getInstance(metodo);
            md.update(bytes);
            byte[] resultado=md.digest();

            System.out.println("cadena normal "+caden+"  resultado "+resultado);
        } catch (NoSuchAlgorithmException e) {
        }
    }
}