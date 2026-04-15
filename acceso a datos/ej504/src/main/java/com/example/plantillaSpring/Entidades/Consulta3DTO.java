package com.example.plantillaSpring.Entidades;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Consulta3DTO {
    private String nombrE;
    private LocalDate fecha;

    public Consulta3DTO(Emp emp){
        this.nombrE = emp.getNoemp();
        this.fecha = emp.getFeccont();
    }
}
