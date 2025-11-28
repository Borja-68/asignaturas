/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Beans/Bean.java to edit this template
 */
package ceditor;

import java.awt.Color;
import java.awt.Font;
import java.io.Serializable;
import javax.swing.JTextField;

public class TCompEManual extends JTextField implements Serializable {
    
    private Color color;
    private Font fuente;
    private int ancho;
    private Color fondo;

    public TCompEManual() {
    }
    
     public Color getFondo() {
        return fondo;
    }

    /**
     * Set the value of fondo
     *
     * @param fondo new value of fondo
     */
    public void setFondo(Color fondo) {
        this.fondo = fondo;
        this.setBackground(fondo);
    }


    /**
     * Get the value of color
     *
     * @return the value of color
     */
    public Color getColor() {
        return color;
    }

    /**
     * Set the value of color
     *
     * @param color new value of color
     */
    public void setColor(Color color) {
        this.color = color;
        this.setForeground(color);
    }


    /**
     * Get the value of fuente
     *
     * @return the value of fuente
     */
    public Font getFuente() {
        return fuente;
    }

    /**
     * Set the value of fuente
     *
     * @param fuente new value of fuente
     */
    public void setFuente(Font fuente) {
        this.fuente = fuente;
        this.setFont(fuente);
    }


    /**
     * Get the value of ancho
     *
     * @return the value of ancho
     */
    public int getAncho() {
        return ancho;
    }

    /**
     * Set the value of ancho
     *
     * @param ancho new value of ancho
     */
    public void setAncho(int ancho) {
        this.ancho = ancho;
        this.setColumns(ancho);
    }
}


