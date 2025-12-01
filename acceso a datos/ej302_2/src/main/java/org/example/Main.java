package org.example;

import Entidades.Depto;
import Entidades.Emp;
import Repositorios.DeptoRepositorio;
import Repositorios.EmpRepositorio;
import org.hibernate.Session;


//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        System.out.println("Test");

        Session session = HibernateUtil.get().openSession();

        DeptoRepositorio dptoRepositorio = new DeptoRepositorio(session);
        EmpRepositorio empRepositorio = new EmpRepositorio(session);

        Depto depto1 = new Depto("Depto 1", "Vigo");
        Depto depto2 = new Depto("Depto 2", "Vigo");
        Depto depto3 = new Depto("Depto 3", "Santiago");
        Depto depto4 = new Depto("Depto 4", "Santiago");
        Depto depto5 = new Depto("Depto 5", "Ourense");

        Emp emp1 = new Emp("Emp 1", "Ingeniero", 3000, 45, "12345678A", true);
        Emp emp2 = new Emp("Emp 2", "Técnico", 1000, 18, "12345678B", false);
        Emp emp3 = new Emp("Emp 3", "Ingeniero", 4000, 50, "12345678C", true);
        Emp emp4 = new Emp("Emp 4", "Técnico", 1500, 30, "12345678D", false);
        Emp emp5 = new Emp("Emp 5", "Ingeniero", 2000, 35, "12345678E", false);
        Emp emp6 = new Emp("Emp 6", "Técnico", 1250, 20, "12345678F", false);
        Emp emp7 = new Emp("Emp 7", "Ingeniero", 2500, 40, "12345678G", false);
        Emp emp8 = new Emp("Emp 8", "Técnico", 1300, 22, "12345678H", false);
        Emp emp9 = new Emp("Emp 9", "Ingeniero", 2750, 43, "12345678I", false);
        Emp emp10 = new Emp("Emp 10", "Técnico", 1450, 25, "12345678J", false);

        depto1.nuevoEmpleado(emp1);
        depto1.nuevoEmpleado(emp2);
        depto2.nuevoEmpleado(emp3);
        depto2.nuevoEmpleado(emp4);
        depto3.nuevoEmpleado(emp5);
        depto3.nuevoEmpleado(emp6);
        depto4.nuevoEmpleado(emp7);
        depto4.nuevoEmpleado(emp8);
        depto5.nuevoEmpleado(emp9);
        depto5.nuevoEmpleado(emp10);



        dptoRepositorio.guardar(depto1);
        dptoRepositorio.guardar(depto2);
        dptoRepositorio.guardar(depto3);
        dptoRepositorio.guardar(depto4);
        dptoRepositorio.guardar(depto5);

        empRepositorio.guardar(emp1);
        empRepositorio.guardar(emp2);
        empRepositorio.guardar(emp3);
        empRepositorio.guardar(emp4);
        empRepositorio.guardar(emp5);
        empRepositorio.guardar(emp6);
        empRepositorio.guardar(emp7);
        empRepositorio.guardar(emp8);
        empRepositorio.guardar(emp9);
        empRepositorio.guardar(emp10);

        dptoRepositorio.visualizarDepartamento(1);
        session.close();
        System.out.println("Finalizando la conexion a MySQL");
    }
}
