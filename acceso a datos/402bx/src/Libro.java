import java.util.Scanner;

public class Libro {

    private int publicacion;
    private int edicion;
    private String titulo;
    private Autor autor;
    private String editorial;
    private int paginas;
    private boolean edicionElectronica;

    @Override
    public String toString() {
        return "<libro publicacion='" + publicacion + "' edicion='" + edicion +
                "'><titulo>" + titulo + "</titulo>" + autor.toString() + "<editorial>" +
                editorial + "</editorial><paginas>" + paginas + "</paginas><edicionElectronica>" +
                edicionElectronica + "</edicionElectronica></libro>";
    }

    public Libro generarDatosAleatorios() {
        this.publicacion = (int) (Math.random() * 15) + 2000;
        this.edicion = (int) (Math.random() * 20) + 1;
        this.titulo = "Título " + (int) (Math.random() * 20000);
        this.autor = new Autor().generarAleatorio();
        this.editorial = "Editorial " + (int) (Math.random() * 100);
        this.paginas = (int) (Math.random() * 701) + 150;
        this.edicionElectronica = Math.random() > 0.5;

        return this;
    }
}

class Autor{
    private String nombre;
    private String apellidos;

    public Autor() {
    }

    @Override
    public String toString() {
        return "<autor>" +
                "<nombre>" + nombre + "</nombre>"+
                "<apellidos>" + apellidos + "</apellidos>" +
                "</autor>";
    }

    public Autor generarAleatorio() {
        this.nombre = "Nombre" + (int) (Math.random() * 20);
        this.apellidos = "Apellido" + (int) (Math.random() * 20) +
                " Apellido" + (int) (Math.random() * 20);
        return this;
    }
}