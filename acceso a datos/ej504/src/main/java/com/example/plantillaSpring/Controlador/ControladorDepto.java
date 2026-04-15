package com.example.plantillaSpring.Controlador;

import com.example.plantillaSpring.Entidades.Depto;
import com.example.plantillaSpring.Entidades.DeptoDTO;
import com.example.plantillaSpring.Servicios.ServicioDepto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/empleados/depto")
@RequiredArgsConstructor
public class ControladorDepto {
    private  final ServicioDepto serDep;

    @GetMapping
    public ResponseEntity<List<Depto>> obtenerDeptos(){
        return ResponseEntity.ok(serDep.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Depto> obtenerDeptoPorId(@PathVariable int id){
        return ResponseEntity.ok(serDep.findById(id));
    }

    @PostMapping
    public ResponseEntity<String> creaDepartamento(@RequestBody Depto dep){
        serDep.guardar(dep);
        return ResponseEntity.status(HttpStatus.CREATED).body("Depto creado");
    }

    @PostMapping("/{id}")
    public ResponseEntity<Depto> modificaDepartamento(@PathVariable int id,@RequestBody Depto dep){
       Depto depOr=serDep.findById(id);
       if (depOr!=null){
           dep.setNumdep(id);
           serDep.guardar(dep);
           return ResponseEntity.ok(dep);
       }else {
           return ResponseEntity.notFound().build();
       }
    }
}
