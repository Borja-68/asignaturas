import java.sql.*;

public class AccionMixta {
    static Connection conexionMysql = ConexionesDB.getConectionMysql();
    private static Connection conexionPostgre=ConexionesDB.getConectionPostgre();;

    public static void creaTratamiento(String nombre, String descripcion, String nombreEspecialidad, String nifMedico) {
        try {
            AccionesMysql.conexionMysql.setAutoCommit(false);
            conexionPostgre.setAutoCommit(false);

            Statement obenerMayorNumeroMysql = AccionesMysql.conexionMysql.createStatement();
            obenerMayorNumeroMysql.execute("select max(id_tratamiento)+1 from tratamientos");
            ResultSet maxnum = obenerMayorNumeroMysql.getResultSet();
            int numero = -1;
            while (maxnum.next()) {
                numero = maxnum.getInt(1);
            }
            obenerMayorNumeroMysql.close();

            PreparedStatement mySql = AccionesMysql.conexionMysql.prepareStatement("insert into tratamientos values(?,?,?);");
            mySql.setInt(1, numero);
            mySql.setString(2, nombre);
            mySql.setString(3, descripcion);
            mySql.executeUpdate();

            PreparedStatement postgre = conexionPostgre.prepareStatement("insert into hospital.tratamientos " +
                    "values(?,(select id_medico from hospital.medicos where (contacto).nif=?)," +
                    "(select id_especialidad from hospital.especialidades where nombre_especialidad=?) );");

            postgre.setInt(1, numero);
            postgre.setString(2, nifMedico);
            postgre.setString(3, nombreEspecialidad);
            postgre.executeUpdate();

            System.out.println("sentencias ejecutadas con exito");
            AccionesMysql.conexionMysql.commit();
            conexionPostgre.commit();

        } catch (SQLException e) {
            System.err.println("operaciones no realizadas, sucedio un error en una de las consultas");

            try {
                AccionesMysql.conexionMysql.rollback();
                conexionPostgre.rollback();
            } catch (SQLException ex) {
                System.err.println("rollback fallado");
            }

        } finally {

            try {
                AccionesMysql.conexionMysql.setAutoCommit(true);
                conexionPostgre.setAutoCommit(true);
            } catch (SQLException e) {
                System.err.println("error en autocomit true");
            }
        }


    }

    public static void borraTratamiento(String nombre) {
        try {
            AccionesMysql.conexionMysql.setAutoCommit(false);
            conexionPostgre.setAutoCommit(false);

            PreparedStatement numTratamiento = AccionesMysql.conexionMysql.prepareStatement("select id_tratamiento from tratamientos where nombre_tratamiento=?;");
            numTratamiento.setString(1, nombre);

            ResultSet numeroSelec = numTratamiento.executeQuery();
            int id_tratamieto = -1;
            while (numeroSelec.next()) {
                id_tratamieto = numeroSelec.getInt(1);
            }

            PreparedStatement mySql = AccionesMysql.conexionMysql.prepareStatement("delete from tratamientos where id_tratamiento=? ;");
            mySql.setInt(1, id_tratamieto);
            mySql.executeUpdate();

            PreparedStatement postgre = conexionPostgre.prepareStatement("delete from hospital.tratamientos where id_tratamiento=?;");
            postgre.setInt(1, id_tratamieto);
            postgre.executeUpdate();

            System.out.println("sentencias ejecutadas con exito");
            AccionesMysql.conexionMysql.commit();
            conexionPostgre.commit();

        } catch (SQLException e) {
            System.err.println("operaciones no realizadas, sucedio un error en una de las consultas");

            try {
                AccionesMysql.conexionMysql.rollback();
                conexionPostgre.rollback();
            } catch (SQLException ex) {
                System.err.println("rollback fallado");
            }

        } finally {

            try {
                AccionesMysql.conexionMysql.setAutoCommit(true);
                conexionPostgre.setAutoCommit(true);
            } catch (SQLException e) {
                System.err.println("error en autocomit true");
            }
        }

    }

    public static void devueveDatosTratamiento() {
        try {
            Statement postgre = conexionPostgre.createStatement();
            postgre.execute("select * from hospital.tratamientos;");
            ResultSet resultadoPost = postgre.getResultSet();
            Statement mysql = AccionesMysql.conexionMysql.createStatement();
            mysql.execute("select nombre_tratamiento,descripcion from tratamientos;");
            ResultSet resultadoMy = mysql.getResultSet();

            while (resultadoMy.next() && resultadoPost.next()) {
                System.out.println(resultadoPost.getInt(1) + ", " +
                        resultadoMy.getString(1) + ", " +
                        resultadoMy.getString(2) + ", " +
                        resultadoPost.getInt(2) + ", " +
                        resultadoPost.getInt(3));
            }

        } catch (SQLException e) {
            System.err.println("sucedio un error en una de las consultas");

        } catch (NullPointerException e) {
            System.out.println("resultado vacio");
        }
    }

    public static void obtenerPacientesPorEspecialidad(int especialidad) {
        try {
            PreparedStatement postgre = conexionPostgre.prepareStatement("select id_tratamiento from hospital.tratamientos" +
                    " where id_especialidad=?;");
            postgre.setInt(1, especialidad);
            postgre.execute();
            ResultSet resultadoPostgre = postgre.getResultSet();

            while (resultadoPostgre.next()) {
                PreparedStatement mysql = AccionesMysql.conexionMysql.prepareStatement("select nombre from pacientes_tratamientos join pacientes on" +
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
        } catch (NullPointerException e) {
            System.out.println("resultado vacio");
        }
    }
    public static void close() {
        try {
            conexionMysql.close();
            conexionPostgre.close();
        } catch (SQLException e) {
            System.err.println("no se cerraron las bases");
        }
    }
}