import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CreaPokemon {
   private static String sql=
           "CREATE SCHEMA objetos;\n" +
           "\n" +
           "-- Definir el tipo de datos personalizado \"Pokemon\"\n" +
           "CREATE TYPE objetos.Pokemon AS (\n" +
           "    nombre VARCHAR(255),\n" +
           "    tipo VARCHAR(50),\n" +
           "    nivel INT\n" +
           ");\n" +
           "\n" +
           "CREATE TABLE objetos.Pokemons (\n" +
           "    id serial PRIMARY KEY,\n" +
           "    pokemon objetos.Pokemon\n" +
           ");\n" +
           "\n" +
           "INSERT INTO objetos.Pokemons (pokemon)\n" +
           "VALUES (ROW('Pikachu', 'Eléctrico', 30));\n" +
           "\n" +
           "INSERT INTO objetos.Pokemons (pokemon)\n" +
           "VALUES (ROW('Charizard', 'Fuego/Volador', 50));\n";
   private static  String url="jdbc:postgresql://localhost:5432/";
   private static  String usuario="postgres";
   private static  String contrasena="abc123.";
   private static Connection conecxion;

   public static void creaPokemon() {
       try {
           conecxion = DriverManager.getConnection(url, usuario, contrasena);
           PreparedStatement sentenciaCreacion=conecxion.prepareStatement("CREATE DATABASE pokemons;\n" );
           sentenciaCreacion.executeUpdate();
           conecxion.close();
       }catch (SQLException e){
           System.out.println("Creacion fue mal");
       }
       try {
           conecxion = DriverManager.getConnection(url + "pokemons", usuario, contrasena);
           PreparedStatement sentenciaTabla=conecxion.prepareStatement(sql);
           sentenciaTabla.executeLargeUpdate();
           conecxion.close();
       }catch (SQLException e){
           System.out.println("Creacion tabla");
       }
   }
}
