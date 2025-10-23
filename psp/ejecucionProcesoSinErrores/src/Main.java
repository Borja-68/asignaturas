import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        ProcessBuilder pb=new ProcessBuilder();
        try {
            int exitCode;
            pb.command("cmd.exe", "/c", "dir");
            var process= pb.inheritIO().start();
            exitCode=process.waitFor();
            System.out.println();
            System.out.println(pb.directory());
            System.out.println("la ejecution de cmd.exe devuelve "+exitCode);
            System.out.println();

            pb.directory(new File("C:/Users/a24borjapp/Desktop/asignaturas"));

            Map<String,String> mapa= pb.environment();
            for(String key : mapa.keySet()){
                System.out.println(key +": "+ mapa.get(key));
            }
            System.out.println();
            process=pb.inheritIO().start();
            exitCode=process.waitFor( );
            System.out.println();
            System.out.println(pb.directory());
            System.out.println("la ejecution de cmd.exe devuelve "+exitCode);


        }catch (IOException e){
            System.out.println("ocurrio una excepcion de entrada y salida");
            System.exit(2);

        } catch (InterruptedException e) {
            System.out.println("proceso interrumpido");
            System.exit(3);
        }
    }
}