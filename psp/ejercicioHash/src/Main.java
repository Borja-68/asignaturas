import java.security.Security;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        Set<String> algoritmos=Security.getAlgorithms("MessageDigest");
        System.out.println();
        for(String algoritmo: algoritmos){
            System.out.println(algoritmo);
        }
    }
}