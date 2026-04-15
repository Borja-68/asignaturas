package org.example;

import java.io.*;
import java.util.Scanner;

public class Hilo extends Thread{
    private DataOutputStream flujo_salida;
    public Hilo(DataOutputStream flujo_salida){
        super();
        this.flujo_salida=flujo_salida;
    }

    public void run(){
        try( BufferedReader in=new BufferedReader(new InputStreamReader(System.in))) {
        String mensaje="";
            while (true){
                mensaje=in.readLine();
                flujo_salida.writeUTF(mensaje);
                Servidor.setMensaje(mensaje);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
