package org.example;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Cliente {
    static final String HOST ="localhost";
    static final int Puerto=2000;
    public static String mensaje="";
    public Cliente(){
        try{
            Scanner in=new Scanner(System.in);
            Socket sCliente =new Socket( HOST , Puerto );
// Creo los flujos de entrada y salida 
            DataInputStream flujo_entrada =new
                    DataInputStream(sCliente.getInputStream());
            DataOutputStream flujo_salida=new
                    DataOutputStream(sCliente.getOutputStream());
// TAREAS QUE REALIZA EL CLIENTE

            String datos=flujo_entrada.readUTF();
            System.out.println(datos);
            String mensaje="";
            Hilo hil=new Hilo(flujo_salida);
            hil.start();
            do{
                    String mensajeCliente = flujo_entrada.readUTF();
                    System.out.println(mensajeCliente);
                    if (mensajeCliente.equals("bye")) mensaje = mensajeCliente;
                if (mensaje.equals("bye"))break;

            }while (true);
            hil.interrupt();
            sCliente.close();
        }catch( Exception e ){
            System.out.println( e.getMessage());
        }
    }
    public static void main( String[] arg ){
        new Cliente();
    }

    public static void setMensaje(String men){
        mensaje=men;
    }
} 