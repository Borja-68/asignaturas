/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package componenteshora;

import javax.swing.JPanel;
import java.awt.Color; 
import java.awt.Dimension; 
import java.awt.Graphics; 
import java.awt.Graphics2D;  
import java.awt.RenderingHints; 
import java.awt.Rectangle;  
import java.awt.event.MouseAdapter; 
import java.awt.event.MouseEvent; 
import java.io.Serializable;

/**
 *
 * @author elgoc
 */
public class SelectorHoraAnalogicoBean extends JPanel implements Serializable{
     // Guardamos la hora en formato 24h (0–23) 
    private int hora; 
 
    // Guardamos el minuto (0–59) 
    private int minuto; 
 
    // Colores configurables para el fondo y las agujas 
    private Color colorFondo; 
    private Color colorAgujas; 
 
    // "Botones" dibujados: zonas clicables 
    private Rectangle botonHoraMas; 
    private Rectangle botonHoraMenos; 
    private Rectangle botonMinutoMas; 
    private Rectangle botonMinutoMenos; 
 
// ------------------------------- 
// CONSTRUCTOR DEL COMPONENTE 
// Inicia los valores 
// Establece el tamaño preferido del panel 
// Crea los rectángulos-botones 
// Añade un oyente de clics de ratón 
// ------------------------------- 
 
 /** 
  * Constructor sin parámetros (requerido por los JavaBeans). 
  * Aquí establecemos el estado inicial del componente. 
  */ 
    public SelectorHoraAnalogicoBean() { 
 
        // Establecemos el valor inicial del reloj: 12:00 PM 
        this.hora = 12; 
        this.minuto = 0; 
 
        // Establecemos los colores por defecto 
        this.colorFondo = Color.WHITE; 
        this.colorAgujas = Color.BLACK; 
 
        // Establecemos el tamaño por defecto del componente 
        setPreferredSize(new Dimension(200, 220)); 
 
        // Establecemos un color de fondo 
        setBackground(Color.WHITE); 
 
        // Creamos las zonas clicables. Su tamaño final se calcula 
        // durante el pintado del componente. 
        botonHoraMas = new Rectangle(); 
        botonHoraMenos = new Rectangle(); 
        botonMinutoMas = new Rectangle(); 
        botonMinutoMenos = new Rectangle(); 
 
   // Añadimos un listener para detectar clics del ratón 
   // El evento de clic da una posición x y una posición y 
   // Sobreescribimos el método mouseClicked (evento) 
   // Ahora llamará a gestionarClick(posición_x, posición_y) 
        addMouseListener(new MouseAdapter() { 
            @Override 
            public void mouseClicked(MouseEvent e) { 
                gestionarClick(e.getX(), e.getY()); 
            } 
        }); 
    // Aquí añadiremos getters/setters, normalización y dibujo 
    }
    // ------------------------------- 
// NORMALIZACIÓN DE HORA Y MINUTOS 
// Validamos que la hora esté entre 0 y 23 
// Validamos que los minutos estén entre 0 y 59 
// ------------------------------- 
   private void normalizarHora() { 
        // Entre 0 y 23 
        if (hora < 0) { 
            hora = (hora % 24 + 24) % 24; 
        } else { 
            hora = hora % 24; 
        } 
    } 
 
    private void normalizarMinuto() { 
        // Entre 0 y 59 
        
        if (minuto < 0) { 
            minuto = (minuto % 60 + 60) % 60;
            setHora(hora-1);
        }if(minuto==60){
         minuto = minuto % 60; 
         setHora(hora+1);
        } else { 
            minuto = minuto % 60; 
        } 
    }
    
    public int getHora() { 
        return hora; 
    } 
 
    public void setHora(int hora) { 
        this.hora = hora; 
        normalizarHora(); // Aseguramos rango 0–23 
        repaint();        // Hacemos que el reloj se redibuje 
    } 
 
    public int getMinuto() { 
        return minuto; 
    } 
 
    public void setMinuto(int minuto) { 
        this.minuto = minuto; 
        normalizarMinuto(); // Aseguramos rango 0–59 
        repaint();  // Hacemos que el reloj se redibuje 
    } 
 
    public Color getColorFondo() { 
        return colorFondo; 
    } 
 
    public void setColorFondo(Color colorFondo) { 
        this.colorFondo = colorFondo; 
        repaint(); 
    } 
 
    public Color getColorAgujas() { 
        return colorAgujas; 
    } 
 
