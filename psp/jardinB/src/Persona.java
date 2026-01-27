import java.util.Random;

enum Estado{dentro,fuera}

public class Persona extends Thread {
    public int numPersona;
    public Estado situacion;
    public Boolean funcionando=true;
    public Random random=new Random();
    public Persona(int numPersona, Estado estado) {
        super();
        this.numPersona = numPersona;
        this.situacion = estado;
    }

    @Override
    public void run() {
        while (funcionando)
            synchronized (this) {
                    int i= random.nextInt(Jardin.puertas.size());
                    if (situacion == Estado.dentro) {
                        if (Jardin.puertas.get(i)) {
                            if (Jardin.personas <  Jardin.personasMax) {
                                Jardin.setXpuertaFalse(i);
                                System.out.println("saliendo puerta "+(i+1)+" persona " + numPersona);
                                try {
                                    Thread.sleep(3000);
                                } catch (InterruptedException e) {return;}
                                System.out.println("salio puerta "+(i+1)+" persona " + numPersona);
                                Jardin.setXpuertaTrue(i);
                                Jardin.personas--;
                                situacion = Estado.fuera;
                            }else{ Jardin.addFallos();
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {return;}
                            }

                        } else {
                            System.out.println(" no sali puerta "+(i+1)+" , puerta ocupada " + numPersona);
                            Jardin.addFallos();
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {return;}
                        }

                    }


                    if (situacion == Estado.fuera) {
                        if (Jardin.puertas.get(i)) {
                            if (Jardin.personas <  Jardin.personasMax) {
                                Jardin.setXpuertaFalse(i);
                                System.out.println("entrando puerta "+(i+1)+" persona " + numPersona);
                                try {
                                    Thread.sleep(3000);
                                } catch (InterruptedException e) {return;}

                                System.out.println("entrado puerta "+(i+1)+" persona " + numPersona);
                                Jardin.setXpuertaTrue(i);
                                Jardin.personas++;
                                situacion = Estado.dentro;
                            } else {
                                System.out.println(" no entre puerta "+(i+1)+" , estaba lleno " + numPersona);
                                Jardin.addFallos();
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {return;}
                            }
                        } else {
                            System.out.println(" no entre puerta "+(i+1)+" , puerta ocupada " + numPersona);
                            Jardin.addFallos();
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                return;
                            }
                        }
                    }
            }
        }

        public void setFuncionandoFalse(){
        this.funcionando=false;
        }
    }

