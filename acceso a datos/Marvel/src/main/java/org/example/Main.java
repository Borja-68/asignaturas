package org.example;
import Entidades.*;
import org.hibernate.Session;
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        System.out.println("Test");

        Session session = HibernateUtil.get().openSession();
        Traje traje1=new Traje("Armadura Mark LXXXV con nanobots");
        Traje traje2=new Traje("Traje de vibranium con escudo magnético");
        Traje traje3=new Traje("Traje de batalla psíquico");
        Traje traje4=new Traje("Traje de combate reforzado con adamantium");
        Traje traje5=new Traje("Uniforme espacial con propulsores");

        Personaje per1=new Personaje("Tony Stark", "Iron Man");
        Personaje per2=new Personaje("Steve Rogers","Capitán América");
        Personaje per3=new Personaje("Jean Grey","Phoenix");
        Personaje per4=new Personaje("Logan","Wolverine");
        Personaje per5=new Personaje("Peter Quill","Star-Lord");

        per1.setTraje(traje1);
        per2.setTraje(traje2);
        per3.setTraje(traje3);
        per4.setTraje(traje4);
        per5.setTraje(traje5);

        Habilidad hab1=new Habilidad("Vuelo","Capacidad de desplazarse por el aire");
        Habilidad hab2=new Habilidad("Superfuerza","Fuerza física muy superior a la humana");
        Habilidad hab3=new Habilidad("Regeneración","Curación acelerada de heridas");
        Habilidad hab4=new Habilidad("Telepatía","Habilidad para leer y controlar mentes");
        Habilidad hab5=new Habilidad("Combate cuerpo a cuerpo","Entrenamiento avanzado en lucha");

        PersonajeHabilidad perhab1=new PersonajeHabilidad();
        PersonajeHabilidad perhab2=new PersonajeHabilidad();
        PersonajeHabilidad perhab3=new PersonajeHabilidad();
        PersonajeHabilidad perhab4=new PersonajeHabilidad();
        PersonajeHabilidad perhab5=new PersonajeHabilidad();
        PersonajeHabilidad perhab6=new PersonajeHabilidad();
        PersonajeHabilidad perhab7=new PersonajeHabilidad();
        PersonajeHabilidad perhab8=new PersonajeHabilidad();
        PersonajeHabilidad perhab9=new PersonajeHabilidad();

        perhab1.setPersonaje(per1);
        perhab2.setPersonaje(per1);
        perhab3.setPersonaje(per2);
        perhab4.setPersonaje(per2);
        perhab5.setPersonaje(per3);
        perhab6.setPersonaje(per4);
        perhab7.setPersonaje(per4);
        perhab8.setPersonaje(per5);
        perhab9.setPersonaje(per5);

        perhab1.setHabilidad(hab1);
        perhab2.setHabilidad(hab2);
        perhab3.setHabilidad(hab2);
        perhab4.setHabilidad(hab5);
        perhab5.setHabilidad(hab4);
        perhab6.setHabilidad(hab3);
        perhab7.setHabilidad(hab5);
        perhab8.setHabilidad(hab1);
        perhab9.setHabilidad(hab5);

        Evento ev1=new Evento("Batalla de Nueva York","Nueva York");
        Evento ev2=new Evento("Guerra Civil","Leipzig-Halle");
        Evento ev3=new Evento("Infinity War","Titán");

        Participa par1=new Participa("2012-05-04","Líder tecnológico");
        Participa par2=new Participa("2012-05-04","Combatiente principal");
        Participa par3=new Participa("2016-05-06","Líder del bando legalista");
        Participa par4=new Participa("2018-04-27","Apoyo mental");
        Participa par5=new Participa("2018-04-27","Tanque ofensivo");
        Participa par6=new Participa("2018-04-27","Explorador espacial");


        session.close();
        System.out.println("Finalizando la conexion a MySQL");
    }
}
