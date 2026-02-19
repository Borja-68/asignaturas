
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.Timer;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author a24borjapp
 */
public class TempRespasp extends JLabel implements Serializable, ActionListener{
    private int tiempo;
    private transient Timer t;
    private List<FinCuentaAtrasListener> receptores;

    
    
    public TempRespasp(){

        this.tiempo=5;
        this.t = new Timer(1000, this);
        this.receptores=new ArrayList<>();
        this.setText(Integer.toString(this.tiempo));
    }
    
    public int getTiempo() {
        return tiempo;
    }

    public void setTiempo(int tiempo) {
        this.tiempo = tiempo;
        this.setText(Integer.toString(tiempo));
        this.repaint();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
            if(tiempo>0){
                setTiempo(tiempo--); 
            }
            this.setText(Integer.toString(tiempo));
             if(tiempo<=0){
                lazarFinCuentaAtras();
            }
    }
    
    public void anadeReceptor(FinCuentaAtrasListener receptor){
        this.receptores.add(receptor);
    }
    
    public void quitaReceptor(FinCuentaAtrasListener receptor){
        this.receptores.remove(receptor);
    }

    private void lazarFinCuentaAtras() {
        FinCuentaAtrasEvent ev= new FinCuentaAtrasEvent(this);
        for (FinCuentaAtrasListener receptore : receptores) {
            receptore.capturarFinCuentaAtras(ev);
        }
    }
    
    public void setActivo(boolean valor){
        if(valor){
            t.start();
        }
        else t.stop();
    }
    
    public boolean isActivo(boolean valor){
            return t.isRunning();
    }
    
}
