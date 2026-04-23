package org.example;
import java.io.*;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;

public class Encriptador {

    public final static String Nombre_Fichero="Encriptacion.txt";

    public static void NuevaContrasena(String mensaje) throws FileNotFoundException {
        PrintWriter pw= new PrintWriter(Nombre_Fichero);
        int longitud=16;
        if (mensaje.length()>=24)longitud=24;
        if (mensaje.length()>=32)longitud=32;
        pw.write(mensaje+"###"+longitud);
        pw.close();
    }

    public static Key obtenerClave() throws IOException {
        // La longitud puede ser de 16, 24 o 32 bytes.
        File fil=new File(Nombre_Fichero);
        BufferedReader br=new BufferedReader(new FileReader(fil));
        String[] datos=br.readLine().split("###");
        Key clave = new SecretKeySpec(datos[0].getBytes(), 0, Integer.parseInt(datos[1]), "AES");
        return clave;
    }

    public static String cifrar(String textoEnClaro, Key key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key);

        byte[] cipherText = cipher.doFinal(textoEnClaro.getBytes());
        return Base64.getEncoder().encodeToString(cipherText);
    }

    public static String descifrar(String textoCifrado, Key key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key);

        byte[] plainText = cipher.doFinal(Base64.getDecoder().decode(textoCifrado));
        return new String(plainText);
    }
}
