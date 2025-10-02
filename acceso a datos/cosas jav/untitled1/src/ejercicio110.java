import java.util.Scanner;

public class ejercicio110 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Introduzca el valor del desplazamiento");
        int desplazamiento = sc.nextInt();
        if (desplazamiento > 26)
            desplazamiento = desplazamiento % 26;

        System.out.println("Selecciona si quieres:\n1.- Cifrar\n2.- Descifrar");
        int opcion = -1;
        do {
            opcion = sc.nextInt();
        }while(opcion != 1 && opcion != 2);

        String mensaje = "";
        System.out.println("Introduzca el mensaje a cifrar/descifrar:");

        do {
            mensaje = sc.next();
        }while(mensaje.isEmpty());

        switch (opcion) {
            case 1: {
                cifrar(desplazamiento, mensaje);
                break;
            }
            case 2: {
                descifrar(desplazamiento, mensaje);
                break;
            }
        }

    }

    private static void cifrar(int desplazamiento, String mensaje) {
        StringBuilder sb = new StringBuilder();
        char[]letras = mensaje.toCharArray();

        for(char letra : letras) {
            int asciiLetra = (int)letra;
            int asciiNuevo = (int)letra + desplazamiento;
            if (asciiNuevo > (int)'z' && asciiLetra >= (int)'a' && asciiLetra <= (int)'z')
                asciiNuevo -= 26;
            if (asciiNuevo > (int)'Z' && asciiLetra >= (int)'A' && asciiLetra <= (int)'Z')
                asciiNuevo -= 26;
            sb.append(((char)asciiNuevo));
        }
        System.out.println("El nuevo mensaje cifrado es: " + sb.toString());
        return;

    }

    private static void descifrar(int desplazamiento, String mensaje) {
        StringBuilder sb = new StringBuilder();
        char[]letras = mensaje.toCharArray();

        for(char letra : letras) {
            int asciiLetra = (int)letra;
            int asciiNuevo = (int)letra - desplazamiento;
            if (asciiNuevo < (int)'a' && asciiLetra >= (int)'a' && asciiLetra <= (int)'z')
                asciiNuevo += 26;
            if (asciiNuevo < (int)'A' && asciiLetra >= (int)'A' && asciiLetra <= (int)'Z')
                asciiNuevo += 26;
            sb.append(((char)asciiNuevo));
        }
        System.out.println("El nuevo mensaje cifrado es: " + sb.toString());
        return;

    }

}
