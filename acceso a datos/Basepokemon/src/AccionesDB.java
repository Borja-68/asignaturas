import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class AccionesDB {
    public AccionesDB(){}

    public static int Insercion(Connection conexion, Scanner input){
        String nombre="";
        String tipo="";
        Integer nivel=-1;
        do{
            System.out.println("introduce el nombre del pokemon");
            nombre=input.next();
            input.nextLine();
        }while(nombre.isEmpty());

        do{
            System.out.println("introduce el tipo/os del pokemon");
            tipo=input.next();
            input.nextLine();
        }while(tipo.isEmpty());

        do{
            try {
                System.out.println("introduce el nivel del pokemon, numero");
                nivel = input.nextInt();
                input.nextLine();
            }catch (InputMismatchException e){
                System.out.println("no se introdujo un numero");
            }
        }while(nivel<=0 || nivel>100);
    try{
        PreparedStatement sentencia= conexion.prepareStatement("INSERT INTO objetos.Pokemons (pokemon) VALUES (ROW(?,?,?);");
        sentencia.setString(1,nombre);
        sentencia.setString(2,tipo);
        sentencia.setInt(3,nivel);
    }catch (SQLException e){
        System.out.println("insercion fallida");
    }
    return 0;
    }
}
