package org.example;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.Key;

public class Prueba {
    public static void main(String[] args) throws Exception {

        Encriptador.NuevaContrasena("ggggggggggggggggggggggggggg");
        Key ka= Encriptador.obtenerClave();
        String awka="awala";
        System.out.println(awka);
        String cifrado=Encriptador.cifrar(awka,ka);
        System.out.println(cifrado);
        System.out.println(Encriptador.descifrar(cifrado,ka));


    }

}
