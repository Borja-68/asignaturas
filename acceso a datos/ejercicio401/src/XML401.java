public class XML401 {

    private String nombre;
    private String apellidos;
    private int edad;
    private String correo;

    public XML401(String nombre, String apellidos, int edad, String correo) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.edad = edad;
        this.correo = correo;
    }

    @Override
    public String toString() {
        return "<alumno>" +
                "<nombre>" + nombre + "</nombre>" +
                "<apellidos>" + apellidos + "</apellidos>" +
                "<edad>" + edad + "</edad>" +
                "<correo>" + correo + "</correo>" +
                "</alumno>";
    }
}
