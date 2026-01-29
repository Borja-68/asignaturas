enum Estado{dentro,fuera}

public class Persona extends Thread {
    public int numPersona;
    public Estado situacion;
    public Persona(int numPersona, Estado estado) {
        super();
        this.numPersona = numPersona;
        this.situacion = estado;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (this) {
                if (situacion == Estado.dentro) {
                    if (Jardin.puertaSalida) {
                        if (Jardin.personas>0){
                        Jardin.puertaSalida = false;
                        System.out.println("saliendo " + numPersona);
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            return;
                        }
                        System.out.println("salio " + numPersona);
                        Jardin.personas--;
                        situacion = Estado.fuera;
                        Jardin.puertaSalida = true;
                        } else {
                                System.out.println(" no sali, no habia nadie " + numPersona);
                                Jardin.fallos += 1;
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    return;
                                }
                            }
                    }else {
                        System.out.println(" no sali, puerta ocupada " + numPersona);
                        Jardin.fallos += 1;
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            return;
                        }
                    }
                }
            }
            synchronized (this) {
                if (situacion == Estado.fuera) {
                    if (Jardin.puertaEntrada) {
                        Jardin.puertaEntrada = false;
                        System.out.println("entrando " + numPersona);
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            return;
                        }
                        System.out.println("entrado " + numPersona);
                        Jardin.personas++;
                        situacion = Estado.dentro;
                        Jardin.puertaEntrada = true;

                    } else {
                        System.out.println(" no entre, puerta ocupada " + numPersona);
                        Jardin.fallos += 1;
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            return;
                        }
                    }
                }
            }
        }
    }
 }

