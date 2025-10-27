import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class AccionesDB {
    private static Connection conexionMysql=ConexionesDB.getConectionMysql();;
    private static Connection conexionPostgre=ConexionesDB.getConectionPostgre();;
    public AccionesDB() {
    }

    public static void nuevaEspecialidad(String nombreEspecialidad) {
        try {
            Statement obenerMayorNumero = conexionPostgre.createStatement();
            obenerMayorNumero.execute("select max(id_especialidad)+1 from hospital.especialidades");
            ResultSet maxnum=obenerMayorNumero.getResultSet();
            int numero =-1;
            while (maxnum.next()) {
                 numero =maxnum.getInt(1);
            }
            obenerMayorNumero.close();

            PreparedStatement sql = conexionPostgre.prepareStatement("insert into hospital.especialidades values(?,?);");
            sql.setInt(1, numero);
            sql.setString(2, nombreEspecialidad);
            sql.executeUpdate();

            System.out.println("especialidad creada");
        } catch (SQLException e) {
            System.out.println("sucedio un error,especialidad no creada");
        }
    }

    public static void nuevoMedico(String nombreMedico, String nif, int telefono, String email) {
        try {
            Statement obenerMayorNumero = conexionPostgre.createStatement();
            obenerMayorNumero.execute("select max(id_medico)+1 from hospital.medicos");
            int numero = obenerMayorNumero.getResultSet().getInt(1);
            obenerMayorNumero.close();

            PreparedStatement sql = conexionPostgre.prepareStatement("insert into hospital.medicos values(?,?,ROW(?,?,?,?));");
            sql.setInt(1, numero);
            sql.setString(2, nombreMedico);
            sql.setString(3, nombreMedico);
            sql.setString(4, nif);
            sql.setString(5, "+34"+telefono);
            sql.setString(6, email);

            sql.executeUpdate();
            System.out.println("medico creado");
        } catch (SQLException e) {
            System.out.println("sucedio un error,medico no creado");
        }
    }

    public static void quitaMedico( int id) {
        try {
            PreparedStatement sql = conexionPostgre.prepareStatement("delete from hospital.medicos where id_medico=?;");
            sql.setInt(1, id);

            sql.executeUpdate();
            System.out.println("medico borrado, si existia");
        } catch (SQLException e) {
            System.out.println("sucedio un error,medico no borrado");
        }
    }

    public static void nuevoPaciente(String nombre, String email, LocalDate fechaNacimiento) {
        try {
            Statement obenerMayorNumero = conexionMysql.createStatement();
            obenerMayorNumero.execute("select max(id_paciente)+1 from pacientes");
            int numero = obenerMayorNumero.getResultSet().getInt(1);
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

    public static void creaTratamiento(String nombre, String descripcion, String nombreEspecialidad, String nifMedico) {
        try {
            conexionMysql.setAutoCommit(false);
            conexionPostgre.setAutoCommit(false);

            Statement obenerMayorNumeroMysql = conexionMysql.createStatement();
            obenerMayorNumeroMysql.execute("select max(id_tratamiento)+1 from tratamientos");
            int numeroMysql = obenerMayorNumeroMysql.getResultSet().getInt(1);
            obenerMayorNumeroMysql.close();

            PreparedStatement mySql = conexionMysql.prepareStatement("insert into tratamientos values(?,?,?);");
            mySql.setInt(1, numeroMysql);
            mySql.setString(2, nombre);
            mySql.setString(3, descripcion);
            mySql.executeUpdate();

            PreparedStatement postgre = conexionMysql.prepareStatement("insert into hospital.tratamientos " +
                    "values(?,(select id_medico from hospital.medicos where (contacto).nif=?)," +
                    "(select is_especialidad from hospital.especialidades where nombre_especialidad=?) );");

            mySql.setInt(1, numeroMysql);
            mySql.setString(2, nifMedico);
            mySql.setString(3, nombreEspecialidad);
            postgre.executeUpdate();

            System.out.println("sentencias ejecutadas con exito");
            conexionMysql.commit();
            conexionPostgre.commit();

        } catch (SQLException e) {
            System.err.println("operaciones no realizadas, sucedio un error en una de las consultas");

            try {
                conexionMysql.rollback();
                conexionPostgre.rollback();
            } catch (SQLException ex) {
                System.err.println("rollback fallado");
            }

        } finally {

            try {
                conexionMysql.setAutoCommit(true);
                conexionPostgre.setAutoCommit(true);
            } catch (SQLException e) {
                System.err.println("error en autocomit true");
            }
        }


    }


    public static void borraTratamiento(String nombre) {
        try {
            conexionMysql.setAutoCommit(false);
            conexionPostgre.setAutoCommit(false);

            PreparedStatement numTratamiento = conexionMysql.prepareStatement("select id_tratamiento from tratamientos where nombre_tratamiento=?;");
            numTratamiento.setString(1, nombre);

            int id_tratamieto = numTratamiento.getResultSet().getInt(1);

            PreparedStatement mySql = conexionMysql.prepareStatement("delete from tratamientos where id_tratamiento=? ;");
            mySql.setInt(1, id_tratamieto);
            mySql.executeUpdate();

            PreparedStatement postgre = conexionMysql.prepareStatement("delete from hospital.tratamientos where id_tratamiento=?;");
            postgre.setInt(1, id_tratamieto);
            postgre.executeUpdate();

            System.out.println("sentencias ejecutadas con exito");
            conexionMysql.commit();
            conexionPostgre.commit();

        } catch (SQLException e) {
            System.err.println("operaciones no realizadas, sucedio un error en una de las consultas");

            try {
                conexionMysql.rollback();
                conexionPostgre.rollback();
            } catch (SQLException ex) {
                System.err.println("rollback fallado");
            }

        } finally {

            try {
                conexionMysql.setAutoCommit(true);
                conexionPostgre.setAutoCommit(true);
            } catch (SQLException e) {
                System.err.println("error en autocomit true");
            }
        }

    }


    public static void pacientesPorTratamiento(int cantidad) {
        try {

            PreparedStatement mySql = conexionMysql.prepareStatement("select  nombre_tratamiento,count(id_paciente) " +
                    "from pacientes_tratamientos join tratamientos on " +
                    "pacientes_tratamientos.id_tratamiento=tratamientos.id_tratamiento" +
                    " group by nombre_tratamiento having count(id_paciente)<=?;");

            mySql.setInt(1, cantidad);
            ResultSet resultado=mySql.executeQuery();

            while (resultado.next()){
                System.out.println(resultado.getString(1)+" "+resultado.getInt(2));
            }

        } catch (SQLException e) {
            System.err.println("sucedio un error de consulta");

        }
        catch (NullPointerException e){
            System.out.println("resultado vacio");
        }
    }


    public static void listaCitasPaciente() {
        try {

            Statement mysql = conexionMysql.createStatement();
            mysql.execute("select  nombre,count(id_cita) from citas join pacientes on" +
                    " pacientes.id_paciente=citas.id_paciente group by citas.id_paciente;");

            ResultSet resultado= mysql.getResultSet();
            while (resultado.next()){
                System.out.println(resultado.getString(1)+" "+resultado.getInt(2));
            }

        } catch (SQLException e) {
            System.out.println("sucedio un error en la consulta");

        }
        catch (NullPointerException e){
            System.out.println("resultado vacio");
        }
    }

    public static void listaSalasTratamiento() {
        try {

            Statement postgre = conexionPostgre.createStatement();
            postgre.execute("select nombre_sala, count(id_tratamiento)" +
                    " from hospital.salas_tratamientos join hospital.salas " +
                    "on salas_tratamientos.id_sala=salas.id_sala" +
                    " group by nombre_sala;");

            ResultSet resultado= postgre.getResultSet();
            while (resultado.next()){
                System.out.println(resultado.getString(1)+" "+resultado.getInt(2));
            }
        } catch (SQLException e) {
            System.out.println("sucedio un error en la consulta");
        }
        catch (NullPointerException e){
            System.out.println("resultado vacio");
        }
    }



    public static void devueveDatosTratamiento() {
        try {
            Statement postgre = conexionPostgre.createStatement();
            postgre.execute("select * from hospital.tratamientos;");
            ResultSet resultadoPost=postgre.getResultSet();
            Statement mysql = conexionMysql.createStatement();
            mysql.execute("select nombre_tratamiento,descripcion from tratamientos;");
            ResultSet resultadoMy=mysql.getResultSet();

            while (resultadoMy.next() && resultadoPost.next()){
                System.out.println(resultadoPost.getInt(1)+ ", "+
                        resultadoMy.getString(1) + ", "+
                        resultadoMy.getString(2)+", "+
                        resultadoPost.getInt(2)+ ", "+
                        resultadoPost.getInt(3));
            }

        } catch (SQLException e) {
            System.err.println("sucedio un error en una de las consultas");

        }
        catch (NullPointerException e){
            System.out.println("resultado vacio");
        }
    }

    public static void obtenerPacientesPorEspecialidad(int especialidad){
        try {
            PreparedStatement postgre = conexionPostgre.prepareStatement("select id_tratamiento from hospital.tratamientos" +
                    " where id_especialidad=?;");
            postgre.setInt(1,especialidad);
            postgre.execute();
            ResultSet resultadoPostgre=postgre.getResultSet();

            while (resultadoPostgre.next()) {
                PreparedStatement mysql = conexionMysql.prepareStatement("select nombre from pacientes_tratamientos join pacientes on" +
                        " pacientes_tratamientos.id_paciente=pacientes.id_paciente" +
                        " where id_tratamiento=?;");
                mysql.setInt(1, resultadoPostgre.getInt(1));

                ResultSet resultadoMy = mysql.executeQuery();
                while (resultadoMy.next()) {
                    System.out.println(resultadoMy.getString(1));
                }
            }

        } catch (SQLException e) {
            System.out.println("sucedio un error en una de las consultas");
        }
        catch (NullPointerException e){
            System.out.println("resultado vacio");
        }
    }








    public static boolean anyMedicExists(){
        try {
            Statement postgre=conexionPostgre.createStatement();
            postgre.execute(  "select * from hospital.medicos;");

            return emptyresult(postgre.getResultSet());

        } catch (SQLException e) {
            System.err.println("consulta no realizada, sucedio un error");
            return false;
        }

    }


    public static boolean medicExist(int id){
        try {

            PreparedStatement postgre = conexionPostgre.prepareStatement("select * from hospital.medicos where id_medico=?;");
            postgre.setInt(1, id);

            return emptyresult(postgre.getResultSet());

        } catch (SQLException e) {
            System.out.println("error en la consulta de comprobacion");
            return false;
        }
    }

    private static boolean emptyresult(ResultSet result){
        try{
            while (result.next()){
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

    public static void close(){
        try {
            conexionPostgre.close();
            conexionMysql.close();
        } catch (SQLException e) {
            System.err.println("no se cerraron las bases");
        }
    }

}

