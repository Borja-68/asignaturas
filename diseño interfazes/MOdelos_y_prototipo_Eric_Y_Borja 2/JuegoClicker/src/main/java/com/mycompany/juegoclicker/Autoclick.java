/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.juegoclicker;

import static com.mycompany.juegoclicker.PantallaPrincipal.sumadorPuntos;

/**
 *
 * @author elgoc
 */
public class Autoclick extends Thread {
    private static final Object LOCK = new Object();
    private static boolean pausado = false;
    public static int tiempoDormir=2000;
    public Autoclick(){super();
    }
   
    public void run(){
        try{
            while(true){
                   synchronized (LOCK) {
                    while (pausado) {
                        try {
                            LOCK.wait();
                        } catch (InterruptedException e) {
                            return;
                        }
                    }
                }
                
                Thread.sleep(tiempoDormir);
                addPuntos();
            }
        }catch(Exception ignore){}
    }
    
      public static void pausarTodos() {
        synchronized (LOCK) {
            pausado = true;
        }
    }
      
         public static void reanudarTodos() {
        synchronized (LOCK) {
            pausado = false;
            LOCK.notifyAll();
        }
    }
    
    private static void addPuntos(){
       PantallaPrincipal.puntos+=PantallaPrincipal.sumadorPuntos;
       PantallaPrincipal.jLabel1.setText(""+PantallaPrincipal.puntos);
       
    }
}
