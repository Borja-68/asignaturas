package com.example.plantillaSpring.Controlador;

import com.example.plantillaSpring.Entidades.*;
import com.example.plantillaSpring.Servicios.ServicioDepto;
import com.example.plantillaSpring.Servicios.ServicioEmp;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/empleados")
@RequiredArgsConstructor
public class ControladorAplicacion {
    private final ServicioDepto serDep;
    private final ServicioEmp serEmp;

    @GetMapping("/Consulta1/{dep1}-{dep2}")
    public ResponseEntity<List<DeptoDTO>> consultaNum1(@PathVariable String dep1, @PathVariable String dep2){
        return ResponseEntity.ok(serDep.findByNomdepIsNotAndNomdepIsNot(dep1,dep2).stream().map(DeptoDTO::new).collect(Collectors.toList()));
    }

    @GetMapping("/Consulta2/{letra}-{salario}-{numdep}")
    public ResponseEntity<List<EmpDTO>> consultaNum2(@PathVariable char letra, @PathVariable Double salario, @PathVariable int numdep){
        return ResponseEntity.ok(serEmp.nombreEmpleadoLetraSalariooComicionDepartamento(letra,salario,numdep).stream().map(EmpDTO::new).collect(Collectors.toList()));
    }

    @GetMapping("/Consulta3/{puesto}")
    public ResponseEntity<List<Consulta3DTO>> consultaNum3(@PathVariable String puesto){
        return ResponseEntity.ok(serEmp.nombreyFechaEmpleadosNoPuesto(puesto).stream().map(Consulta3DTO::new).collect(Collectors.toList()));
    }

    @GetMapping("/Consulta4")
    public ResponseEntity<List<EmpDTO>> consultaNum4(){
        return ResponseEntity.ok(serEmp.findByComisionIsNotNull().stream().map(EmpDTO::new).collect(Collectors.toList()));
    }

    @GetMapping("/Consulta5/{numemp}")
    public ResponseEntity<List<EmpDTO>> consultaNum5(@PathVariable int numemp){
        return ResponseEntity.ok(serEmp.empleadosSalarioMayorASalariodeOtroCompanhero(numemp).stream().map(EmpDTO::new).collect(Collectors.toList()));
    }

    @GetMapping("/asignar/{idDept}-{idEmp}")
    public ResponseEntity<DeptoDTO> asignarDepartamento(@PathVariable int idDept, @PathVariable int idEmp ){
        Depto depto = serDep.findById(idDept);
        Emp emp = serEmp.findById(idEmp);
        depto.anadirEmpleado(emp);
        serDep.guardar(depto);
        return ResponseEntity.ok(new DeptoDTO(depto));
    }

    @GetMapping("/jefe/{idDept}-{idEmp}")
    public ResponseEntity<DeptoDTO> asignaJefe(@PathVariable int idDept, @PathVariable int idEmp ){
        Depto depto = serDep.findById(idDept);
        Emp emp = serEmp.findById(idEmp);
        depto.cambiarJefe(emp);
        serDep.guardar(depto);
        return ResponseEntity.ok(new DeptoDTO(depto));
    }

    @PatchMapping("/cargar")
    public ResponseEntity<String> cargarDatos(){
        ArrayList<Depto> listaDeptos = new ArrayList<>();
        ArrayList<Emp> listaEmp = new ArrayList<>();

        listaEmp.add(new Emp("SMITH", "CONTABLE", LocalDate.of(1980, 12, 17),  800));
        listaEmp.add(new Emp("ALLEN", "COMERCIAL", LocalDate.of(1981, 2, 20), 1600, 300));
        listaEmp.add(new Emp("WARD", "COMERCIAL", LocalDate.of(1981, 2, 22), 1250, 500));
        listaEmp.add(new Emp("JONES", "MANAGER", LocalDate.of(1981, 4, 2), 2975));
        listaEmp.add(new Emp("MARTIN", "COMERCIAL", LocalDate.of(1981, 9, 28), 1250, 1400));
        listaEmp.add(new Emp("BLAKE", "MANAGER", LocalDate.of(1981, 5, 1), 2850));
        listaEmp.add(new Emp("CLARK", "MANAGER", LocalDate.of(1981, 6, 9),2450));
        listaEmp.add(new Emp("SCOTT", "ANALISTA", LocalDate.of(1982, 12, 9),3000));
        listaEmp.add(new Emp("KING", "PRESIDENTE", LocalDate.of(1981, 11, 17),5000));
        listaEmp.add(new Emp("TURNER", "COMERCIAL", LocalDate.of(1981, 9, 8), 1500,0));
        listaEmp.add(new Emp("ADAMS", "CONTABLE", LocalDate.of(1983, 1, 12),1100));
        listaEmp.add(new Emp("JAMES", "CONTABLE", LocalDate.of(1981, 12, 3), 950));
        listaEmp.add(new Emp("FORD", "ANALISTA", LocalDate.of(1981, 12, 3),3000));
        listaEmp.add(new Emp("MILLER", "CONTABLE", LocalDate.of(1982, 1, 23),1300));

        for (Emp emp: listaEmp)
            serEmp.guardar(emp);

        listaDeptos.add(new Depto("CONTABILIDAD", "SANTIAGO"));
        listaDeptos.add(new Depto("ADMINISTRACION", "VIGO"));
        listaDeptos.add(new Depto("VENTAS", "PONTEVEDRA"));
        listaDeptos.add(new Depto("OPERACIONES", "VILAGARCIA"));

        listaDeptos.get(0).cambiarJefe(listaEmp.get(6));
        serDep.guardar(listaDeptos.get(0));
        listaDeptos.get(1).cambiarJefe(listaEmp.get(0));
        serDep.guardar(listaDeptos.get(1));
        listaDeptos.get(2).cambiarJefe(listaEmp.get(1));
        serDep.guardar(listaDeptos.get(2));
        listaDeptos.get(3).cambiarJefe(listaEmp.get(13));
        serDep.guardar(listaDeptos.get(3));

        listaDeptos.get(0).anadirEmpleado(listaEmp.get(6));
        listaDeptos.get(0).anadirEmpleado(listaEmp.get(8));
        listaDeptos.get(0).anadirEmpleado(listaEmp.get(13));

        listaDeptos.get(1).anadirEmpleado(listaEmp.get(1));
        listaDeptos.get(1).anadirEmpleado(listaEmp.get(3));
        listaDeptos.get(1).anadirEmpleado(listaEmp.get(7));
        listaDeptos.get(1).anadirEmpleado(listaEmp.get(10));
        listaDeptos.get(1).anadirEmpleado(listaEmp.get(12));

        listaDeptos.get(2).anadirEmpleado(listaEmp.get(1));
        listaDeptos.get(2).anadirEmpleado(listaEmp.get(2));
        listaDeptos.get(2).anadirEmpleado(listaEmp.get(4));
        listaDeptos.get(2).anadirEmpleado(listaEmp.get(5));
        listaDeptos.get(2).anadirEmpleado(listaEmp.get(9));
        listaDeptos.get(2).anadirEmpleado(listaEmp.get(11));

        for (Depto depto : listaDeptos)
            serDep.guardar(depto);

        return ResponseEntity.ok("Datos cargados correctamente");
    }

}
