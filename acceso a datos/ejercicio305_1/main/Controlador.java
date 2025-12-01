package ejercicio305.main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controlador implements ActionListener{
    private Vista vista;
    private Modelo modelo;

    public Controlador(Vista vista, Modelo modelo){
        this.vista = vista;
        this.modelo = modelo;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("SUMAR")){
            String[] param = vista.getCampos();
            modelo.suma(Integer.parseInt(param[0]), Integer.parseInt(param[1]));
            int resultado = modelo.getResultadoSuma();
            vista.escribirResultado(Integer.toString(resultado));
        }
    }
}