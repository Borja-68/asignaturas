package com.example.ej501;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/saludo")
public class SaludoControlador {

    private final SaludoServicio saludoServicio;
     @Autowired
        public SaludoControlador(SaludoServicio sal){
         this.saludoServicio=sal;
     }

     @GetMapping("/{nombre}")
    public ResponseEntity<String> saludar(@PathVariable String nombre){
         String saludo= saludoServicio.saludo(nombre);
         return ResponseEntity.ok(saludo);
     }

}
