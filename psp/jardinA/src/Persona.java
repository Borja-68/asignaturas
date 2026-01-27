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
    public void run()  {
        while (true) {
            synchronized (this) {
                    if (situacion == Estado.dentro) {
                        if (Jardin.puertaSalida) {
                            Jardin.puertaSalida = false;
                            System.out.println("saliendo " + numPersona);
                            try {
                                Thread.sleep(3000);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                            System.out.println("salio " + numPersona);
                            Jardin.puertaSalida = true;
                            Jardin.personas--;
                            situacion = Estado.fuera;
                        } else {
                            System.out.println(" no sali, puerta ocupada " + numPersona);
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }


                    if (situacion == Estado.fuera) {
                        if (Jardin.puertaEntrada) {
                            if (Jardin.personas < 100) {
                                Jardin.puertaEntrada = false;
                                System.out.println("entrando " + numPersona);
                                try {
                                    Thread.sleep(3000);
                                } catch (InterruptedException e) {
                                    throw new RuntimeException(e);
                                }

                                System.out.println("entrado " + numPersona);
                                Jardin.puertaEntrada = true;
                                Jardin.personas++;
                                situacion = Estado.dentro;
                            } else {
                                System.out.println(" no entre, eran gordos " + numPersona);
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        } else {
                            System.out.println(" no entre, puerta ocupada " + numPersona);
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
            }
        }
    }
}