    public void setColorAgujas(Color colorAgujas) { 
        this.colorAgujas = colorAgujas; 
        repaint(); 
    } 
    
     @Override 
    protected void paintComponent(Graphics g) { 
 
        // Limpia el panel antes de dibujar. Siempre recomendable ponerlo al principio cuando sobreescribimos paintComponent 
        super.paintComponent(g);  
 
        // Convertimos Graphics en Graphics2D (más avanzado). 
        // El parámetro g es de tipo Graphics por compatibilidad,  
        // pero en Swing realmente se pasa un Graphics2D internamente. 
        // Por eso podemos hacer el casteo sin problemas. 
        Graphics2D g2 = (Graphics2D) g; 
 
        // Activamos suavizado para líneas más bonitas 
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); 
 
        int w = getWidth();   // Detectamos ancho del panel 
        int h = getHeight();  // Detectamos alto del panel 
 
        // ----------------------------------- 
        // Pintar fondo del componente 
        // ----------------------------------- 
        g2.setColor(colorFondo); 
        g2.fillRect(0, 0, w, h); 
 
        // Dejamos espacio inferior para los botones 
        int espacioBotones = 40; 
 
        // Calculamos el tamaño del reloj (es un círculo) 
        int diametro = Math.min(w, h - espacioBotones) - 20; // -20 lo dejamos como margen estético 
        if (diametro < 0) diametro = 0; 
 
        int x = (w - diametro) / 2; // coordenada X del círculo 
        int y = 10;                 // un poco de margen arriba 
 
        // Centro del círculo 
        int centroX = x + diametro / 2; 
        int centroY = y + diametro / 2; 
        int radio = diametro / 2; 
 
        // ----------------------------------- 
        // Dibujar la esfera del reloj 
        // ----------------------------------- 
        g2.setColor(Color.LIGHT_GRAY); 
        g2.fillOval(x, y, diametro, diametro); //Relleno del reloj 
 
        g2.setColor(Color.DARK_GRAY); 
        g2.drawOval(x, y, diametro, diametro); //Borde del reloj 
 
        // ----------------------------------- 
        // Dibujar marcas de las 12 horas 
        // ----------------------------------- 
        for (int i = 0; i < 12; i++) { 
            // Cada hora está separada 30 grados (360/12). 
            // -90 para empezar en “las 12” 
            // Por defecto, el 0º sería una línea horizontal 
            double angulo = Math.toRadians(i * 30 - 90);  
            // Inicio de la marca (en el 80% del tamaño del radio del reloj) 
            int rInterno = (int) (radio * 0.8);  
            // Final de la marca (en el 90% del tamaño del radio del reloj)             
             int rExterno = (int) (radio * 0.9);  
            // Cálculo de coordenadas de los dos extremos de las marcas  
            int x1 = centroX + (int) (rInterno * Math.cos(angulo)); 
            int y1 = centroY + (int) (rInterno * Math.sin(angulo)); 
            int x2 = centroX + (int) (rExterno * Math.cos(angulo)); 
            int y2 = centroY + (int) (rExterno * Math.sin(angulo)); 
 
            // Dibujo de la línea entre los puntos x e y calculados antes 
            g2.drawLine(x1, y1, x2, y2); 
        } 
 
        // ----------------------------------- 
        // Dibujar aguja de horas 
        // ----------------------------------- 
        g2.setColor(colorAgujas); 
 
        // Calculamos el resto entre la hora y las 12 (mod) 
        int hora12 = hora % 12; // convertimos 0–23 en 0–11 
 
        // Angulo de la aguja de horas: cada hora son 30º, 
        // Añadimos la fracción de los minutos a la aguja de la hora 
        double anguloHora = Math.toRadians((hora12 + minuto / 60.0) * 30 - 90); 
 
        // Esta aguja va desde el centro a la mitad del radio del reloj 
        int largoHora = (int) (radio * 0.5); 
 
        // Calculamos las coordenadas del extremo de la aguja 
        int hx = centroX + (int) (largoHora * Math.cos(anguloHora)); 
        int hy = centroY + (int) (largoHora * Math.sin(anguloHora)); 
 
        // Dibujo de la línea entre el centro y el extremo 
        g2.drawLine(centroX, centroY, hx, hy); 
 
        // ----------------------------------- 
        // Aguja de minutos (más larga) 
        // ----------------------------------- 
        // Cada minuto está separado 6 grados (360/60). 
        // -90 para empezar en “las 12” 
        // Por defecto, el 0º sería una línea horizontal 
        double anguloMin = Math.toRadians(minuto * 6 - 90); // 6º por minuto 
 
