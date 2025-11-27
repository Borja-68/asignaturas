import java.sql.*;

public class AccionesPostgre {
   private static Connection conexionPostgre=ConexionBase.gitConexionPostgre();

    public static void creaEsquemaDesguace(){
        try{
            Statement postgre=conexionPostgre.createStatement();
            postgre.execute("CREATE SCHEMA desguace");
            System.out.println("consulta realizada con exito");
        } catch (SQLException e) {
            System.err.println("sucedio un error en la creacion de desguace");
        }
    }

    public static void creaTipoComponente(){
        try{
            Statement postgre=conexionPostgre.createStatement();
            postgre.execute("CREATE TYPE desguace.Componente" +
                    " AS (nombre text,precio numeric(10,2),tamaño int);");
            System.out.println("consulta realizada con exito");
        } catch (SQLException e) {
            System.err.println("sucedio un error en la creacion de Componente");
        }
    }

    public static void creaVenta(){
        try{
            PreparedStatement postgre=conexionPostgre.prepareStatement("Insert into concesionario.Ventas(cliente_id,coche_id,fecha_venta,precio_venta)" +
                    " values(6,5,'2023-04-05', 35000.00);");
            postgre.executeUpdate();
            System.out.println("consulta realizada con exito");
        } catch (SQLException e) {
            System.err.println("sucedio un error en la creacion de venta");
        }
    }
    public static void cambiaCoche(){
        try{
            PreparedStatement postgre=conexionPostgre.prepareStatement("Update concesionario.Coches set detalles_coche.año_fabricacion=3000 where coche_id=1;");
            postgre.executeUpdate();
            System.out.println("consulta realizada con exito");
        } catch (SQLException e) {
            System.err.println("sucedio un error en la modificacion de coches");
        }
    }

    public static void quitaCocheDeClienteYVentas(){
        try{
            conexionPostgre.setAutoCommit(false);
            PreparedStatement postgreConsultaVentas=conexionPostgre.prepareStatement("delete from concesionario.Ventas " +
                    "where coche_id=1");
            postgreConsultaVentas.executeUpdate();

            PreparedStatement postgreConsultaClientes=conexionPostgre.prepareStatement("delete from concesionario.Clientes " +
                    "where coche_preferido_id=1");
            postgreConsultaClientes.executeUpdate();

            System.out.println("consultas realizada con exito");
            conexionPostgre.commit();
        } catch (SQLException e) {
            System.err.println("sucedio un error en la modificacion de coches");
            try {
                conexionPostgre.rollback();
            } catch (SQLException ex) {
                System.err.println("sucedio un error en el rollback");
            }
        }
        finally {
            try {
                conexionPostgre.setAutoCommit(true);
            } catch (SQLException e) {
                System.err.println("sucedio un error en el autocomit a verdadero");
            }
        }
    }

    public static void obtenEdadMediaClentes(){
        try{
            PreparedStatement postgre=conexionPostgre.prepareStatement("select Avg((datos_personales).edad) from concesionario.Clientes;");
            ResultSet resultado=postgre.executeQuery();
            while(resultado.next()){
                System.out.println("edad media clientes= "+resultado.getInt(1));
            }
        } catch (SQLException e) {
            System.err.println("sucedio un error en la consulta");
        }catch (NullPointerException e){
            System.err.println("resultado Vacio");
        }
    }
    public static void obtenerNombreYEspecialidadVendedores(){
        try{
            PreparedStatement postgre=conexionPostgre.prepareStatement("select (datos_personales).cedula,(datos_personales).especialidad" +
                    " from concesionario.Vendedores;");
            ResultSet resultado=postgre.executeQuery();
            while(resultado.next()){
                System.out.println("nombre: "+resultado.getString(1)+" ,"+
                        "especialidad: "+resultado.getString(2));
            }
        } catch (SQLException e) {
            System.err.println("sucedio un error en la consulta");
        }catch (NullPointerException e){
            System.err.println("resultado Vacio");
        }
    }

    public static void cochePreferidosMayores30(){
        try{
            PreparedStatement postgre=conexionPostgre.prepareStatement("select (datos_personales).nombre, (detalles_coche).modelo" +
                    " from concesionario.Clientes join concesionario.coches on Clientes.coche_preferido_id=Coches.coche_id where (datos_personales).edad>30;");
            ResultSet resultado=postgre.executeQuery();
            while(resultado.next()){
                System.out.println("nombre: "+resultado.getString(1)+" ,"+
                        "modelo preferido: "+resultado.getString(2));
            }
        } catch (SQLException e) {
            System.err.println("sucedio un error en la consulta");
        }catch (NullPointerException e){
            System.err.println("resultado Vacio");
        }
    }

    public static void datosCocheYVendedor(){
        try{
            PreparedStatement postgre=conexionPostgre.prepareStatement("select (detalles_coche).modelo,(detalles_coche).año_fabricacion,(datos_personales).cedula" +
                    " from concesionario.Coches join concesionario.Vendedores on Coches.vendedor_id=Vendedores.vendedor_id;");
            ResultSet resultado=postgre.executeQuery();
            while(resultado.next()){
                System.out.println("modelo del coche: "+resultado.getString(1)+" ,"+
                        "fecha de fabricacion del coche: "+resultado.getString(2)+" ,"+
                        "nombre vendedor: "+resultado.getString(3));
            }
        } catch (SQLException e) {
            System.err.println("sucedio un error en la consulta");
        }catch (NullPointerException e){
            System.err.println("resultado Vacio");
        }
    }

    public static void numeroVentasVendedores(){
        try{
            PreparedStatement postgre=conexionPostgre.prepareStatement("select (datos_personales).cedula,count(venta_id)" +
                    " from concesionario.Ventas " +
                    "join concesionario.Coches on Ventas.coche_id=Coches.coche_id " +
                    "join concesionario.Vendedores on Coches.vendedor_id=Vendedores.vendedor_id" +
                    " group by (datos_personales).cedula;");
            ResultSet resultado=postgre.executeQuery();
            while(resultado.next()){
                System.out.println("nombre vendedor: "+resultado.getString(1)+" ,"+
                        "cantidad vendida: "+resultado.getInt(2));
            }
        } catch (SQLException e) {
            System.err.println("sucedio un error en la consulta");
        }catch (NullPointerException e){
            System.err.println("resultado Vacio");
        }
    }

    public static void datosVendedoresSinVentas(){
        try{
            PreparedStatement postgre=conexionPostgre.prepareStatement("select (datos_personales).cedula,(datos_personales).especialidad" +
                    " from concesionario.Ventas" +
                    " right join concesionario.Coches on Ventas.coche_id=Coches.coche_id" +
                    " right join concesionario.Vendedores on Coches.vendedor_id=Vendedores.vendedor_id" +
                    " group by (datos_personales).cedula,(datos_personales).especialidad having count(venta_id)=0;");
            ResultSet resultado=postgre.executeQuery();
            while(resultado.next()){
                System.out.println("nombre vendedor: "+resultado.getString(1)+" ,"+
                        "especialidad: "+resultado.getString(2));
            }
        } catch (SQLException e) {
            System.err.println("sucedio un error en la consulta");
        }catch (NullPointerException e){
            System.err.println("resultado Vacio");
        }
    }
}
