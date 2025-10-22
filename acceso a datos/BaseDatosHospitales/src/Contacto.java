public class Contacto {
    private String nombreContacto;
    private String nif;
    private int telefono;
    private String correo;

    public Contacto(){}
    public Contacto(String nombreContacto,String nif,int telefono,String correo){
        this.nombreContacto=nombreContacto;
        this.nif=nif;
        this.telefono=telefono;
        if(correo.contains("@gmail.com"))this.correo=correo;
        else this.correo=correo+"@gmail.com";
    }

    public String getCorreo() {
        return correo;
    }

    public int getTelefono() {
        return telefono;
    }

    public String getNif() {
        return nif;
    }



    public static boolean nifCorrecto(String nif){
        if(nif.length()!=9){
            System.out.println("la cantidad de caracteres no es 9");
            return false;
        }

        for(int i=0; i<nif.length();i++) {
            if (i <= 7){
                if (!Character.isDigit(nif.charAt(i))){
                    System.out.println("no se introdujeron 8 numeros");
                    return false;
                }
        }
         else if(!Character.isLetter(nif.charAt(i))){
                System.out.println("el ultimo caracter no es una letra");
             return false;
            }
        }
        return true;
    }


    public static boolean telefonoCorrecto(String telefono){
        if(telefono.length()!=9){
            System.out.println("la cantidad de numeros no es 9");
            return false;
        }
        for(int i=0; i<telefono.length();i++){
            if(!Character.isDigit(telefono.charAt(i))){
                System.out.println("no se introdujeron solamente numeros");
                return false;
            }
        }
        return true;
    }


}
