package com.example.plantillaSpring.Controlador;

import com.example.plantillaSpring.Entidades.Depto;
import com.example.plantillaSpring.Entidades.Emp;
import com.example.plantillaSpring.Servicios.ServicioDepto;
import com.example.plantillaSpring.Servicios.ServicioEmp;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/empleados/emp")
@RequiredArgsConstructor
public class ControladorEmp {
    private  final ServicioEmp serEmp;

    @GetMapping
    public ResponseEntity<List<Emp>> obtenerDeptos(){
        return ResponseEntity.ok(serEmp.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Emp> obtenerDeptoPorId(@PathVariable int id){
        return ResponseEntity.ok(serEmp.findById(id));
    }

    @PostMapping
    public ResponseEntity<String> creaDepartamento(@RequestBody Emp emp){
        serEmp.guardar(emp);
        return ResponseEntity.status(HttpStatus.CREATED).body("Depto creado");
    }

    @PostMapping("/{id}")
    public ResponseEntity<Emp> modificaDepartamento(@PathVariable int id,@RequestBody Emp emp){
        Emp empOr=serEmp.findById(id);
        if (empOr!=null){
            emp.setNumemp(id);
            serEmp.guardar(emp);
            return ResponseEntity.ok(emp);
        }else {
            return ResponseEntity.notFound().build();
        }
    }
}
