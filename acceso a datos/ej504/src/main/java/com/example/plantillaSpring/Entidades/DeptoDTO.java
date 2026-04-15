package com.example.plantillaSpring.Entidades;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeptoDTO {
    private int numdep;
    private String nomdep;
    private String localidad;
    private int idJefe;

    public  DeptoDTO(Depto depto){
        this.numdep = depto.getNumdep();
        this.nomdep = depto.getNomdep();
        this.localidad = depto.getLocalidad();
        this.idJefe = depto.getJefe().getNumemp();
    }
}
