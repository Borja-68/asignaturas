import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class AccionesDB {
    public AccionesDB(){}

    public static void nuevaEspecialidad(Connection conexionPostgre, Scanner input){
        try{
            Statement obenerMayorNumero=conexionPostgre.createStatement();
            obenerMayorNumero.execute("select max(id_especialidad)+1 from hospital.especialidades");
            int numero=obenerMayorNumero.getResultSet().getInt(1);
            obenerMayorNumero.close();

            System.out.println("introduzca el nombre de la especialidad");
            String nombreEspecialidad= getNormalString(input);

            PreparedStatement sql=conexionPostgre.prepareStatement("insert into hospital.especialidades values(?,?);");
            sql.setInt(1,numero);
            sql.setString(2,nombreEspecialidad);
            sql.executeUpdate();

            System.out.println("especialidad creada");
        } catch (SQLException e) {
            System.out.println("sucedio un error,especialidad no creada");
        }
    }

    public static void nuevoMedico(Connection conexionPostgre, Scanner input){
        try{
            Statement obenerMayorNumero=conexionPostgre.createStatement();
            obenerMayorNumero.execute("select max(id_medico)+1 from hospital.medicos");
            int numero=obenerMayorNumero.getResultSet().getInt(1);
            obenerMayorNumero.close();

            System.out.println("introduzca el nombre del medico");
            String nombreMedico= getNormalString(input);

            Contacto contacto=creaContacto(input);

            PreparedStatement sql=conexionPostgre.prepareStatement("insert into hospital.medicos values(?,?,ROW(?,?,?,?));");
            sql.setInt(1,numero);
            sql.setString(2,nombreMedico);
            sql.setString(3,contacto.getNombreContacto());
            sql.setString(4,contacto.getNif());
            sql.setString(5,contacto.getTelefono());
            sql.setString(6,contacto.getCorreo());

            sql.executeUpdate();
            System.out.println("medico creado");
        } catch (SQLException e) {
            System.out.println("sucedio un error,medico no creado");
        }
    }

    public static void quitaMedico(Connection conexionPostgre, Scanner input){
        try{
            int numero=-1;
            do {
                try {
                    System.out.println("introduzca la id del medico que quiere eliminar");
                    numero = input.nextInt();
                    input.nextLine();

                }catch (InputMismatchException e){
                    System.out.println("lo introducido no fue un numero");
                    input.nextLine();
                }
            }while(numero<=0);

            PreparedStatement sql=conexionPostgre.prepareStatement("delete from hospital.medicos where id_medico=?;");
            sql.setInt(1,numero);

            sql.executeUpdate();
            System.out.println("medico borrado, si existia");
        } catch (SQLException e) {
            System.out.println("sucedio un error,medico no borrado");
        }
    }

    public static void nuevoPaciente(Connection conexionMysql, Scanner input){
       try {
           Statement obenerMayorNumero = conexionMysql.createStatement();
           obenerMayorNumero.execute("select max(id_paciente)+1 from pacientes");
           int numero = obenerMayorNumero.getResultSet().getInt(1);
           obenerMayorNumero.close();

           System.out.println("introduce el nombre");
           String nombre= getNormalString(input);

           System.out.println("introduce el correo, sin @gmail.com");
           String email=getNormalString(input);
           if(!email.contains("@gmail.com"))email=email+"@gmail.com";

           String fecha=getFecha(input);

           PreparedStatement sql=conexionMysql.prepareStatement("insert into pacientes values(?,?,?,?);");
           sql.setInt(1,numero);
           sql.setString(2,nombre);
           sql.setString(3,email);
           sql.setString(4,fecha);

           sql.executeUpdate();
           System.out.println("paciente creado");
       }catch (SQLException e){
           System.out.println("sucedio un error, no se creo el paciente");
       }
    }


    public static void quitaPaciente(Connection conexionMysql, Scanner input){
        try{
            int numero=-1;
            do {
                try {
                    System.out.println("introduzca la id del medico que quiere eliminar");
                    numero = input.nextInt();
                    input.nextLine();

                }catch (InputMismatchException e){
                    System.out.println("lo introducido no fue un numero");
                    input.nextLine();
                }
            }while(numero<=0);

            PreparedStatement sql=conexionMysql.prepareStatement("delete from pacientes where id_paciente=?;");
            sql.setInt(1,numero);

            sql.executeUpdate();
            System.out.println("paciente borrado, si existia");
        } catch (SQLException e) {
            System.out.println("sucedio un error,paciente no borrado");
        }
    }

    public static void creaTratamiento(Connection conexionMysql,Connection conexionPostgre,Scanner input){
       try {
           conexionMysql.setAutoCommit(false);
           conexionPostgre.setAutoCommit(false);

           Statement obenerMayorNumeroMysql = conexionMysql.createStatement();
           obenerMayorNumeroMysql.execute("select max(id_tratamiento)+1 from tratamientos");
           int numeroMysql = obenerMayorNumeroMysql.getResultSet().getInt(1);
           obenerMayorNumeroMysql.close();

           Statement obenerMayorNumeroPostgre = conexionPostgre.createStatement();
           obenerMayorNumeroPostgre.execute("select max(id_tratamiento)+1 from hospital.tratamientos");
           int numeroPostgre = obenerMayorNumeroPostgre.getResultSet().getInt(1);
           obenerMayorNumeroPostgre.close();

           System.out.println("introduce nombre tratamiento");
           String nombre = getNormalString(input);

           System.out.println("introduce la descripcion tratamiento");
           String descripcion = getNormalString(input);

           System.out.println("introduzca la nombre de la especialidad");
           String nombreEspecialidad = getNormalString(input);

           String nif="";
           do{
               System.out.println("inserta el nif");
               nif=input.next();
               input.nextLine();
           }while(!Contacto.nifCorrecto(nif));


           PreparedStatement mySql=conexionMysql.prepareStatement("insert into tratamientos values(?,?,?);");
           mySql.setInt(1,numeroMysql);
           mySql.setString(2,nombre);
           mySql.setString(3,descripcion);
           mySql.executeUpdate();

           PreparedStatement postgre=conexionMysql.prepareStatement("insert into hospital.tratamientos " +
                   "values(?,(select id_medico from hospital.medicos where (contacto).nif=?)," +
                   "(select is_especialidad from hospital.especialidades where nombre_especialidad=?) );");

           mySql.setInt(1,numeroPostgre);
           mySql.setString(2,nif);
           mySql.setString(3,nombreEspecialidad);
           postgre.executeUpdate();

           System.out.println("sentencias ejecutadas con exito");
           conexionMysql.commit();
           conexionPostgre.commit();

       }catch (SQLException e) {
           System.err.println("operaciones no realizadas, sucedio un error en una de las consultas");

           try {
               conexionMysql.rollback();
               conexionPostgre.rollback();
           }
           catch (SQLException ex) {
               System.err.println("rollback fallado");
           }

       }
       finally {

           try {
               conexionMysql.setAutoCommit(true);
               conexionPostgre.setAutoCommit(true);
           }
           catch (SQLException e) {
               System.err.println("error en autocomit true");
           }
       }


    }



    public static void borraTratamiento(Connection conexionMysql,Connection conexionPostgre,Scanner input){
        try {
            conexionMysql.setAutoCommit(false);
            conexionPostgre.setAutoCommit(false);

            System.out.println("introduce el nombre del tratamiento");
            String nombre = getNormalString(input);

            PreparedStatement numTratamiento=conexionMysql.prepareStatement("select id_tratamiento from tratamientos where nombre_tratamiento=?;");
            numTratamiento.setString(1,nombre);

            int id_tratamieto= numTratamiento.getResultSet().getInt(1);

            PreparedStatement mySql=conexionMysql.prepareStatement("delete from tratamientos where id_tratamiento=? ;");
            mySql.setInt(1,id_tratamieto);
            mySql.executeUpdate();

            PreparedStatement postgre=conexionMysql.prepareStatement("delete from hospital.tratamientos where id_tratamiento=?;");
            postgre.setInt(1,id_tratamieto);
            postgre.executeUpdate();

            System.out.println("sentencias ejecutadas con exito");
            conexionMysql.commit();
            conexionPostgre.commit();

        }catch (SQLException e) {
            System.err.println("operaciones no realizadas, sucedio un error en una de las consultas");

            try {
                conexionMysql.rollback();
                conexionPostgre.rollback();
            }
            catch (SQLException ex) {
                System.err.println("rollback fallado");
            }

        }
        finally {

            try {
                conexionMysql.setAutoCommit(true);
                conexionPostgre.setAutoCommit(true);
            }
            catch (SQLException e) {
                System.err.println("error en autocomit true");
            }
        }

    }



    public static ResultSet pucientesPorTratamiento(Connection conexionMysql,Scanner input){
        try{
            int maxPacientes = -1;
            do {
                try {
                    System.out.println("introduzca la cantidad de pacientes maxima");
                    maxPacientes = input.nextInt();
                    input.nextLine();

                } catch (InputMismatchException e) {
                    System.out.println("lo introducido no fue un numero");
                    input.nextLine();
                }

            }while(maxPacientes<=0);

            PreparedStatement mySql=conexionMysql.prepareStatement("select count(id_paciente), nombre_tratamiento " +
                                                                        "from pacientes_tratamientos join tratamientos on " +
                                                                        "pacientes_tratamientos.id_tratamiento=tratamientos.id_tratamiento" +
                                                                        " group by nombre_tratamiento having count(id_paciente)<=?;");

            mySql.setInt(1,maxPacientes);
            mySql.execute();
            return mySql.getResultSet();

        }catch (SQLException e){
            System.err.println("sucedio un error de consulta");
            return null;
        }
    }

    public static ResultSet listaCitasPaciente(Connection conexionMysql){
        try{

            Statement mysql=conexionMysql.createStatement();
            mysql.execute("select count(id_cita), id_paciente " +
                              "from citas group by id_paciente;");

            return mysql.getResultSet();

        }catch (SQLException e){
            System.out.println("sucedio un error en la consulta");
            return null;
        }
    }

    public static ResultSet listaSalasTratamiento(Connection conexionPostgre){
        try{

            Statement postgre=conexionPostgre.createStatement();
            postgre.execute("select count(id_sala),id_tratamiento" +
                    " from hospital.salas_tratamientos group by id_tratamiento;");

            return postgre.getResultSet();

        }catch (SQLException e){
            System.out.println("sucedio un error en la consulta");
            return null;
        }
    }

    private static Contacto creaContacto(Scanner input){
        System.out.println("inserta nombre del contacto");
        String nombreContacto= getNormalString(input);

        String nif="";
        do{
            System.out.println("inserta el nif");
            nif=input.next();
            input.nextLine();
        }while(!Contacto.nifCorrecto(nif));

        String telefono="";
        do{
            System.out.println("inserta el telefono, sin el +34");
            telefono=input.next();
            input.nextLine();
        }while(!Contacto.telefonoCorrecto(telefono));

        System.out.println("inserta el correo sin @gmail.com");
        String correo= getNormalString(input);

        return new Contacto(nombreContacto,nif,telefono,correo);
    }







    private static String getNormalString(Scanner input){
        String cadena="";
        do{
            System.out.println("introduce la cadena menor de 100 caracteres");
            cadena=input.next();
            input.nextLine();
        }while(cadena.length()>100);
        return cadena;
    }



    private static String getFecha(Scanner input){
        int anio=-1;
        int mes=-1;
        int dia=-1;
        do {
            try {
            System.out.println("introduce el año entre 1925 y 2025");
            anio=input.nextInt();
            input.nextLine();

            }catch (InputMismatchException e){
                System.out.println("no se introdujo un numero");
                input.nextLine();
            }
        }while (anio<1925 || anio>2025);

        do {
            try {
                System.out.println("introduce el mes entre 1 y 12");
                mes=input.nextInt();
                input.nextLine();

            }catch (InputMismatchException e){
                System.out.println("no se introdujo un numero");
                input.nextLine();
            }
        }while (mes<1 || mes>12);

        int maximoDias=-1;
        switch (mes){
            case 1,3,5,7,8,10,12:maximoDias=31; break;
            case 2:maximoDias=28; break;
            case 4,6,9,11:maximoDias=28; break;
        }

        do{
            try{
                System.out.println("introduce el dia entre 1 y "+maximoDias);
                dia=input.nextInt();
                input.nextLine();

            }catch (InputMismatchException e){
                System.out.println("no se introdujo un numero");
                input.nextLine();
            }
        }while (dia<1 || dia>maximoDias);
        return anio+"-"+mes+"-"+dia;
    }

}