        // La aguja minutos va del centro al 80% del radio del reloj 
        int largoMin = (int) (radio * 0.8); 
        // Calculamos las coordenadas del extremo 
        int mx = centroX + (int) (largoMin * Math.cos(anguloMin)); 
        int my = centroY + (int) (largoMin * Math.sin(anguloMin)); 
        // Dibujo de la línea entre el centro y el extremo 
        g2.drawLine(centroX, centroY, mx, my); 
 
        // Dibujamos un circulillo en el centro del reloj 
        g2.fillOval(centroX - 3, centroY - 3, 6, 6); 
 
        // ----------------------------------- 
        // Indicador AM / PM 
        // ----------------------------------- 
 
        // Determinamos si es AM (0 a 11) o PM (12 a 23) 
        boolean esPM = (hora >= 12); 
        String indicador = esPM ? "PM" : "AM"; 
 
        g2.setColor(Color.BLACK); 
        g2.setFont(g2.getFont().deriveFont(16f)); //fuente actual, asigna tamaño 16 
 
        //Comprueba cuando ocupa el indicador en la fuente actual, para dibujarlo centrado y por debajo del reloj 
        int anchoTexto = g2.getFontMetrics().stringWidth(indicador); 
        g2.drawString(indicador, centroX - anchoTexto / 2, centroY + radio / 2); 
 
        // ----------------------------------- 
        // Dibujar los botones 
        // ----------------------------------- 
 
        dibujarBotones(g2, w, y + diametro); 
    }
    
     /** 
     * Dibuja los 4 botones que permiten cambiar hora y minuto. 
     * También calcula las áreas rectangulares para detectar clics. 
     */ 
    private void dibujarBotones(Graphics2D g2, int w, int baseY) { 
 
        // Definimos el tamaño de los botones y huecos entre ellos 
        int margen = 10; 
        int altoBotones = 30; 
        int anchoBoton = 40; 
        int espacio = 10; 
 
        // Calculamos posición vertical 
        int filaY = baseY + margen; 
 
        // Calculamos posición horizontal centrada 
        int anchoTotal = 4 * anchoBoton + 3 * espacio; 
        int x1 = (w - anchoTotal) / 2; 
 
        // Definimos los rectángulos en los que podemos hacer clic 
        botonHoraMenos.setBounds(x1, filaY, anchoBoton, altoBotones); 
        botonHoraMas.setBounds(x1 + (anchoBoton + espacio), filaY, anchoBoton, altoBotones); 
        botonMinutoMenos.setBounds(x1 + 2 * (anchoBoton + espacio), filaY, anchoBoton, altoBotones); 
        botonMinutoMas.setBounds(x1 + 3 * (anchoBoton + espacio), filaY, anchoBoton, altoBotones); 
 
        // Rellenamos los botones 
        g2.setColor(Color.GRAY); 
        g2.fill(botonHoraMenos); 
        g2.fill(botonHoraMas); 
        g2.fill(botonMinutoMenos); 
        g2.fill(botonMinutoMas); 
 
        // Dibujamos el borde de los botones 
        g2.setColor(Color.BLACK); 
        g2.draw(botonHoraMenos); 
        g2.draw(botonHoraMas); 
        g2.draw(botonMinutoMenos); 
        g2.draw(botonMinutoMas); 
 
        // Dibujamos las etiquetas de los botones (H+,H-,M+,M-) 
        g2.drawString("-H", botonHoraMenos.x + 12, botonHoraMenos.y + 20); 
        g2.drawString("+H", botonHoraMas.x + 12, botonHoraMas.y + 20); 
        g2.drawString("-M", botonMinutoMenos.x + 12, botonMinutoMenos.y + 20); 
        g2.drawString("+M", botonMinutoMas.x + 12, botonMinutoMas.y + 20); 
    } 
    
    /** 
    * Comprueba si el clic se produjo en alguno de los botones 
    * en cuyo caso, actualiza hora y minuto 
    */ 
   private void gestionarClick(int x, int y) { 
        if (botonHoraMas.contains(x, y)) { 
            setHora(hora + 1); 
        } else if (botonHoraMenos.contains(x, y)) { 
            setHora(hora - 1); 
        } else if (botonMinutoMas.contains(x, y)) { 
            setMinuto(minuto + 1); 
        } else if (botonMinutoMenos.contains(x, y)) { 
            setMinuto(minuto - 1); 
        } 
        // Los setters se encargan de normalizar y llamar a repaint() 
    } 
}
