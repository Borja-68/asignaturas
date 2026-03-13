
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.Serializable;
import java.util.List;
import javax.swing.JTextField;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author a24borjapp
 */
public class pasoAPaso2 extends JTextField implements Serializable,FocusListener{

    private Color colorError; 
    private Color colorBase; 
    
    private List<ErrorNumericoListener> receptores;
    
    public pasoAPaso2(){
    this.colorError = Color.RED;
    this.colorBase=Color.WHITE;
    this.setText("0");
    this.receptores = new java.util.ArrayList<>();
    this.addFocusListener(this);
    }

    public Color getColorError() {
        return colorError;
    }

    public Color getColorBase() {
        return colorBase;
    }

    public void setColorError(Color colorError) {
        this.colorError = colorError;
    }

    public void setColorBase(Color colorBase) {
        this.colorBase = colorBase;
    }
    
    public void addError(ErrorNumericoListener error){
        receptores.add(error);
    }
    
    public void quitError(ErrorNumericoListener error){
        receptores.remove(error);
    }
    
    @Override
    public void focusGained(FocusEvent e) {
            
    }

    @Override
    public void focusLost(FocusEvent e) { 
        try{
                Integer.parseInt(this.getText());
                this.setBackground(colorBase);
                this.repaint();
            }
            catch(NumberFormatException ex){
                this.setBackground(colorError);
                this.repaint();
                lanzarNumerico();
            }   }

    private void lanzarNumerico() {
        ErrorNumericoEvent ev=new ErrorNumericoEvent(this);
        for(ErrorNumericoListener receptor: receptores){
            receptor.errorEnDato(ev);
        }
    }

    
    
}
