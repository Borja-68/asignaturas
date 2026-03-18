package com.example.plantillaSpring.Servicios;

import com.example.plantillaSpring.Depto;
import com.example.plantillaSpring.Repos.RepositorioDepto;

public class ServicioDepto {
    private RepositorioDepto repoDepto;

    public void guardar(Depto depto){
        repoDepto.save(depto);
    }

    public Depto nuevoDepto(Depto depto){
        return repoDepto.save(depto);
    }

    public void remove(int depto){
        repoDepto.delete(depto);
    }
}
