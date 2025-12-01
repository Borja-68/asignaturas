package ejercicio305.main;

public class main {
    
    public static void main(String[] args) {
        // el modelo:
        Modelo modelo = new Modelo();
        // la vista:
        Vista vista = new Vista();
        // y el control:
        Controlador control = new Controlador (vista, modelo);
        // y arranca la interfaz (vista):
        vista.arranca();
        // configura la vista
        vista.setControlador(control);
    }
}
