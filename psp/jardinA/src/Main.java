//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Persona per1=new Persona(1,Estado.dentro);
        Persona per2=new Persona(2,Estado.dentro);
        Persona per3=new Persona(3,Estado.dentro);
        Persona per4=new Persona(4,Estado.dentro);
        Persona per5=new Persona(5,Estado.dentro);
        Persona per6=new Persona(6,Estado.fuera);
        Persona per7=new Persona(7,Estado.fuera);
        Persona per8=new Persona(8,Estado.fuera);
        Persona per9=new Persona(9,Estado.fuera);
        Persona per0=new Persona(0,Estado.fuera);
        Jardin jar=new Jardin();
        jar.listaPersonas.add(per0);
        jar.listaPersonas.add(per1);
        jar.listaPersonas.add(per2);
        jar.listaPersonas.add(per3);
        jar.listaPersonas.add(per4);
        jar.listaPersonas.add(per5);
        jar.listaPersonas.add(per6);
        jar.listaPersonas.add(per7);
        jar.listaPersonas.add(per8);
        jar.listaPersonas.add(per9);

        jar.start();

    }
}