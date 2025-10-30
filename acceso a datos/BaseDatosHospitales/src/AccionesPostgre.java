import java.sql.*;

public class AccionesPostgre {
    private static Connection conexionPostgre=ConexionesDB.getConectionPostgre();;
    public static void crearEspecialidad(String nombreEspecialidad) {
        try {
            Statement obenerMayorNumero = conexionPostgre.createStatement();
            obenerMayorNumero.execute("select max(id_especialidad)+1 from hospital.especialidades");
            ResultSet maxnum = obenerMayorNumero.getResultSet();
            int numero = -1;
            while (maxnum.next()) {
                numero = maxnum.getInt(1);
            }
            obenerMayorNumero.close();

            PreparedStatement sql =conexionPostgre.prepareStatement("insert into hospital.especialidades values(?,?);");
            sql.setInt(1, numero);
            sql.setString(2, nombreEspecialidad);
            sql.executeUpdate();

            System.out.println("especialidad creada");
        } catch (SQLException e) {
            System.out.println("sucedio un error,especialidad no creada");
        }
    }

    public static void crearMedico(String nombreMedico, String nif, int telefono, String email) {
        try {
            Statement obenerMayorNumero = conexionPostgre.createStatement();
            obenerMayorNumero.execute("select max(id_medico)+1 from hospital.medicos");
            ResultSet maxnum = obenerMayorNumero.getResultSet();
            int numero = -1;
            while (maxnum.next()) {
                numero = maxnum.getInt(1);
            }
            obenerMayorNumero.close();

            PreparedStatement sql = conexionPostgre.prepareStatement("insert into hospital.medicos values(?,?,ROW(?,?,?,?));");
            sql.setInt(1, numero);
            sql.setString(2, nombreMedico);
            sql.setString(3, nombreMedico);
            sql.setString(4, nif);
            sql.setString(5, Integer.toString(telefono));
            sql.setString(6, email);

            sql.executeUpdate();
            System.out.println("medico creado");
        } catch (SQLException e) {
            System.out.println("sucedio un error,medico no creado");
        }
    }

    public static void eliminarMedico(int id) {
        try {
            PreparedStatement sql = conexionPostgre.prepareStatement("delete from hospital.medicos where id_medico=?;");
            sql.setInt(1, id);

            sql.executeUpdate();
            System.out.println("medico borrado, si existia");
        } catch (SQLException e) {
            System.out.println("sucedio un error,medico no borrado");
        }
    }

    public static void obtenerCantidadTratamientosPorSala() {
        try {

            Statement postgre = conexionPostgre.createStatement();
            postgre.execute("select nombre_sala, count(id_tratamiento)" +
                    " from hospital.salas_tratamientos join hospital.salas " +
                    "on salas_tratamientos.id_sala=salas.id_sala" +
                    " group by nombre_sala;");

            ResultSet resultado = postgre.getResultSet();
            while (resultado.next()) {
                System.out.println(resultado.getString(1) + " " + resultado.getInt(2));
            }
        } catch (SQLException e) {
            System.out.println("sucedio un error en la consulta");
        } catch (NullPointerException e) {
            System.out.println("resultado vacio");
        }
    }

    public static boolean anyMedicExists() {
        try {
            Statement postgre = conexionPostgre.createStatement();
            postgre.execute("select * from hospital.medicos;");

            return emptyresult(postgre.getResultSet());

        } catch (SQLException e) {
            System.err.println("consulta no realizada, sucedio un error");
            return false;
        }

    }

    public static void listaNifMedicos() {
        try {
            Statement postgre = conexionPostgre.createStatement();
            postgre.execute("select (contacto).nif from hospital.medicos;");
            ResultSet resultado= postgre.getResultSet();
            while (resultado.next()){
                System.out.println(resultado.getString(1));
            }

        } catch (SQLException e) {
            System.err.println("consulta no realizada, sucedio un error");
        }
        catch (NullPointerException e){
            System.out.println("no existen medicos");
        }
    }

    public static void listaIdMedicos() {
        try {
            Statement postgre = conexionPostgre.createStatement();
            postgre.execute("select id_medico from hospital.medicos;");
            ResultSet resultado= postgre.getResultSet();
            while (resultado.next()){
                System.out.println(resultado.getString(1));
            }

        } catch (SQLException e) {
            System.err.println("consulta no realizada, sucedio un error");
        }
        catch (NullPointerException e){
            System.out.println("no existen medicos");
        }
    }

    public static void listaNombreEspecialidades() {
        try {
            Statement postgre = conexionPostgre.createStatement();
            postgre.execute("select nombre_especialidad from hospital.especialidades;");
            ResultSet resultado= postgre.getResultSet();
            while (resultado.next()){
                System.out.println(resultado.getString(1));
            }

        } catch (SQLException e) {
            System.err.println("consulta no realizada, sucedio un error");
        }
        catch (NullPointerException e){
            System.out.println("no existen especialidades");
        }
    }

    public static void listaIdEspecialidades() {
        try {
            Statement postgre = conexionPostgre.createStatement();
            postgre.execute("select id_especialidad from hospital.especialidades;");
            ResultSet resultado= postgre.getResultSet();
            while (resultado.next()){
                System.out.println(resultado.getInt(1));
            }

        } catch (SQLException e) {
            System.err.println("consulta no realizada, sucedio un error");
        }
        catch (NullPointerException e){
            System.out.println("no existen especialidades");
        }
    }

    public static boolean medicExist(int id) {
        try {

            PreparedStatement postgre = conexionPostgre.prepareStatement("select * from hospital.medicos where id_medico=?;");
            postgre.setInt(1, id);

            return emptyresult(postgre.executeQuery());

        } catch (SQLException e) {
            System.out.println("error en la consulta de comprobacion");
            return false;
        }

    }

    private static boolean emptyresult(ResultSet result){
        try{
            while (result.next()){
                return true;
            }
            return  false;
        } catch (SQLException e) {
            return false;
        }
        catch (NullPointerException e){
            System.out.println("cadena vacia");
            return false;
        }
    }
    public static void close() {
        try {
            conexionPostgre.close();
        } catch (SQLException e) {
            System.err.println("no se cerraron las bases");
        }
    }
}