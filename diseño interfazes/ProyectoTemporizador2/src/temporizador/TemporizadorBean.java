/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Beans/Bean.java to edit this template
 */
package temporizador;
import java.io.Serializable;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
  
/**
 *
 * @author a24borjapp
 */
public class TemporizadorBean extends JLabel implements Serializable, ActionListener,FinCuentaAtrasListener {
        private int tiempo;
        private transient javax.swing.Timer t;
        private FinCuentaAtrasListener receptor;

            
        public TemporizadorBean() {
            tiempo = 5; // tiempo por defecto
            t = new javax.swing.Timer(1000, this); // se ejecuta cada segundo
            setText(Integer.toString(tiempo)); // muestra tiempo inicial
            setActivo(true); // reinicia la cuenta atrás
        }
    
        public int getTiempo() {
            return tiempo;
        }

        public void setTiempo(int tiempo) {
            this.tiempo = tiempo;
            setText(Integer.toString(tiempo)); // reflejar en pantalla
            repaint();
        }
        
        @Override
        public void actionPerformed(ActionEvent e) {
            setText(Integer.toString(tiempo));
            repaint();
            if (tiempo == 0) {
                setActivo(false);
                System.out.println("Temporizador finalizado");
                lanzarFinCuentaAtras();
            }
            tiempo--;
        }
        
        public final void setActivo(boolean valor) {
            if (valor)
            t.start();
            else
            t.stop();
        }
        public boolean getActivo() {
            return t.isRunning();
        }

    @Override
    public void capturarFinCuentaAtras(FinCuentaAtrasEvent ev) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void addFinCuentaAtrasListener(FinCuentaAtrasListener r) {
        this.receptor = r;
    }
    
    public void removeFinCuentaAtrasListener(FinCuentaAtrasListener r)
    {
        this.receptor = null;
    }

    private void lanzarFinCuentaAtras() {
        if (receptor != null) {
            receptor.capturarFinCuentaAtras(
            new FinCuentaAtrasEvent(this)
            );
        }
    }

}
