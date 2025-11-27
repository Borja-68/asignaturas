/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package temporizador;

import java.util.EventListener;
import temporizador.FinCuentaAtrasEvent;

public interface FinCuentaAtrasListener extends EventListener {
    public void capturarFinCuentaAtras(FinCuentaAtrasEvent ev);
}
