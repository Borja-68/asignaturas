package com.example.plantillaSpring.Entidades;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class EmpDTO {
    private int numemp;
    private String nomemp;
    private String puesto;
    private LocalDate feccont;
    private double sal;
    private double comision;
    private int iddepto;

    public EmpDTO (Emp emp){
        this.numemp = emp.getNumemp();
        this.nomemp = emp.getNoemp();
        this.puesto = emp.getPuesto();
        this.feccont = emp.getFeccont();
        this.sal = emp.getSal();
        this.comision = emp.getComision();
        this.iddepto = emp.getDepto().getNumdep();
    }
}