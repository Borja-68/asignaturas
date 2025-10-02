public class Contacto {
    private String numero;
    private String nombre;
    public Contacto(){}
    public Contacto(String numero,String nombre){
        this.nombre=nombre;
        this.numero=numero;
    }

    public String getNumero() {
        return numero;
    }

    public String getNombre() {
        return nombre;
    }
    public String toString(){
        return "numero "+numero+"\n"+"nombre "+nombre;
    }
}
