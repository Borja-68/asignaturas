package com.example.plantillaSpring.Servicios;

import com.example.plantillaSpring.Entidades.Depto;
import com.example.plantillaSpring.Repos.RepositorioDepto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ServicioDepto {
    private RepositorioDepto repoDepto;

    public void guardar(Depto depto){
        repoDepto.save(depto);
    }

    public Depto nuevoDepto(Depto depto){
        return repoDepto.save(depto);
    }

    public Depto remove(int depto){
        Depto dep=repoDepto.findById(depto).orElse(null);
        repoDepto.deleteById(depto);
        return dep;
    }

    public Depto update(Depto depto){
        return repoDepto.save(depto);
    }

    public List<Depto> findAll(){
        return repoDepto.findAll();
    }

    public Depto findById(int id){
        return repoDepto.findById(id).orElse(null);
    }
    public List<Depto> findByNomdepIsNotAndNomdepIsNot(String nomdep1,String nomdep2){
        return repoDepto.findByNomdepIsNotAndNomdepIsNot(nomdep1,nomdep2);
    }
}
