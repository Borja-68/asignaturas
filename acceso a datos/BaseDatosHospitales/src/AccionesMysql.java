import java.sql.*;
import java.time.LocalDate;

public class AccionesMysql {
    static Connection conexionMysql = ConexionesDB.getConectionMysql();

    public static void nuevoPaciente(String nombre, String email, LocalDate fechaNacimiento) {
        try {
            Statement obenerMayorNumero = conexionMysql.createStatement();
            obenerMayorNumero.execute("select max(id_paciente)+1 from pacientes");
            ResultSet maxnum = obenerMayorNumero.getResultSet();
            int numero = -1;
            while (maxnum.next()) {
                numero = maxnum.getInt(1);
            }
            obenerMayorNumero.close();

            PreparedStatement sql = conexionMysql.prepareStatement("insert into pacientes values(?,?,?,?);");
            sql.setInt(1, numero);
            sql.setString(2, nombre);
            sql.setString(3, email);
            sql.setString(4, fechaNacimiento.toString());

            sql.executeUpdate();
            System.out.println("paciente creado");
        } catch (SQLException e) {
            System.out.println("sucedio un error, no se creo el paciente");
        }
    }

    public static void quitaPaciente(int numero) {
        try {
            PreparedStatement sql = conexionMysql.prepareStatement("delete from pacientes where id_paciente=?;");
            sql.setInt(1, numero);

            sql.executeUpdate();
            System.out.println("paciente borrado, si existia");
        } catch (SQLException e) {
            System.out.println("sucedio un error,paciente no borrado");
        }
    }

    public static void pacientesPorTratamiento(int cantidad) {
        try {

            PreparedStatement mySql = conexionMysql.prepareStatement("select  nombre_tratamiento,count(id_paciente) " +
                    "from pacientes_tratamientos join tratamientos on " +
                    "pacientes_tratamientos.id_tratamiento=tratamientos.id_tratamiento" +
                    " group by nombre_tratamiento having count(id_paciente)<=?;");

            mySql.setInt(1, cantidad);
            ResultSet resultado = mySql.executeQuery();

            while (resultado.next()) {
                System.out.println(resultado.getString(1) + " " + resultado.getInt(2));
            }

        } catch (SQLException e) {
            System.err.println("sucedio un error de consulta");

        } catch (NullPointerException e) {
            System.out.println("resultado vacio");
        }
    }

    public static void listaCitasPaciente() {
        try {

            Statement mysql = conexionMysql.createStatement();
            mysql.execute("select  nombre,count(id_cita) from citas join pacientes on" +
                    " pacientes.id_paciente=citas.id_paciente group by citas.id_paciente;");

            ResultSet resultado = mysql.getResultSet();
            while (resultado.next()) {
                System.out.println(resultado.getString(1) + " " + resultado.getInt(2));
            }

        } catch (SQLException e) {
            System.out.println("sucedio un error en la consulta");

        } catch (NullPointerException e) {
            System.out.println("resultado vacio");
        }
    }

    public static boolean anyPaciente() {
        try {
            Statement mysql = conexionMysql.createStatement();
            mysql.execute("select * from pacientes;");

            return emptyresult(mysql.getResultSet());

        } catch (SQLException e) {
            System.err.println("consulta no realizada, sucedio un error");
            return false;
        }

    }

    public static boolean pacienteExist(int id) {
        try {

            PreparedStatement postgre = conexionMysql.prepareStatement("select * from hospital.medicos where id_medico=?;");
            postgre.setInt(1, id);

            emptyresult(postgre.executeQuery());

        } catch (SQLException e) {
            System.out.println("error en la consulta de comprobacion");
            return false;
        }
        return false;
    }

    private static boolean emptyresult(ResultSet result){
        try{
            while (result.next()){
                return true;
            }
            return true;
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
            conexionMysql.close();
        } catch (SQLException e) {
            System.err.println("no se cerraron las bases");
        }
    }
}