package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import java.io.*;
import java.net.*;
import java.util.Scanner;

class Servidor extends Thread{
    Socket skCliente;
    static final int Puerto=2000;
    public static String mensaje="";
    public Servidor(Socket sCliente){
        skCliente=sCliente;
    }
    public static void main( String[] arg ){
        try{
// Inicio el servidor en el puerto
            ServerSocket skServidor =new ServerSocket(Puerto);
            System.out.println("Escucho el puerto "+ Puerto );
            while(true){
                Socket skCliente = skServidor.accept();// Espero a que  conecte un cliente
                System.out.println("Cliente conectado");
// Atiendo al cliente mediante un thread
                new Servidor(skCliente).start();
            }
        }catch(Exception e){;}
    }
    public void run(){
        try{
// Creo los flujos de entrada y salida
            DataInputStream flujo_entrada =new
                    DataInputStream(skCliente.getInputStream());
            DataOutputStream flujo_salida=new
                    DataOutputStream(skCliente.getOutputStream());

// ATENDER PETICIÓN DEL CLIENTE
            flujo_salida.writeUTF("Se ha conectado el cliente de forma correcta");
            Hilo hil=new Hilo(flujo_salida);
            hil.start();
            do{
                    String mensajeCliente = flujo_entrada.readUTF();
                    System.out.println(mensajeCliente);
                    if (mensajeCliente.equals("bye")) mensaje = mensajeCliente;
                    if (mensaje.equals("bye"))break;
            }while (true);
            hil.interrupt();
// Se cierra la conexión
            skCliente.close();
            System.out.println("Cliente desconectado");
        }catch( Exception e ){
            System.out.println( e.getMessage());
        }
    }
    public static void setMensaje(String men){
        mensaje=men;
    }
}